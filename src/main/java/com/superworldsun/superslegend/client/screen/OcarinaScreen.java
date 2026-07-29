package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.sound.OcarinaSongSound;
import com.superworldsun.superslegend.items.item.OcarinaOfTime;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.PlaySongMessage;
import com.superworldsun.superslegend.network.message.PlayOcarinaNoteMessage;
import com.superworldsun.superslegend.network.message.SetOcarinaPlayingMessage;
import com.superworldsun.superslegend.registries.OcarinaSongInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.LearnedSongs;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
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

    private static final List<Note> playedNotes = new ArrayList<>();
    private static String playedPattern = "";
    private OcarinaSong playedSong;
    private final List<Note> hintNotes = new ArrayList<>();
    private int hintTimer;
    private int hintNoteTimer;
    private int closeDelay = -1;
    private boolean playingStateSent = false;
    static public Player player;
    private final InteractionHand playingHand;

    public OcarinaScreen(Player player, InteractionHand playingHand) {
        super(Component.literal("Ocarina"));
        this.player = player;
        this.playingHand = playingHand;
        this.minecraft = Minecraft.getInstance();
        clearPlayedNotes();
    }

    @Override
    protected void init() {
        super.init();
        ensureClientReferences();
        sendPlayingStateOnce();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!ensureValidLayout()) {
            return;
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderBackground(guiGraphics);
        renderOcarinaBackground(guiGraphics);
        renderControls(guiGraphics);
        renderPlayedNotes(guiGraphics);
        renderSongsIcons(guiGraphics);
        renderSongsHoverText(guiGraphics, mouseX, mouseY);

    }
    private void renderControls(GuiGraphics guiGraphics) {
        int controlsWidth = NOTE_ICON_SIZE * 5 + NOTE_ICON_SPACING * 4 + NOTE_ICON_TEXT_SPACING * 5;

        for (Note note : Note.values()) {
            controlsWidth += font.width(note.keyMapping.getTranslatedKeyMessage());
        }

        int controlsX = (width - controlsWidth) / 2;
        int controlsY = (int) (height * CONTROLS_Y);

        for (Note note : Note.values()) {
            Component noteKeyName = note.keyMapping.getTranslatedKeyMessage();
//            minecraft.getTextureManager().bindForSetup(TEXTURE);
            note.render(guiGraphics, controlsX, controlsY);
            controlsX += NOTE_ICON_SIZE + NOTE_ICON_TEXT_SPACING;
            guiGraphics.drawString(font, noteKeyName, controlsX, controlsY + 1, 0xFFFFFF);
            controlsX += font.width(noteKeyName) + NOTE_ICON_SPACING;
        }
    }
    private void renderOcarinaBackground(GuiGraphics guiGraphics) {
        guiGraphics.fill(0, 0, width, height, 0x80000000);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, (width - 156) / 2, (int) (height * PLAYED_NOTES_Y) - 30 / 2, 0, 0, 156, 30, 256, 256);
    }

    private void renderPlayedNotes(GuiGraphics guiGraphics) {
        minecraft.getTextureManager().bindForSetup(TEXTURE);
        int notesX = (width - 156) / 2 + 23;
        int notesY = (int) (height * PLAYED_NOTES_Y) - 30 / 2;

        for (Note note : playedNotes) {
            guiGraphics.blit(TEXTURE, notesX, notesY + note.yShift, note.textureX, note.textureY, NOTE_ICON_SIZE, NOTE_ICON_SIZE, 256, 256);
            notesX += PLAYED_NOTE_SPACING;
        }
    }

    private void renderSongsIcons(GuiGraphics guiGraphics) {
        minecraft.getTextureManager().bindForSetup(TEXTURE);
        int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
        int songsX = (width - songsRowWidth) / 2;
        int songsY = (int) (height * SONGS_Y);
        int i = 0;

        for (RegistryObject<OcarinaSong> songRegistryObject : OcarinaSongInit.OCARINA_SONGS.getEntries()) {
            OcarinaSong song = songRegistryObject.get();
            int color = UNLEARNED_SONG_ICON_COLOR;
            boolean isSongLearned = LearnedSongs.Provider.getLearnedSongs(player).getLearnedSongs().contains(song);

            if (isSongLearned) {
                color = song.getSongIconColor();
            }

            guiGraphics.blit(TEXTURE, songsX, songsY, 0, 41, SONG_ICON_WIDTH, SONG_ICON_HEIGHT, 256, 256);
            guiGraphics.setColor((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, 1.0F);
            guiGraphics.blit(TEXTURE, songsX, songsY, 0, 41, SONG_ICON_WIDTH, SONG_ICON_HEIGHT, 256, 256);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            songsX += SONG_ICON_WIDTH + SONG_ICON_HORIZONTAL_SPACING;
            i++;

            if (i % 7 == 0 && i > 0) {
                songsX = (width - songsRowWidth) / 2;
                songsY += SONG_ICON_HEIGHT + SONG_ICON_VERTICAL_SPACING;
            }
        }
    }

    private void renderSongsHoverText(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
        int songsX = (width - songsRowWidth) / 2;
        int songsY = (int) (height * SONGS_Y);
        int i = 0;

        for (RegistryObject<OcarinaSong> songRegistryObject : OcarinaSongInit.OCARINA_SONGS.getEntries()) {
            OcarinaSong song = songRegistryObject.get();
            boolean isSongLearned = LearnedSongs.Provider.getLearnedSongs(player).getLearnedSongs().contains(song);
            boolean isMouseOver = mouseX >= songsX && mouseX <= songsX + SONG_ICON_WIDTH &&
                    mouseY >= songsY && mouseY <= songsY + SONG_ICON_HEIGHT;

            if (isSongLearned && isMouseOver) {
                guiGraphics.renderTooltip(this.font, song.getLocalizedName(), mouseX, mouseY);
            }

            songsX += SONG_ICON_WIDTH + SONG_ICON_HORIZONTAL_SPACING;
            i++;

            if (i % 7 == 0 && i > 0) {
                songsX = (width - songsRowWidth) / 2;
                songsY += SONG_ICON_HEIGHT + SONG_ICON_VERTICAL_SPACING;
            }
        }
    }

    private void ensureClientReferences() {
        if (this.minecraft == null) {
            this.minecraft = Minecraft.getInstance();
        }
        if (this.font == null && this.minecraft != null) {
            this.font = this.minecraft.font;
        }
    }

    private boolean ensureValidLayout() {
        ensureClientReferences();
        if (this.minecraft == null || this.font == null) {
            return false;
        }

        int currentWidth = this.minecraft.getWindow().getGuiScaledWidth();
        int currentHeight = this.minecraft.getWindow().getGuiScaledHeight();
        if (currentWidth <= 0 || currentHeight <= 0) {
            return false;
        }

        // A screen can occasionally reach its first render before the normal Screen#init dimensions have
        // been applied. Always use the live GUI dimensions so centered elements can never collapse at (0, 0).
        if (this.width != currentWidth || this.height != currentHeight) {
            this.width = currentWidth;
            this.height = currentHeight;
        }
        sendPlayingStateOnce();
        return true;
    }

    private void sendPlayingStateOnce() {
        if (!this.playingStateSent) {
            NetworkDispatcher.network_channel.sendToServer(new SetOcarinaPlayingMessage(true, playingHand));
            this.playingStateSent = true;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int songsRowWidth = SONG_ICON_WIDTH * 7 + SONG_ICON_HORIZONTAL_SPACING * 6;
        int songsX = (width - songsRowWidth) / 2;
        int songsY = (int) (height * SONGS_Y);
        int i = 0;

        for (RegistryObject<OcarinaSong> songRegistryObject : OcarinaSongInit.OCARINA_SONGS.getEntries()) {
            OcarinaSong song = songRegistryObject.get();
            boolean isSongLearned = LearnedSongs.Provider.getLearnedSongs(player).getLearnedSongs().contains(song);
            boolean isMouseOver = mouseX >= songsX && mouseX <= songsX + SONG_ICON_WIDTH &&
                    mouseY >= songsY && mouseY <= songsY + SONG_ICON_HEIGHT;
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

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean canPlayNotes = playedSong == null && hintTimer == 0;

        if (canPlayNotes) {
            Note note = Note.getByKeyCode(keyCode);
            boolean reachedMaxNotes = playedNotes.size() == MAX_NOTES;

            if (reachedMaxNotes) {
                removeFirstPlayedNote();
            }

            Optional.ofNullable(Note.getByKeyCode(keyCode)).ifPresent(Note::play);
            OcarinaSongInit.OCARINA_SONGS.getEntries().forEach(this::checkIfSongWasPlayed);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void checkIfSongWasPlayed(RegistryObject<OcarinaSong> song) {
        boolean isSongLearned = LearnedSongs.Provider.getLearnedSongs(player).getLearnedSongs().contains(song.get());

        if (!isSongLearned) {
            return;
        }

        String songPattern = song.get().getSongPattern();
        String playedPattern = OcarinaScreen.playedPattern;

        if (playedPattern.length() > songPattern.length()) {
            playedPattern = playedPattern.substring(MAX_NOTES - songPattern.length());
        }

        boolean isPlayedPatternCorrect = songPattern.equals(playedPattern);

        if (isPlayedPatternCorrect) {
            setPlayedSong(song.get());
            closeAfterDelay(20);
            LearnedSongs learnedSongs = LearnedSongs.Provider.getLearnedSongs(player);
            learnedSongs.setCurrentSong(null);
            LearnedSongs.Provider.saveLearnedSongs(player, learnedSongs);
        }
    }

    private void setPlayedSong(OcarinaSong song) {
        playedSong = song;
    }

    private void closeAfterDelay(int delay) {
        closeDelay = delay;
    }

    private void removeFirstPlayedNote() {
        playedNotes.remove(0);
        playedPattern = playedPattern.substring(1);
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
        if (closeDelay == 0) {
            boolean canApplySongEffect = player.getItemInHand(playingHand).getItem() instanceof OcarinaOfTime
                    || Config.canFairyOcarinaApply(playedSong);
            boolean rearFacingCamera = minecraft.options.getCameraType().isMirrored();
            NetworkDispatcher.network_channel.sendToServer(
                    new PlaySongMessage(playedSong, rearFacingCamera, playingHand));
            if (canApplySongEffect) {
                int songColor = playedSong.getSongIconColor();
                MutableComponent songName = playedSong.getLocalizedName().copy()
                        .withStyle(style -> style.withColor(TextColor.fromRgb(songColor)));
                MutableComponent message = Component.translatable("screen.ocarina.song_played", songName)
                        .withStyle(ChatFormatting.GOLD);
                player.sendSystemMessage(message);            }
            minecraft.setScreen(null);
            minecraft.getSoundManager().play(new OcarinaSongSound(player, playedSong));
            LearnedSongs learnedSongs = LearnedSongs.Provider.getLearnedSongs(player);
            learnedSongs.setCurrentSong(playedSong);
            LearnedSongs.Provider.saveLearnedSongs(player, learnedSongs);
        }

        if (closeDelay > 0) {
            closeDelay--;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        super.removed();
        if (this.playingStateSent) {
            NetworkDispatcher.network_channel.sendToServer(new SetOcarinaPlayingMessage(false, playingHand));
            this.playingStateSent = false;
        }
        if (minecraft != null && minecraft.gameMode != null && player != null && player.isUsingItem()) {
            minecraft.gameMode.releaseUsingItem(player);
        }
    }

    public InteractionHand getPlayingHand() {
        return playingHand;
    }

    private void clearPlayedNotes() {
        playedNotes.clear();
        playedPattern = "";
    }

    private static int calculateMaxNotes() {
        return OcarinaSongInit.OCARINA_SONGS.getEntries().stream()
                .mapToInt(songRegistryObject -> songRegistryObject.get().getSongPattern().length())
                .max()
                .orElse(0);
    }

    private enum Note {
        U(4, 0, 30, Minecraft.getInstance().options.keyUp, SoundInit.OCARINA_NOTE_U),
        L(7, 11, 30, Minecraft.getInstance().options.keyLeft, SoundInit.OCARINA_NOTE_L),
        R(14, 22, 30, Minecraft.getInstance().options.keyRight, SoundInit.OCARINA_NOTE_R),
        D(18, 33, 30, Minecraft.getInstance().options.keyDown, SoundInit.OCARINA_NOTE_D),
        A(22, 44, 30, Minecraft.getInstance().options.keyJump, SoundInit.OCARINA_NOTE_A);

        final int yShift;
        final int textureX;
        final int textureY;
        final KeyMapping keyMapping;
        final RegistryObject<net.minecraft.sounds.SoundEvent> soundObject;

        Note(int yShift, int textureX, int textureY, KeyMapping keyMapping, RegistryObject<net.minecraft.sounds.SoundEvent> soundObject) {
            this.yShift = yShift;
            this.textureX = textureX;
            this.textureY = textureY;
            this.keyMapping = keyMapping;
            this.soundObject = soundObject;
        }

        void render(GuiGraphics guiGraphics, int x, int y) {
            guiGraphics.blit(TEXTURE, x, y, textureX, textureY, NOTE_ICON_SIZE, NOTE_ICON_SIZE, 256, 256);
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
                if (keyCode == note.keyMapping.getKey().getValue()) {
                    return note;
                }
            }
            return null;
        }

        void play() {

            Minecraft.getInstance().getSoundManager().play(new AbstractTickableSoundInstance(soundObject.get(),
                    SoundSource.PLAYERS, RandomSource.create()) {

                @Override
                public void tick() {
                    if (!player.isAlive()) {
                        stop();
                        return;
                    }
                    x = player.getX();
                    y = player.getY();
                    z = player.getZ();
                }

                @Override
                public boolean canPlaySound()
                {
                    return !player.isSilent();
                }

                @Override
                public boolean canStartSilent()
                {
                    return true;
                }
            });

            NetworkDispatcher.network_channel.sendToServer(new PlayOcarinaNoteMessage(ordinal()));

            playedNotes.add(this);
            playedPattern += this.name().toLowerCase();
        }

    }
}
