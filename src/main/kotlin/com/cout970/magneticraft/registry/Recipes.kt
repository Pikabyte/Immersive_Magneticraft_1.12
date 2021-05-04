package com.cout970.magneticraft.registry

import com.cout970.magneticraft.api.internal.registries.machines.crushingtable.CrushingTableRecipeManager
import com.cout970.magneticraft.api.internal.registries.machines.hydraulicpress.HydraulicPressRecipeManager
import com.cout970.magneticraft.api.internal.registries.machines.sieve.SieveRecipeManager
import com.cout970.magneticraft.api.internal.registries.machines.sluicebox.SluiceBoxRecipeManager
import com.cout970.magneticraft.api.registries.machines.hydraulicpress.HydraulicPressMode
import com.cout970.magneticraft.api.registries.machines.hydraulicpress.HydraulicPressMode.MEDIUM
import com.cout970.magneticraft.misc.STANDARD_AMBIENT_TEMPERATURE
import com.cout970.magneticraft.misc.ensureNonZero
import com.cout970.magneticraft.misc.inventory.stack
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.registry.GameRegistry


/**
 * Created by cout970 on 11/06/2016.
 * Modified by Yurgen
 *
 * Called by CommonProxy to register all the recipes in the mod
 */
fun registerRecipes() {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                                                  SIEVE RECIPES
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    addSieveRecipe(Blocks.GRAVEL.stack(), Items.FLINT.stack(), 1f, Items.FLINT.stack(), 0.15f, Items.FLINT.stack(), 0.05f, 50f)
    addSieveRecipe(Blocks.SAND.stack(), Items.GOLD_NUGGET.stack(), 0.04f, Items.GOLD_NUGGET.stack(), 0.02f, Items.QUARTZ.stack(), 0.01f, 80f)
    addSieveRecipe(Blocks.SOUL_SAND.stack(), Items.QUARTZ.stack(), 0.15f, Items.QUARTZ.stack(), 0.1f, Items.QUARTZ.stack(), 0.05f, 80f)
//    addSieveRecipe(Blocks..stack(), Items.QUARTZ.stack(), 0.15f, Items.QUARTZ.stack(), 0.1f, Items.QUARTZ.stack(), 0.05f, 80f)

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                                              CRUSHING TABLE RECIPES
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // skulls
    addCrushingTableRecipe(Items.SKULL.stack(meta = 4), Items.GUNPOWDER.stack(8), true)    // creeper
    addCrushingTableRecipe(Items.SKULL.stack(meta = 0), Items.DYE.stack(8, 15), true) // skeleton
    addCrushingTableRecipe(Items.SKULL.stack(meta = 2), Items.ROTTEN_FLESH.stack(4), true) // zombie

    // rods
    addCrushingTableRecipe(Items.BLAZE_ROD.stack(), Items.BLAZE_POWDER.stack(5))
    addCrushingTableRecipe(Items.BONE.stack(), Items.DYE.stack(4, 15))
    // blocks
    addCrushingTableRecipe(Blocks.STONE.stack(), Blocks.COBBLESTONE.stack())
    addCrushingTableRecipe(Blocks.STONE.stack(1, 6), Blocks.STONE.stack(1, 5))
    addCrushingTableRecipe(Blocks.STONE.stack(1, 4), Blocks.STONE.stack(1, 3))
    addCrushingTableRecipe(Blocks.STONE.stack(1, 2), Blocks.STONE.stack(1, 1))
    addCrushingTableRecipe(Blocks.STONEBRICK.stack(), Blocks.STONEBRICK.stack(1, 2))
    addCrushingTableRecipe(Blocks.STONEBRICK.stack(1, 1), Blocks.MOSSY_COBBLESTONE.stack())
    addCrushingTableRecipe(Blocks.PRISMARINE.stack(1, 1), Blocks.PRISMARINE.stack())
    addCrushingTableRecipe(Blocks.END_BRICKS.stack(1), Blocks.END_STONE.stack(1))

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                                                SLUICE BOX RECIPES
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    addSluiceBoxRecipe(Blocks.GRAVEL.stack(), Items.FLINT.stack(), listOf(Items.FLINT.stack() to 0.15f))
    addSluiceBoxRecipe(Blocks.SAND.stack(), ItemStack.EMPTY,
            listOf(
                    Items.GOLD_NUGGET.stack() to 0.01f,
                    Items.GOLD_NUGGET.stack() to 0.005f,
                    Items.GOLD_NUGGET.stack() to 0.0025f,
                    Items.GOLD_NUGGET.stack() to 0.00125f,
                    Items.GOLD_NUGGET.stack() to 0.000625f,
                    Items.GOLD_NUGGET.stack() to 0.0003125f,
                    Items.GOLD_NUGGET.stack() to 0.00015625f,
                    Items.GOLD_NUGGET.stack() to 0.000078125f,
                    Items.GOLD_NUGGET.stack() to 0.0000390625f
            ))

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                                                  HYDRAULIC PRESS RECIPES
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //@formatter:on

    // utility
    addHydraulicPressRecipe(Blocks.STONE.stack(), Blocks.COBBLESTONE.stack(), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.STONE.stack(meta = 6), Blocks.STONE.stack(meta = 5), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.STONE.stack(meta = 4), Blocks.STONE.stack(meta = 3), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.STONE.stack(meta = 2), Blocks.STONE.stack(meta = 1), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.STONEBRICK.stack(meta = 1), Blocks.MOSSY_COBBLESTONE.stack(), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.STONEBRICK.stack(), Blocks.STONEBRICK.stack(meta = 2), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.END_BRICKS.stack(), Blocks.END_STONE.stack(), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.RED_SANDSTONE.stack(meta = 2), Blocks.RED_SANDSTONE.stack(), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.SANDSTONE.stack(meta = 2), Blocks.SANDSTONE.stack(), MEDIUM, 20f)
    addHydraulicPressRecipe(Blocks.PRISMARINE.stack(meta = 1), Blocks.PRISMARINE.stack(), MEDIUM, 20f)

}

