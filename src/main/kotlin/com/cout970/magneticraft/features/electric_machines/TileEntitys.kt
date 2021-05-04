package com.cout970.magneticraft.features.electric_machines

import com.cout970.magneticraft.api.internal.energy.ElectricNode
import com.cout970.magneticraft.misc.ElectricConstants.TIER_1_MACHINES_MIN_VOLTAGE
import com.cout970.magneticraft.misc.RegisterTileEntity
import com.cout970.magneticraft.misc.block.getFacing
import com.cout970.magneticraft.misc.energy.RfStorage
import com.cout970.magneticraft.misc.network.IBD
import com.cout970.magneticraft.misc.tileentity.DoNotRemove
import com.cout970.magneticraft.misc.tileentity.shouldTick
import com.cout970.magneticraft.misc.world.isClient
import com.cout970.magneticraft.systems.config.Config
import com.cout970.magneticraft.systems.tileentities.TileBase
import com.cout970.magneticraft.systems.tilemodules.ModuleAirlock
import com.cout970.magneticraft.systems.tilemodules.ModuleElectricity
import com.cout970.magneticraft.systems.tilemodules.ModuleRf
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.fml.relauncher.Side

/**
 * Created by cout970 on 2017/06/29.
 */

@RegisterTileEntity("airlock")
class TileAirLock : TileBase(), ITickable {

    val node = ElectricNode(ref)

    val electricModule = ModuleElectricity(
            electricNodes = listOf(node)
    )

    val airlockModule = ModuleAirlock(node)

    init {
        initModules(airlockModule, electricModule)
    }

    @DoNotRemove
    override fun update() {
        super.update()
    }
}

@RegisterTileEntity("electric_engine")
class TileElectricEngine : TileBase(), ITickable {
    val facing: EnumFacing get() = getBlockState().getFacing()

    val storage = RfStorage(80_000)
    val node = ElectricNode(ref)

    val rfModule = ModuleRf(storage)
    val electricModule = ModuleElectricity(listOf(node))

    var lastWorkingTick = 0L
    var animationStep = 0.0
    var animationSpeed = 0.0
    var animationLastTime = 0.0

    init {
        initModules(rfModule, electricModule)
    }

    @DoNotRemove
    override fun update() {
        super.update()

        if (world.isClient) return
        storage.exportTo(world, pos, facing.opposite)

        if (node.voltage < TIER_1_MACHINES_MIN_VOLTAGE) return

        val space = storage.maxEnergyStored - storage.energyStored
        if (space == 0) return

        val rf = Math.min(space, Config.electricEngineSpeed)
        node.applyPower(-rf * Config.wattsToFE, false)
        storage.energyStored += rf

        // Animation
        lastWorkingTick = world.totalWorldTime
        if (container.shouldTick(10)) {
            // Packet to client
            container.sendSyncDataToNearPlayers(IBD().apply {
                setInteger(0, 0)
                setLong(1, lastWorkingTick)
            })
        }
    }

    override fun receiveSyncData(ibd: IBD, otherSide: Side) {
        val id = ibd.getInteger(0)
        if (id == 0) {
            ibd.getLong(1) {
                lastWorkingTick = it
            }
        }
        super.receiveSyncData(ibd, otherSide)
    }
}