package com.dublikunt.astelfa.helper;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class FluidStack {
    public FluidVariant fluidVariant;
    public long amount;

    public FluidStack(FluidVariant variant, long amount) {
        this.fluidVariant = variant;
        this.amount = amount;
    }

    public static long convertDropletsToMb(long droplets) {
        return (droplets / 81);
    }

    public static long convertMbToDroplets(long mb) {
        return mb * 81;
    }

    public FluidVariant getFluidVariant() {
        return this.fluidVariant;
    }

    public void setFluidVariant(FluidVariant fluidVariant) {
        this.fluidVariant = fluidVariant;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
