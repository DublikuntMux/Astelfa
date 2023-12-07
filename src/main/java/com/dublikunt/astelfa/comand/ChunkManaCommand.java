package com.dublikunt.astelfa.comand;

import com.dublikunt.astelfa.air_mana.ManaAmount;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.Vec2ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

public final class ChunkManaCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("chunk_mana")
                .then(CommandManager.argument("position", Vec2ArgumentType.vec2())
                        .then(CommandManager.literal("get")
                                .executes(ChunkManaCommand::getMana)
                        )
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ChunkManaCommand::setMana)
                                )
                        )

                )
        );
    }

    private static int getMana(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        final Text text = Text.translatable("command.astelfa.mana_amount", ManaAmount.getOrCreateManaData(chunk).mana_amount);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setMana(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        int mana_amount = IntegerArgumentType.getInteger(context, "amount");

        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        ManaAmount.getOrCreateManaData(chunk).mana_amount = mana_amount;
        final Text text = Text.translatable("command.astelfa.mana_amount", mana_amount);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }
}
