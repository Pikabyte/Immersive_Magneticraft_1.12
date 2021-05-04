package com.cout970.magneticraft.systems.multiblocks

import blusunrize.immersiveengineering.common.IEContent
import net.minecraft.block.Block
import com.cout970.magneticraft.features.multiblock_parts.Blocks as MultiblockParts
import com.cout970.magneticraft.features.multiblocks.Blocks as Multiblocks


fun Multiblock.ofBlock(block: Block): SingleBlockComponent {
    return SingleBlockComponent(block.defaultState, Multiblocks.gap.defaultState)
}

fun Multiblock.grateBlock(): SingleBlockComponent {
    val block = MultiblockParts.PartType.GRATE.getBlockState(MultiblockParts.parts)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.corrugatedBlock(): SingleBlockComponent {
    val block = MultiblockParts.PartType.CORRUGATED_IRON.getBlockState(MultiblockParts.parts)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.pressureHeadBlock(): SingleBlockComponent {
    val block = MultiblockParts.PartType.BASE.getBlockState(MultiblockParts.parts)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.sheetmetalSteelBlock(): SingleBlockComponent {
    val block = IEContent.blockSheetmetal.getStateFromMeta(8)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.sheetmetalIronBlock(): SingleBlockComponent {
    val block = IEContent.blockSheetmetal.getStateFromMeta(9)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.steelBlock(): SingleBlockComponent {
    val block = IEContent.blockStorage.getStateFromMeta(8)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.dynamoBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDevice1.getStateFromMeta(2)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.lightEngineeringBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDecoration0.getStateFromMeta(4)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.heavyEngineeringBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDecoration0.getStateFromMeta(5)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.fenceSteelBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDecoration1.getStateFromMeta(0)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.scaffoldingSteelBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDecoration1.getStateFromMeta(1)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.scaffoldingSlabSteelBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDecorationSlabs1.getStateFromMeta(1)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.barrelIronBlock(): SingleBlockComponent {
    val block = IEContent.blockMetalDevice0.getStateFromMeta(4)
    return SingleBlockComponent(block, Multiblocks.gap.defaultState)
}

fun Multiblock.mainBlockOf(it: Block): MainBlockComponent {
    return MainBlockComponent(it) { context, activate ->
        Multiblocks.MultiblockOrientation.of(context.facing, activate).getBlockState(it)
    }
}