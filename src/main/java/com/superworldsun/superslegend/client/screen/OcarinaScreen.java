package com.superworldsun.superslegend.client.screen;

import java.util.UUID;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.client.sound.OcarinaSongSound;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.PlaySongMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.ILearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongsProvider;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OcarinaScreen extends Screen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/ocarina.png");
	private static int max_notes;
	private String playedNotes = "";
	private int closeDelay = -1;
	private OcarinaSong playedSong;
	
	public OcarinaScreen()
	{
		super(new StringTextComponent(""));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(matrixStack);
		minecraft.getTextureManager().bind(TEXTURE);
		blit(matrixStack, (width - 156) / 2, (int) ((height * .67 - 30) / 2), 0, 0, 156, 30);
		int controlsWidth = 11 * 5 + 6 * 4 + 3 * 5;
		KeyBinding[] keys = { minecraft.options.keyUp, minecraft.options.keyLeft, minecraft.options.keyRight, minecraft.options.keyDown,
				minecraft.options.keyJump };
		
		for (KeyBinding key : keys)
		{
			controlsWidth += font.width(key.getTranslatedKeyMessage());
		}
		
		int controlsX = (width - controlsWidth) / 2;
		int controlsY = (int) (height * .67 - 11 / 2);
		int i = 0;
		
		for (KeyBinding key : keys)
		{
			minecraft.getTextureManager().bind(TEXTURE);
			blit(matrixStack, controlsX, controlsY, i * 11, 30, 11, 11);
			controlsX += 11 + 3;
			font.draw(matrixStack, key.getTranslatedKeyMessage(), controlsX, controlsY + 1, 0xffffff);
			controlsX += font.width(key.getTranslatedKeyMessage()) + 6;
			i++;
		}
		
		minecraft.getTextureManager().bind(TEXTURE);
		int notesX = (width - 156) / 2 + 23;
		int notesY = (int) ((height * .67 - 30) / 2);
		
		for (i = 0; i < playedNotes.length(); i++)
		{
			char note = playedNotes.charAt(i);
			
			switch (note)
			{
				case 'a':
					blit(matrixStack, notesX, notesY + 22, 44, 30, 11, 11);
					break;
				case 'd':
					blit(matrixStack, notesX, notesY + 18, 33, 30, 11, 11);
					break;
				case 'r':
					blit(matrixStack, notesX, notesY + 14, 22, 30, 11, 11);
					break;
				case 'l':
					blit(matrixStack, notesX, notesY + 7, 11, 30, 11, 11);
					break;
				case 'u':
					blit(matrixStack, notesX, notesY + 4, 0, 30, 11, 11);
					break;
			}
			
			notesX += 16;
		}
	}
	
	@Override
	public boolean keyPressed(int keyCode, int p_231046_2_, int p_231046_3_)
	{
		if (playedNotes.length() < max_notes && playedSong == null)
		{
			if (keyCode == minecraft.options.keyUp.getKey().getValue())
			{
				minecraft.player.playSound(SoundInit.OCARINA_NOTE_U.get(), 1F, 1F);
				playedNotes += "u";
			}
			
			if (keyCode == minecraft.options.keyDown.getKey().getValue())
			{
				minecraft.player.playSound(SoundInit.OCARINA_NOTE_D.get(), 1F, 1F);
				playedNotes += "d";
			}
			
			if (keyCode == minecraft.options.keyLeft.getKey().getValue())
			{
				minecraft.player.playSound(SoundInit.OCARINA_NOTE_L.get(), 1F, 1F);
				playedNotes += "l";
			}
			
			if (keyCode == minecraft.options.keyRight.getKey().getValue())
			{
				minecraft.player.playSound(SoundInit.OCARINA_NOTE_R.get(), 1F, 1F);
				playedNotes += "r";
			}
			
			if (keyCode == minecraft.options.keyJump.getKey().getValue())
			{
				minecraft.player.playSound(SoundInit.OCARINA_NOTE_A.get(), 1F, 1F);
				playedNotes += "a";
			}
		}
		
		SupersLegendRegistries.OCARINA_SONGS.forEach(song ->
		{
			ILearnedSongs learnedSongs = LearnedSongsProvider.get(minecraft.player);
			
			if (/* learnedSongs.getLearnedSongs().contains(song) && */song.getPattern().equals(playedNotes))
			{
				LearnedSongsProvider.get(minecraft.player).setCurrentSong(null);
				playedSong = song;
				closeDelay = 20;
			}
		});
		
		return super.keyPressed(keyCode, p_231046_2_, p_231046_3_);
	}
	
	@Override
	public void tick()
	{
		if (closeDelay == 0)
		{
			NetworkDispatcher.networkChannel.sendToServer(new PlaySongMessage(playedSong));
			minecraft.player.sendMessage(new TranslationTextComponent("screen.ocarina.song_played", playedSong.getLocalizedName()), UUID.randomUUID());
			minecraft.setScreen(null);
			minecraft.getSoundManager().play(new OcarinaSongSound(minecraft.player, playedSong));
			LearnedSongsProvider.get(minecraft.player).setCurrentSong(playedSong);
		}
		
		if (closeDelay > 0)
		{
			closeDelay--;
		}
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	static
	{
		SupersLegendRegistries.OCARINA_SONGS.forEach(song ->
		{
			int length = song.getPattern().length();
			
			if (length > max_notes)
			{
				max_notes = length;
			}
		});
	}
}
