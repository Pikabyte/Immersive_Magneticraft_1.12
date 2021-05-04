package com.cout970.magneticraft.features.heat_machines

import com.cout970.magneticraft.api.internal.heat.HeatNode
import com.cout970.magneticraft.misc.RegisterTileEntity
import com.cout970.magneticraft.misc.block.getOrientation
import com.cout970.magneticraft.misc.fluid.Tank
import com.cout970.magneticraft.misc.inventory.Inventory
import com.cout970.magneticraft.misc.tileentity.DoNotRemove
import com.cout970.magneticraft.systems.config.Config
import com.cout970.magneticraft.systems.tileentities.TileBase
import com.cout970.magneticraft.systems.tilemodules.*
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos

/**
 * Created by cout970 on 2017/08/10.
 */

@RegisterTileEntity("combustion_chamber")
class TileCombustionChamber : TileBase(), ITickable {

    val facing: EnumFacing get() = getBlockState().getOrientation()
    val inventory = Inventory(1)
    val node = HeatNode(ref)

    val heatModule = ModuleHeat(node, capabilityFilter = { it == EnumFacing.UP })
    val invModule = ModuleInventory(inventory)
    val combustionChamberModule = ModuleCombustionChamber(node, inventory)

    init {
        initModules(invModule, combustionChamberModule, heatModule)
    }

    @DoNotRemove
    override fun update() {
        super.update()
    }
}

@RegisterTileEntity("steam_boiler")
class TileSteamBoiler : TileBase(), ITickable {

    val node = HeatNode(ref)

    val heatModule = ModuleHeat(node)

    val waterTank = Tank(
        capacity = 1000,
        allowInput = true,
        allowOutput = false,
        fluidFilter = { it.fluid.name == "water" }
    ).apply { clientFluidName = "water" }

    val steamTank = Tank(
        capacity = 16000,
        allowInput = false,
        allowOutput = true,
        fluidFilter = { it.fluid.name == "steam" }
    ).apply { clientFluidName = "steam" }

    val fluidModule = ModuleFluidHandler(waterTank, steamTank)

    val boilerModule = ModuleSteamBoiler(node, waterTank, steamTank, Config.boilerMaxProduction)

    val fluidExportModule = ModuleFluidExporter(steamTank, { listOf(BlockPos(0, 1, 0) to EnumFacing.DOWN) })
    val openGui = ModuleOpenGui()

    init {
        initModules(fluidModule, boilerModule, fluidExportModule, openGui, heatModule)
    }

    @DoNotRemove
    override fun update() {
        super.update()
    }
}