private fun fluidOf(name: String, amount: Int) = FluidRegistry.getFluidStack(name, amount)

private fun addSmeltingRecipe(result: ItemStack, input: ItemStack) {
    if (input.isEmpty)
        throw IllegalStateException("Trying to register furnace recipe with empty input stack: $input")
    if (result.isEmpty)
        throw IllegalStateException("Trying to register furnace recipe with empty result empty stack: $result")

    GameRegistry.addSmelting(input, result, 0.1f) // i don't care about xp
}

private fun addCrushingTableRecipe(input: ItemStack, output: ItemStack, strict: Boolean = false) {
    CrushingTableRecipeManager.registerRecipe(CrushingTableRecipeManager.createRecipe(input, output, !strict))
}

private fun addSluiceBoxRecipe(input: ItemStack, output: ItemStack,
                               otherOutput: List<Pair<ItemStack, Float>> = emptyList()) {
    SluiceBoxRecipeManager.registerRecipe(SluiceBoxRecipeManager.createRecipe(input, (listOf(output to 1f) + otherOutput).toMutableList(), true))
}

private fun balancedConductivity(temp: Double): Double {
    if (temp < STANDARD_AMBIENT_TEMPERATURE) {
        return 2000.0 / ensureNonZero(STANDARD_AMBIENT_TEMPERATURE - temp)
    }
    return 1000.0 / ensureNonZero(temp - STANDARD_AMBIENT_TEMPERATURE)
}

private fun addSieveRecipe(input: ItemStack, output0: ItemStack, prob0: Float, output1: ItemStack, prob1: Float,
                           output2: ItemStack,
                           prob2: Float, duration: Float) {
    SieveRecipeManager.registerRecipe(
            SieveRecipeManager.createRecipe(input, output0, prob0, output1, prob1, output2, prob2, duration, true))
}

private fun addSieveRecipe(input: ItemStack, output0: ItemStack, prob0: Float, output1: ItemStack, prob1: Float,
                           duration: Float) {
    SieveRecipeManager.registerRecipe(
            SieveRecipeManager.createRecipe(input, output0, prob0, output1, prob1, output1, 0f, duration, true))
}

private fun addSieveRecipe(input: ItemStack, output0: ItemStack, prob0: Float, duration: Float) {
    SieveRecipeManager.registerRecipe(
            SieveRecipeManager.createRecipe(input, output0, prob0, output0, 0f, output0, 0f, duration, true))
}

private fun addHydraulicPressRecipe(input: ItemStack, output: ItemStack, mode: HydraulicPressMode, ticks: Float) {
    HydraulicPressRecipeManager.registerRecipe(HydraulicPressRecipeManager.createRecipe(input, output, ticks, mode, true))
}