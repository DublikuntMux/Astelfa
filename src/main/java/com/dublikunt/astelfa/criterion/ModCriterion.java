package com.dublikunt.astelfa.criterion;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriterion {
    public static ManaFillerCriterion MANA_FILLER = Criteria.register(Astelfa.MOD_ID + "/mana_filler",
            new ManaFillerCriterion());
}
