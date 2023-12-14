package com.dublikunt.astelfa.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ManaFillerCriterion extends AbstractCriterion<ManaFillerCriterion.Conditions> {
    @Override
    protected Conditions conditionsFromJson(@NotNull JsonObject json,
                                            Optional<LootContextPredicate> playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        Identifier identifier = new Identifier(json.get("item").getAsString());
        Item item = Registries.ITEM.getOrEmpty(identifier).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + identifier + "'"));

        return new ManaFillerCriterion.Conditions(item);
    }

    public void trigger(ServerPlayerEntity player, Item item) {
        trigger(player, conditions -> conditions.requirementsMet(item));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final Item item;

        public Conditions(@Nullable Item item) {
            super(Optional.empty());
            this.item = item;
        }

        boolean requirementsMet(Item item) {
            return this.item == item;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = super.toJson();
            jsonObject.addProperty("item", Registries.ITEM.getId(this.item).toString());
            return jsonObject;
        }
    }
}
