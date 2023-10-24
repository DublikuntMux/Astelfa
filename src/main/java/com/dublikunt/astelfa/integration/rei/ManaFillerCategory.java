package com.dublikunt.astelfa.integration.rei;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.block.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ManaFillerCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(Astelfa.MOD_ID, "textures/gui/mana_filler_rei.png");
    public static final CategoryIdentifier<ManaFillerDisplay> MANA_FILLER =
            CategoryIdentifier.of(Astelfa.MOD_ID, "mana_filler");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return MANA_FILLER;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.astelfa.mana_filler");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.MANA_FILLER_BLOCK.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(@NotNull BasicDisplay display, @NotNull Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 81)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 42, startPoint.y + 30))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 59, startPoint.y + 7))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 59, startPoint.y + 53))
                .entries(display.getInputEntries().get(2)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 120, startPoint.y + 30))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
