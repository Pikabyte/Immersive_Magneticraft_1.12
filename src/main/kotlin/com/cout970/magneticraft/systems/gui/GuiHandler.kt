package com.cout970.magneticraft.systems.gui

import com.cout970.magneticraft.features.automatic_machines.TileInserter
import com.cout970.magneticraft.features.electric_machines.TileAirLock
import com.cout970.magneticraft.features.electric_machines.TileElectricEngine
import com.cout970.magneticraft.features.heat_machines.TileCombustionChamber
import com.cout970.magneticraft.features.heat_machines.TileSteamBoiler
import com.cout970.magneticraft.features.multiblocks.tileentities.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

/**
 * This class handles which GUI should be opened when a block or item calls player.openGui(...)
 */
object GuiHandler : IGuiHandler {

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val container = getServerGuiElement(ID, player, world, x, y, z) as? ContainerBase ?: return null

        // @formatter:off
        return when (container) {
            is AutoContainer                 -> AutoGui(container)
            else -> null
        }
        // @formatter:on
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val pos = BlockPos(x, y, z)

        @Suppress("MoveVariableDeclarationIntoWhen")
        val tile = world.getTileEntity(pos)

        // @formatter:off
        return when (tile) {
            is TileCombustionChamber    -> autoContainer("combustion_chamber", GuiBuilder::combustionChamberGui, tile, player, world, pos)
            is TileSteamBoiler          -> autoContainer("steam_boiler", GuiBuilder::steamBoilerGui, tile, player, world, pos)
            is TileSieve                -> autoContainer("sieve", GuiBuilder::sieveGui, tile, player, world, pos)
            is TileSteamEngine          -> autoContainer("steam_engine", GuiBuilder::steamEngineGui, tile, player, world, pos)
            is TileBigCombustionChamber -> autoContainer("big_combustion_chamber", GuiBuilder::bigCombustionChamberGui, tile, player, world, pos)
            is TileBigSteamBoiler       -> autoContainer("big_steam_boiler", GuiBuilder::bigSteamBoilerGui, tile, player, world, pos)
            is TileInserter             -> autoContainer("inserter", GuiBuilder::inserterGui, tile, player, world, pos)
            is TileHydraulicPress       -> autoContainer("hydraulic_press", GuiBuilder::hydraulicPressGui, tile, player, world, pos)
            is TileShelvingUnit         -> autoContainer("shelving_unit", GuiBuilder::shelvingUnitGui, tile, player, world, pos)
            is TileElectricEngine       -> autoContainer("electric_engine", GuiBuilder::electricEngineGui, tile, player, world, pos)
            is TileAirLock              -> autoContainer("airlock", GuiBuilder::airlockGui, tile, player, world, pos)
            else -> null
        }
        // @formatter:on
    }

    private fun <T : ContainerBase> guiOf(container: T, func: (GuiBase, T) -> Unit): GuiBase {
        return object : GuiBase(container) {
            override fun initComponents() {
                func(this, container)
            }
        }
    }
}