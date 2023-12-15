package com.dublikunt.astelfa.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class ManaFillerCriterion extends AbstractCriterion<ManaFillerCriterion.Conditions> {
    @Override
    public Codec<ManaFillerCriterion.Conditions> getConditionsCodec() {
        return ManaFillerCriterion.Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, RegistryEntry<Item> item) {
        this.trigger(player, conditions -> conditions.matches(item));
    }

    public record Conditions(Optional<LootContextPredicate> player,
                             Optional<RegistryEntry<Item>> item) implements AbstractCriterion.Conditions {
        public static final Codec<ManaFillerCriterion.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codecs.createStrictOptionalFieldCodec(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC, "player").forGetter(ManaFillerCriterion.Conditions::player),
                                Codecs.createStrictOptionalFieldCodec(Registries.ITEM.createEntryCodec(), "item").forGetter(ManaFillerCriterion.Conditions::item)
                        )
                        .apply(instance, ManaFillerCriterion.Conditions::new)
        );

        public static AdvancementCriterion<ManaFillerCriterion.Conditions> any() {
            return ModCriterion.MANA_FILLER.create(new ManaFillerCriterion.Conditions(Optional.empty(), Optional.empty()));
        }

        public boolean matches(RegistryEntry<Item> item) {
            return this.item.isEmpty() || this.item.get().equals(item);
        }
    }
}
