package com.superworldsun.superslegend.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TimeSpeedCommand {
    private TimeSpeedCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralArgumentBuilder<CommandSourceStack> speedCommand = Commands.literal("speed")
                .requires(source -> source.hasPermission(2))
                .then(modeCommand("normal", TimeSongSavedData.Mode.NORMAL,
                        "commands.time.speed.normal", false))
                .then(modeCommand("double", TimeSongSavedData.Mode.DOUBLE,
                        "commands.time.speed.double", true))
                .then(modeCommand("inverted", TimeSongSavedData.Mode.INVERTED,
                        "commands.time.speed.inverted", true));

        CommandNode<CommandSourceStack> timeCommand = dispatcher.getRoot().getChild("time");
        if (timeCommand != null) {
            timeCommand.addChild(speedCommand.build());
        } else {
            dispatcher.register(Commands.literal("time")
                    .requires(source -> source.hasPermission(2))
                    .then(speedCommand));
        }
    }

    private static LiteralArgumentBuilder<CommandSourceStack> modeCommand(
            String name, TimeSongSavedData.Mode mode, String messageKey, boolean allowTimeSuffix) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(name)
                .executes(context -> setSpeed(context.getSource(), mode, messageKey));
        if (allowTimeSuffix) {
            command.then(Commands.literal("time")
                    .executes(context -> setSpeed(context.getSource(), mode, messageKey)));
        }
        return command;
    }

    private static int setSpeed(CommandSourceStack source, TimeSongSavedData.Mode mode, String messageKey) {
        if (!TimeSongSavedData.isModeEnabled(mode)) {
            source.sendFailure(Component.translatable(mode == TimeSongSavedData.Mode.DOUBLE
                    ? "commands.time.speed.double_disabled"
                    : "commands.time.speed.inverted_disabled"));
            return 0;
        }
        ServerLevel overworld = source.getServer().overworld();
        TimeSongSavedData timeData = TimeSongSavedData.get(overworld);
        timeData.setMode(mode, overworld);
        timeData.syncToAllPlayers();
        source.sendSuccess(() -> Component.translatable(messageKey), true);
        return 1;
    }
}
