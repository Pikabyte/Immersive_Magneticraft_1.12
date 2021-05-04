package com.cout970.magneticraft.features.multiblocks.tileentities

import com.cout970.magneticraft.api.internal.energy.ElectricNode
import com.cout970.magneticraft.features.multiblocks.structures.MultiblockSteamEngine
import com.cout970.magneticraft.misc.ElectricConstants
import com.cout970.magneticraft.misc.RegisterTileEntity
import com.cout970.magneticraft.misc.fluid.Tank
import com.cout970.magneticraft.misc.fluid.wrapWithFluidFilter
import com.cout970.magneticraft.misc.tileentity.DoNotRemove
import com.cout970.magneticraft.registry.ELECTRIC_NODE_HANDLER
import com.cout970.magneticraft.systems.config.Config
import com.cout970.magneticraft.systems.multiblocks.Multiblock
import com.cout970.magneticraft.systems.tilemodules.*
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos

@RegisterTileEntity("steam_engine")
class TileSteamEngine : TileMultiblock(), ITickable {

    override fun getMultiblock(): Multiblock = MultiblockSteamEngine

    val tank = Tank(16_000)
    val node = ElectricNode(ref)

    val fluidModule = ModuleFluidHandler(tank, capabilityFilter = wrapWithFluidFilter { it.fluid.name == "steam" })

    val guiModule = ModuleOpenGui()

    val storageModule = ModuleInternalStorage(
        mainNode = node,
        capacity = 80_000,
        lowerVoltageLimit = ElectricConstants.TIER_1_GENERATORS_MAX_VOLTAGE - 5.0,
        upperVoltageLimit = ElectricConstants.TIER_1_GENERATORS_MAX_VOLTAGE - 5.0
    )

    val steamGeneratorModule = ModuleSteamGenerator(
        steamTank = tank,
        node = node,
        maxProduction = Config.steamEngineMaxProduction
    )

    val steamEngineMbModule = ModuleSteamEngineMb(
        facingGetter = { facing },
        steamProduction = steamGeneratorModule.production,
        guiModule = guiModule
    )

    val ioModule: ModuleMultiblockIO = ModuleMultiblockIO(
        facing = { facing },
        connectionSpots = listOf(ConnectionSpot(
            capability = ELECTRIC_NODE_HANDLER!!,
            pos = BlockPos(-2, 0, -2),
            side = EnumFacing.UP,
            getter = { if (active) energyModule else null }
        ), ConnectionSpot(
            capability = ELECTRIC_NODE_HANDLER!!,
            pos = BlockPos(-2, 0, -2),
            side = EnumFacing.SOUTH,
            getter = { if (active) energyModule else null }
        ))
    )

    val energyModule = ModuleElectricity(
        electricNodes = listOf(node),
        connectableDirections = ioModule::getElectricConnectPoints,
        capabilityFilter = { false }
    )

    override val multiblockModule = ModuleMultiblockCenter(
        multiblockStructure = getMultiblock(),
        facingGetter = { facing },
        capabilityGetter = ioModule::getCapability,
        dynamicCollisionBoxes = steamEngineMbModule::getDynamicCollisionBoxes
    )

    init {
        initModules(multiblockModule, fluidModule, energyModule, storageModule, steamGeneratorModule,
            steamEngineMbModule, ioModule, guiModule)
    }

    @DoNotRemove
    override fun update() {
        super.update()
    }
}