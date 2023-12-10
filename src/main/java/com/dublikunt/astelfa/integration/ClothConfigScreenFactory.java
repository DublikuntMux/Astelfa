package com.dublikunt.astelfa.integration;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.helper.common.LogType;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ClothConfigScreenFactory {
    static Screen genConfig(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.astelfa.title"))
                .setSavingRunnable(Astelfa.config::save);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.getOrCreateCategory(Text.translatable("config.astelfa.category.1.title"))
                .addEntry(entryBuilder
                        .startEnumSelector(
                                Text.translatable("config.astelfa.category.1.log_type"),
                                LogType.class,
                                Astelfa.config.logType)
                        .setTooltip(Text.translatable("config.astelfa.category.1.log_type.tooltip"))
                        .setDefaultValue(LogType.INFO)
                        .setSaveConsumer(value -> Astelfa.config.logType = value)
                        .build());
        builder.getOrCreateCategory(Text.translatable("config.astelfa.category.2.title"))
                .addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.astelfa.category.2.mana_overlay_x"),
                                Astelfa.config.manaOverlayPosX,
                                -1000,
                                1000)
                        .setTooltip(Text.translatable("config.astelfa.category.2.mana_overlay_x.tooltip"))
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> Astelfa.config.manaOverlayPosX = value)
                        .build())
                .addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.astelfa.category.2.mana_overlay_y"),
                                Astelfa.config.manaOverlayPosY,
                                -1000,
                                1000)
                        .setTooltip(Text.translatable("config.astelfa.category.2.mana_overlay_y.tooltip"))
                        .setDefaultValue(0)
                        .setSaveConsumer(value -> Astelfa.config.manaOverlayPosY = value)
                        .build());

        return builder.build();
    }
}
