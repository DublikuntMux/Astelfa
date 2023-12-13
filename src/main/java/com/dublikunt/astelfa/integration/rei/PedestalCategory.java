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

public class PedestalCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(Astelfa.MOD_ID, "textures/gui/pedestal_rei.png");
    public static final CategoryIdentifier<PedestalDisplay> PEDESTAL =
            CategoryIdentifier.of(Astelfa.MOD_ID, "pedestal");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return PEDESTAL;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.astelfa.pedestal");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, ModBlocks.PEDESTAL_BLOCK.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(@NotNull BasicDisplay display, @NotNull Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 88, bounds.getCenterY() - 51);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 176, 101)));

        double angleBetweenSlots = 360.0 / display.getInputEntries().size();

        for (int i = 0; i < display.getInputEntries().size(); i++) {
            double angle = Math.toRadians(i * angleBetweenSlots);
            double slotX = 58 + 36 * Math.cos(angle) - 18 / 2;
            double slotY = 50 + 36 * Math.sin(angle) - 18 / 2;

            widgets.add(Widgets.createSlot(new Point(startPoint.x + slotX, startPoint.y + slotY))
                    .entries(display.getInputEntries().get(i)));
        }

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 137, startPoint.y + 40))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 102;
    }
}
