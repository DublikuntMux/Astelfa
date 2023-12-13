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
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class InfuseCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(Astelfa.MOD_ID, "textures/gui/infuse_table_rei.png");
    public static final CategoryIdentifier<InfusingDisplay> INFUSING =
            CategoryIdentifier.of(Astelfa.MOD_ID, "infusing");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return INFUSING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.astelfa.infuse_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, ModBlocks.INFUSING_TABLE_BLOCK.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(@NotNull BasicDisplay display, @NotNull Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 88, bounds.getCenterY() - 41);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 176, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 41, startPoint.y + 33))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 41, startPoint.y + 6))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 59, startPoint.y + 15))
                .entries(display.getInputEntries().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 68, startPoint.y + 33))
                .entries(display.getInputEntries().get(3)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 59, startPoint.y + 51))
                .entries(display.getInputEntries().get(4)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 41, startPoint.y + 60))
                .entries(display.getInputEntries().get(5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23, startPoint.y + 51))
                .entries(display.getInputEntries().get(6)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 14, startPoint.y + 33))
                .entries(display.getInputEntries().get(7)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23, startPoint.y + 15))
                .entries(display.getInputEntries().get(8)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 119, startPoint.y + 33))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
