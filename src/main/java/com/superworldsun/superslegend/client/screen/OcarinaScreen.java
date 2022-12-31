package com.superworldsun.superslegend.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.client.sound.OcarinaSongSound;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.PlaySongMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.ILearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongsProvider;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

public class OcarinaScreen extends Screen {
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/ocarina.png");
	private static final int NOTE_ICON_SIZE = 11;
	private static final int NOTE_ICON_SPACING = 6;
	private static final int NOTE_ICON_TEXT_SPACING = 3;
	private static final int PLAYED_NOTE_SPACING = 16;
	private static final int SONG_ICON_HEIGHT = 15;
	private static final int SONG_ICON_WIDTH = 10;
	private static final int SONG_ICON_VERTICAL_SPACING = 7;
	private static final int SONG_ICON_HORIZONTAL_SPACING = 13;
	private static final float CONTROLS_Y = 0.75F;
	private static final float PLAYED_NOTES_Y = 0.5F;
	private static final float SONGS_Y = 0.12F;
	private static final int UNLEARNED_SONG_ICON_COLOR = 0x404040;
	private static final int MAX_NOTES = calculateMaxNotes();
	private static List<Note> playedNotes = new ArrayList<>();
	private static String playedPattern = "";
	private OcarinaSong playedSong;
	private List<Note> hintNotes = new ArrayList<>();
	private int hintTimer;
	private int hintNoteTimer;
	private int closeTimer = -1;

	public OcarinaScreen() {
		super(new StringTextComponent(""));
		clearPlayedNotes();
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderOcarinaBackground(matrixStack);
		renderControls(matrixStack);
		renderPlayedNotes(matrixStack);
		renderSongsIcons(matrixStack);
		renderSongsHoverText(matrixStack, mouseX, mouseY);
	}

	private void renderSongsHoverText(MatrixStack matrixStack, int mouseX, int mouseY) {
		int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
		int songsX = (width - songsRowWidth) / 2;
		int songsY = (int) (height * SONGS_Y);
		int i = 0;

		for (OcarinaSong song : SupersLegendRegistries.OCARINA_SONGS) {
			boolean isSongLearned = LearnedSongsProvider.get(minecraft.player).getLearnedSongs().contains(song);
			boolean isMouseOver = isMouseOver(mouseX, mouseY, songsX, songsY, SONG_ICON_WIDTH, SONG_ICON_HEIGHT);

			if (isSongLearned && isMouseOver) {
				renderTooltip(matrixStack, song.getLocalizedName(), mouseX, mouseY);
			}

			songsX += SONG_ICON_WIDTH + SONG_ICON_HORIZONTAL_SPACING;
			i++;

			if (i % 7 == 0 && i > 0) {
				songsX = (width - songsRowWidth) / 2;
				songsY += SONG_ICON_HEIGHT + SONG_ICON_VERTICAL_SPACING;
			}
		}
	}

	private void renderSongsIcons(MatrixStack matrixStack) {
		minecraft.getTextureManager().bind(TEXTURE);
		int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
		int songsX = (width - songsRowWidth) / 2;
		int songsY = (int) (height * SONGS_Y);
		int i = 0;

		for (OcarinaSong song : SupersLegendRegistries.OCARINA_SONGS) {
			int color = UNLEARNED_SONG_ICON_COLOR;
			boolean isSongLearned = LearnedSongsProvider.get(minecraft.player).getLearnedSongs().contains(song);

			if (isSongLearned) {
				color = song.getSongIconColor();
			}

			blit(matrixStack, songsX, songsY, 0, 41, SONG_ICON_WIDTH, SONG_ICON_HEIGHT, color);
			songsX += SONG_ICON_WIDTH + SONG_ICON_HORIZONTAL_SPACING;
			i++;

			if (i % 7 == 0 && i > 0) {
				songsX = (width - songsRowWidth) / 2;
				songsY += SONG_ICON_HEIGHT + SONG_ICON_VERTICAL_SPACING;
			}
		}
	}

	private void renderPlayedNotes(MatrixStack matrixStack) {
		minecraft.getTextureManager().bind(TEXTURE);
		int notesX = (width - 156) / 2 + 23;
		int notesY = (int) (height * PLAYED_NOTES_Y) - 30 / 2;

		for (Note note : playedNotes) {
			note.render(this, matrixStack, notesX, notesY + note.yShift);
			notesX += PLAYED_NOTE_SPACING;
		}
	}

