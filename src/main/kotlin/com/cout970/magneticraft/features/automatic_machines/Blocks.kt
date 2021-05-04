package com.cout970.magneticraft.features.automatic_machines

import com.cout970.magneticraft.misc.CreativeTabMg
import com.cout970.magneticraft.misc.RegisterBlocks
import com.cout970.magneticraft.misc.block.get
import com.cout970.magneticraft.misc.resource
import com.cout970.magneticraft.misc.vector.*
import com.cout970.magneticraft.systems.blocks.BlockBase
import com.cout970.magneticraft.systems.blocks.BlockBuilder
import com.cout970.magneticraft.systems.blocks.CommonMethods
import com.cout970.magneticraft.systems.blocks.IBlockMaker
import com.cout970.magneticraft.systems.itemblocks.itemBlockListOf
import com.cout970.magneticraft.systems.tilerenderers.PIXEL
import com.cout970.magneticraft.systems.tilerenderers.px
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock
import net.minecraft.util.math.BlockPos

/**
 * Created by cout970 on 2017/08/10.
 */
@RegisterBlocks
object Blocks : IBlockMaker {

    lateinit var feedingTrough: BlockBase private set
    lateinit var inserter: BlockBase private set

    override fun initBlocks(): List<Pair<Block, ItemBlock>> {
        val builder = BlockBuilder().apply {
            material = Material.IRON
            creativeTab = CreativeTabMg
        }

        inserter = builder.withName("inserter").copy {
            states = CommonMethods.Orientation.values().toList()
            factory = factoryOf(::TileInserter)
            customModels = listOf(
                "model" to resource("models/block/gltf/inserter.gltf"),
                "inventory" to resource("models/block/gltf/inserter.gltf")
            )
            hasCustomModel = true
            generateDefaultItemBlockModel = false
            alwaysDropDefault = true
            boundingBox = { listOf(vec3Of(0, 0, 0) createAABBUsing vec3Of(16.px, 3.px, 16.px)) }
            //methods
            onBlockPlaced = CommonMethods::placeWithOppositeOrientation
            pickBlock = CommonMethods::pickDefaultBlock
            onActivated = CommonMethods::openGui
        }.build()

        feedingTrough = builder.withName("feeding_trough").copy {
            material = Material.WOOD
            states = CommonMethods.CenterOrientation.values().toList()
            factory = factoryOf(::TileFeedingTrough)
            factoryFilter = { it[CommonMethods.PROPERTY_CENTER_ORIENTATION]?.center ?: false }
            customModels = listOf(
                "model" to resource("models/block/mcx/feeding_trough.mcx"),
                "inventory" to resource("models/block/mcx/feeding_trough_inv.mcx")
            )
            hasCustomModel = true
            generateDefaultItemBlockModel = false
            alwaysDropDefault = true
            //methods
            pickBlock = CommonMethods::pickDefaultBlock
            onActivated = CommonMethods::delegateToModule
            blockStatesToPlace = {
                val facing = it.player.horizontalFacing
                val center = CommonMethods.CenterOrientation.of(facing, true)
                val noCenter = CommonMethods.CenterOrientation.of(facing.opposite, false)

                val thisState = it.default.withProperty(CommonMethods.PROPERTY_CENTER_ORIENTATION, center)
                val otherState = it.default.withProperty(CommonMethods.PROPERTY_CENTER_ORIENTATION, noCenter)

                listOf(BlockPos.ORIGIN to thisState, facing.toBlockPos() to otherState)
            }
            onBlockBreak = func@{
                val facing = it.state[CommonMethods.PROPERTY_CENTER_ORIENTATION]?.facing ?: return@func
                it.worldIn.destroyBlock(it.pos + facing.toBlockPos(), true)
            }
            onDrop = {
                val center = it.state[CommonMethods.PROPERTY_CENTER_ORIENTATION]?.center ?: false
                if (center) it.default else emptyList()
            }
            boundingBox = { listOf((vec3Of(0, 0, 0) createAABBUsing vec3Of(16, 12, 16)).scale(PIXEL)) }
        }.build()

        return itemBlockListOf(inserter, feedingTrough)
    }
}