package com.cout970.magneticraft.features.electric_machines

import com.cout970.magneticraft.misc.CreativeTabMg
import com.cout970.magneticraft.misc.RegisterBlocks
import com.cout970.magneticraft.misc.block.get
import com.cout970.magneticraft.misc.resource
import com.cout970.magneticraft.systems.blocks.*
import com.cout970.magneticraft.systems.blocks.CommonMethods.propertyOf
import com.cout970.magneticraft.systems.itemblocks.itemBlockListOf
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemBlock
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.IStringSerializable

/**
 * Created by cout970 on 2017/06/29.
 */
@RegisterBlocks
object Blocks : IBlockMaker {

    val PROPERTY_DECAY_MODE: PropertyEnum<DecayMode> = propertyOf("decay_mode")
    val PROPERTY_WORKING_MODE: PropertyEnum<WorkingMode> = propertyOf("working_mode")

    lateinit var airLock: BlockBase private set
    lateinit var airBubble: BlockBase private set
    lateinit var electricEngine: BlockBase private set

    override fun initBlocks(): List<Pair<Block, ItemBlock?>> {
        val builder = BlockBuilder().apply {
            material = Material.IRON
            creativeTab = CreativeTabMg
        }

        airBubble = builder.withName("air_bubble").copy {
            states = DecayMode.values().toList()
            material = Material.GLASS
            tickRandomly = true
            blockLayer = BlockRenderLayer.CUTOUT
            hasCustomModel = true
            tickRate = 1
            onNeighborChanged = {
                if (it.state[PROPERTY_DECAY_MODE]?.enable == true && it.worldIn.rand.nextBoolean()) {
                    it.worldIn.setBlockToAir(it.pos)
                }
            }
            onUpdateTick = {
                if (it.state[PROPERTY_DECAY_MODE]?.enable == true) {
                    it.world.setBlockToAir(it.pos)
                }
            }
            onDrop = { emptyList() }
            collisionBox = { null }
            shouldSideBeRendered = func@{
                val state = it.blockAccess.getBlockState(it.pos.offset(it.side))
                if (state.block === it.state.block) false
                else !state.doesSideBlockRendering(it.blockAccess, it.pos.offset(it.side), it.side.opposite)
            }
        }.build()

        airLock = builder.withName("airlock").copy {
            factory = factoryOf(::TileAirLock)
            onActivated = CommonMethods::openGui
        }.build()

        electricEngine = builder.withName("electric_engine").copy {
            states = CommonMethods.Facing.values().toList()
            factory = factoryOf(::TileElectricEngine)
            alwaysDropDefault = true
            generateDefaultItemBlockModel = false
            hasCustomModel = true
            customModels = listOf(
                    "model" to resource("models/block/gltf/electric_engine.gltf"),
                    "inventory" to resource("models/block/gltf/electric_engine.gltf")
            )
            //methods
            onActivated = CommonMethods::openGui
            onBlockPlaced = CommonMethods::placeWithOppositeFacing
            pickBlock = CommonMethods::pickDefaultBlock
        }.build()

        return itemBlockListOf(airBubble, airLock,electricEngine)
    }

    enum class WorkingMode(
        override val stateName: String,
        override val isVisible: Boolean,
        val enable: Boolean
    ) : IStatesEnum, IStringSerializable {

        OFF("off", true, false),
        ON("on", false, true);

        override fun getName() = name.toLowerCase()
        override val properties: List<IProperty<*>> get() = listOf(PROPERTY_WORKING_MODE)

        override fun getBlockState(block: Block): IBlockState {
            return block.defaultState.withProperty(PROPERTY_WORKING_MODE, this)
        }
    }

    enum class DecayMode(
        override val stateName: String,
        override val isVisible: Boolean,
        val enable: Boolean
    ) : IStatesEnum, IStringSerializable {

        OFF("off", true, false),
        ON("on", false, true);

        override fun getName() = name.toLowerCase()
        override val properties: List<IProperty<*>> get() = listOf(PROPERTY_DECAY_MODE)

        override fun getBlockState(block: Block): IBlockState {
            return block.defaultState.withProperty(PROPERTY_DECAY_MODE, this)
        }
    }
}