	private void renderControls(MatrixStack matrixStack) {
		int controlsWidth = NOTE_ICON_SIZE * 5 + NOTE_ICON_SPACING * 4 + NOTE_ICON_TEXT_SPACING * 5;

		for (Note note : Note.values()) {
			controlsWidth += font.width(note.keyBinding.getTranslatedKeyMessage());
		}

		int controlsX = (width - controlsWidth) / 2;
		int controlsY = (int) (height * CONTROLS_Y);

		for (Note note : Note.values()) {
			ITextComponent noteKeyName = note.keyBinding.getTranslatedKeyMessage();
			minecraft.getTextureManager().bind(TEXTURE);
			note.render(this, matrixStack, controlsX, controlsY);
			controlsX += NOTE_ICON_SIZE + NOTE_ICON_TEXT_SPACING;
			font.draw(matrixStack, noteKeyName, controlsX, controlsY + 1, 0xffffff);
			controlsX += font.width(noteKeyName) + NOTE_ICON_SPACING;
		}
	}

	private void renderOcarinaBackground(MatrixStack matrixStack) {
		renderBackground(matrixStack);
		minecraft.getTextureManager().bind(TEXTURE);
		blit(matrixStack, (width - 156) / 2, (int) (height * PLAYED_NOTES_Y) - 30 / 2, 0, 0, 156, 30);
	}

