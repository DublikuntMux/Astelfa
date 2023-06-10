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
                        .requireRestart()
                        .build());

        builder.getOrCreateCategory(Text.translatable("config.astelfa.category.2.title"))
                .addEntry(entryBuilder
                        .startIntSlider(Text.translatable("config.astelfa.category.2.change_lose"),
                                Astelfa.config.loseChange, 20, 300)
                        .setTooltip(Text.translatable("config.astelfa.category.2.change_lose.tooltip"))
                        .setDefaultValue(30)
                        .setSaveConsumer(value -> Astelfa.config.loseChange = value)
                        .requireRestart()
                        .build())
                .addEntry(entryBuilder
                        .startIntSlider(Text.translatable("config.astelfa.category.2.hungry_change"),
                                Astelfa.config.hungryChange, 1, 100)
                        .setTooltip(Text.translatable("config.astelfa.category.2.hungry_change.tooltip"))
                        .setDefaultValue(5)
                        .setSaveConsumer(value -> Astelfa.config.hungryChange = value)
                        .build())
                .addEntry(entryBuilder
                        .startIntSlider(Text.translatable("config.astelfa.category.2.switch_change"),
                                Astelfa.config.switchChange, 1, 100)
                        .setTooltip(Text.translatable("config.astelfa.category.2.switch_change.tooltip"))
                        .setDefaultValue(20)
                        .setSaveConsumer(value -> Astelfa.config.switchChange = value)
                        .build())
                .addEntry(entryBuilder
                        .startIntSlider(Text.translatable("config.astelfa.category.2.illusion_change"),
                                Astelfa.config.illusionChange, 100, 10_000)
                        .setTooltip(Text.translatable("config.astelfa.category.2.illusion_change.tooltip"))
                        .setDefaultValue(1_000)
                        .setSaveConsumer(value -> Astelfa.config.illusionChange = value)
                        .requireRestart()
                        .build())
                .addEntry(entryBuilder
                        .startIntSlider(Text.translatable("config.astelfa.category.2.climbing_change"),
                                Astelfa.config.climbingChange, 10, 1_000)
                        .setTooltip(Text.translatable("config.astelfa.category.2.climbing_change.tooltip"))
                        .setDefaultValue(100)
                        .setSaveConsumer(value -> Astelfa.config.climbingChange = value)
                        .requireRestart()
                        .build());

        return builder.build();
    }
}
