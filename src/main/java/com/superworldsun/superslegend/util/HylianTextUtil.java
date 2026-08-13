package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SignText;

public final class HylianTextUtil {
    private static final String HYLIAN_CHAT_MARKER = "\u2063";

    public static final ResourceLocation HYLIAN_FONT =
            new ResourceLocation(SupersLegendMain.MOD_ID, "hylian");

    private HylianTextUtil() {
    }
    public static boolean isHoldingBookOfMudora(Player player) {
        return player != null
                && (player.getMainHandItem().is(ItemInit.BOOK_OF_MUDORA.get())
                || player.getOffhandItem().is(ItemInit.BOOK_OF_MUDORA.get()));
    }

    public static MutableComponent markAsHylian(Component component) {
        return component.copy().withStyle(style -> style.withFont(HYLIAN_FONT));
    }

    public static MutableComponent markAsHylian(String text) {
        return Component.literal(text).withStyle(style -> style.withFont(HYLIAN_FONT));
    }

    public static boolean containsHylianText(SignText signText) {
        return containsHylianText(signText.getMessages(false))
                || containsHylianText(signText.getMessages(true));
    }

    private static boolean containsHylianText(Component[] messages) {
        for (Component message : messages) {
            if (usesHylianFont(message)) {
                return true;
            }
        }

        return false;
    }

    public static boolean usesHylianFont(Component component) {
        if (component.getString().isEmpty()) {
            return false;
        }

        if (HYLIAN_FONT.equals(component.getStyle().getFont())) {
            return true;
        }

        for (Component sibling : component.getSiblings()) {
            if (usesHylianFont(sibling)) {
                return true;
            }
        }

        return false;
    }

    public static String markHylianChatMessage(String text) {
        return markHylianText(text);
    }

    public static boolean isMarkedHylianChatMessage(String text) {
        return isMarkedHylianText(text);
    }

    public static String removeHylianChatMarker(String text) {
        return removeHylianTextMarker(text);
    }

    public static String markHylianText(String text) {
        return isMarkedHylianText(text) ? text : HYLIAN_CHAT_MARKER + text;
    }

    public static boolean isMarkedHylianText(String text) {
        return text != null && text.startsWith(HYLIAN_CHAT_MARKER);
    }

    public static String removeHylianTextMarker(String text) {
        return isMarkedHylianText(text)
                ? text.substring(HYLIAN_CHAT_MARKER.length())
                : text;
    }

    public static boolean containsHylianBookText(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("pages", Tag.TAG_LIST)) {
            return false;
        }

        ListTag pages = stack.getTag().getList("pages", Tag.TAG_STRING);
        for (int page = 0; page < pages.size(); page++) {
            String text = pages.getString(page);
            if (isMarkedHylianText(text) && !removeHylianTextMarker(text).isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