	private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY) {
		return mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
		int songsX = (width - songsRowWidth) / 2;
		int songsY = (int) (height * SONGS_Y);
		int i = 0;

		for (OcarinaSong song : SupersLegendRegistries.OCARINA_SONGS) {
			boolean isSongLearned = LearnedSongsProvider.get(minecraft.player).getLearnedSongs().contains(song);
			boolean isMouseOver = isMouseOver((int) mouseX, (int) mouseY, songsX, songsY, SONG_ICON_WIDTH, SONG_ICON_HEIGHT);
			boolean canShowSongHint = hintTimer == 0;

			if (isSongLearned && isMouseOver && canShowSongHint) {
				clearPlayedNotes();
				song.getSongPattern().chars().forEach(c -> hintNotes.add(Note.getByChar((char) c)));
				hintTimer = song.getSongPattern().length() * 10 + 10;
			}

			songsX += SONG_ICON_WIDTH + SONG_ICON_HORIZONTAL_SPACING;
			i++;

			if (i % 7 == 0 && i > 0) {
				songsX = (width - songsRowWidth) / 2;
				songsY += SONG_ICON_HEIGHT + SONG_ICON_VERTICAL_SPACING;
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean keyPressed(int keyCode, int p_231046_2_, int p_231046_3_) {
		boolean canPlayNotes = playedNotes.size() < MAX_NOTES && playedSong == null && hintTimer == 0;

		if (canPlayNotes) {
			Optional.ofNullable(Note.getByKeyCode(keyCode)).ifPresent(Note::play);
		}

		SupersLegendRegistries.OCARINA_SONGS.forEach(song -> {
			ILearnedSongs learnedSongs = LearnedSongsProvider.get(minecraft.player);
			boolean wasSongPlayed = learnedSongs.getLearnedSongs().contains(song) && song.getSongPattern().equals(playedPattern);

			if (wasSongPlayed) {
				playedSong = song;
				closeTimer = 20;
				LearnedSongsProvider.get(minecraft.player).setCurrentSong(null);
			}
		});

		return super.keyPressed(keyCode, p_231046_2_, p_231046_3_);
	}

	@Override
	public void tick() {
		updateSongHint();
		updateDelayedClose();
	}

	private void updateSongHint() {
		if (hintTimer == 1) {
			clearPlayedNotes();
		}

		if (hintTimer > 0) {
			if (hintNoteTimer == 0 && !hintNotes.isEmpty()) {
				hintNotes.get(0).play();
				hintNotes.remove(0);
				hintNoteTimer = 10;
			}

			if (hintNoteTimer > 0) {
				hintNoteTimer--;
			}

			hintTimer--;
		}
	}

	private void updateDelayedClose() {
		if (closeTimer == 0) {
			boolean canApplySongEffect = minecraft.player.isHolding(ItemInit.OCARINA_OF_TIME.get()) || !playedSong.requiresOcarinaOfTime();

			if (canApplySongEffect) {
				NetworkDispatcher.networkChannel.sendToServer(new PlaySongMessage(playedSong));
				minecraft.player.sendMessage(new TranslationTextComponent("screen.ocarina.song_played", playedSong.getLocalizedName()), Util.NIL_UUID);
			}

			minecraft.setScreen(null);
			minecraft.getSoundManager().play(new OcarinaSongSound(minecraft.player, playedSong));
			LearnedSongsProvider.get(minecraft.player).setCurrentSong(playedSong);
		}

		if (closeTimer > 0) {
			closeTimer--;
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void clearPlayedNotes() {
		playedNotes.clear();
		playedPattern = "";
	}

	@SuppressWarnings("deprecation")
	private static void blit(MatrixStack matrixStack, int x, int y, int u, int v, int sizeX, int sizeY, int color) {
		Matrix4f matrix = matrixStack.last().pose();
		float actualU = u / 256F;
		float actualV = v / 256F;
		float sizeU = sizeX / 256F;
		float sizeV = sizeY / 256F;
		float a = 1F;
		float r = (color >> 16 & 255) / 255F;
		float g = (color >> 8 & 255) / 255F;
		float b = (color & 255) / 255F;
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
		bufferbuilder.vertex(matrix, x, y + sizeY, 0).color(r, g, b, a).uv(actualU, actualV + sizeV).endVertex();
		bufferbuilder.vertex(matrix, x + sizeX, y + sizeY, 0).color(r, g, b, a).uv(actualU + sizeU, actualV + sizeV).endVertex();
		bufferbuilder.vertex(matrix, x + sizeX, y, 0).color(r, g, b, a).uv(actualU + sizeU, actualV).endVertex();
		bufferbuilder.vertex(matrix, x, y, 0).color(r, g, b, a).uv(actualU, actualV).endVertex();
		bufferbuilder.end();
		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.end(bufferbuilder);
	}

	private static int calculateMaxNotes() {
		int maxNotes = 0;

		for (OcarinaSong song : SupersLegendRegistries.OCARINA_SONGS) {
			int length = song.getSongPattern().length();

			if (length > maxNotes) {
				maxNotes = length;
			}
		}

		return maxNotes;
	}

	private enum Note {
		U(4, 0, 30, Minecraft.getInstance().options.keyUp, SoundInit.OCARINA_NOTE_U),
		L(7, 11, 30, Minecraft.getInstance().options.keyLeft, SoundInit.OCARINA_NOTE_L),
		R(14, 22, 30, Minecraft.getInstance().options.keyRight, SoundInit.OCARINA_NOTE_R),
		D(18, 33, 30, Minecraft.getInstance().options.keyDown, SoundInit.OCARINA_NOTE_D),
		A(22, 44, 30, Minecraft.getInstance().options.keyJump, SoundInit.OCARINA_NOTE_A);

		final int textureX;
		final int textureY;
		final int yShift;
		final KeyBinding keyBinding;
		final RegistryObject<SoundEvent> soundObject;

		private Note(int yShift, int textureX, int textureY, KeyBinding keyBinding, RegistryObject<SoundEvent> soundObject) {
			this.yShift = yShift;
			this.textureX = textureX;
			this.textureY = textureY;
			this.keyBinding = keyBinding;
			this.soundObject = soundObject;
		}

		void render(Screen screen, MatrixStack matrixStack, int x, int y) {
			screen.blit(matrixStack, x, y, textureX, textureY, NOTE_ICON_SIZE, NOTE_ICON_SIZE);
		}

		void play() {
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.player.playSound(soundObject.get(), 1F, 1F);
			playedNotes.add(this);
			playedPattern += this.name().toLowerCase();
		}

		@Nullable
		static Note getByChar(char c) {
			for (Note note : Note.values()) {
				if (note.name().toLowerCase().equals("" + c)) {
					return note;
				}
			}

			return null;
		}

		@Nullable
		static Note getByKeyCode(int keyCode) {
			for (Note note : Note.values()) {
				if (keyCode == note.keyBinding.getKey().getValue()) {
					return note;
				}
			}

			return null;
		}
	}
}