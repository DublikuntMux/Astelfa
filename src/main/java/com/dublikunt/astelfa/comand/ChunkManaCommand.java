package com.dublikunt.astelfa.comand;

import com.dublikunt.astelfa.air_mana.ManaAmount;
import com.dublikunt.astelfa.air_mana.ManaDataObject;
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
                        .then(CommandManager.literal("all")
                                .executes(ChunkManaCommand::getAll)
                        )
                        .then(CommandManager.literal("mana_amount")
                                .then(CommandManager.literal("get")
                                        .executes(ChunkManaCommand::getMana)
                                )
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ChunkManaCommand::setMana)
                                        )
                                )
                        )
                        .then(CommandManager.literal("local_maximum")
                                .then(CommandManager.literal("get")
                                        .executes(ChunkManaCommand::getLocalMaximum)
                                )
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ChunkManaCommand::setLocalMaximum)
                                        )
                                )
                        )
                        .then(CommandManager.literal("tick_to_regen")
                                .then(CommandManager.literal("get")
                                        .executes(ChunkManaCommand::getTickToRegen)
                                )
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ChunkManaCommand::setTickToRegen)
                                        )
                                )
                        )
                        .then(CommandManager.literal("per_regen_amount")
                                .then(CommandManager.literal("get")
                                        .executes(ChunkManaCommand::getPerRegenAmount)
                                )
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ChunkManaCommand::setPerRegenAmount)
                                        )
                                )
                        )
                )
        );
    }

    private static int getAll(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        ManaDataObject manaObject = ManaAmount.getOrCreateManaData(chunk);
        Text text = Text.translatable("command.astelfa.get_all",
                manaObject.mana_amount,
                manaObject.local_maximum,
                manaObject.tick_to_regen,
                manaObject.per_regen_amount
        );

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
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

    private static int getLocalMaximum(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        final Text text = Text.translatable("command.astelfa.local_maximum", ManaAmount.getOrCreateManaData(chunk).local_maximum);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setLocalMaximum(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        int local_maximum = IntegerArgumentType.getInteger(context, "amount");

        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        ManaAmount.getOrCreateManaData(chunk).local_maximum = local_maximum;
        final Text text = Text.translatable("command.astelfa.local_maximum", local_maximum);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int getTickToRegen(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        final Text text = Text.translatable("command.astelfa.tick_to_regen", ManaAmount.getOrCreateManaData(chunk).tick_to_regen);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setTickToRegen(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        int tick_to_regen = IntegerArgumentType.getInteger(context, "amount");

        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        ManaAmount.getOrCreateManaData(chunk).tick_to_regen = tick_to_regen;
        final Text text = Text.translatable("command.astelfa.tick_to_regen", tick_to_regen);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int getPerRegenAmount(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        final Text text = Text.translatable("command.astelfa.per_regen_amount", ManaAmount.getOrCreateManaData(chunk).per_regen_amount);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int setPerRegenAmount(@NotNull CommandContext<ServerCommandSource> context) {
        Vec2f position = Vec2ArgumentType.getVec2(context, "position");
        int per_regen_amount = IntegerArgumentType.getInteger(context, "amount");

        WorldChunk chunk = context.getSource().getWorld().getChunk(ChunkSectionPos.getSectionCoord(position.x), ChunkSectionPos.getSectionCoord(position.y));
        ManaAmount.getOrCreateManaData(chunk).per_regen_amount = per_regen_amount;
        final Text text = Text.translatable("command.astelfa.per_regen_amount", per_regen_amount);

        context.getSource().sendFeedback(() -> text, false);
        return Command.SINGLE_SUCCESS;
    }
}
