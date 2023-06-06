package com.dublikunt.astelfa.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// CREDIT: https://github.com/mezz/JustEnoughItems
public interface IIngredientRenderer<T> {
    /**
     * Get the tooltip text for this ingredient. JEI renders the tooltip based on this.
     *
     * @param ingredient  The ingredient to get the tooltip for.
     * @param tooltipFlag Whether to show advanced information on item tooltips, toggled by F3+H
     * @return The tooltip text for the ingredient.
     */
    List<Text> getTooltip(T ingredient, TooltipContext tooltipFlag);

    /**
     * Get the tooltip font renderer for this ingredient. JEI renders the tooltip based on this.
     *
     * @param minecraft  The minecraft instance.
     * @param ingredient The ingredient to get the tooltip for.
     * @return The font renderer for the ingredient.
     */
    default TextRenderer getFontRenderer(@NotNull MinecraftClient minecraft, T ingredient) {
        return minecraft.textRenderer;
    }

    /**
     * Get the width of the ingredient drawn on screen by this renderer.
     */
    default int getWidth() {
        return 16;
    }

    /**
     * Get the height of the ingredient drawn on screen by this renderer.
     */
    default int getHeight() {
        return 16;
    }
}