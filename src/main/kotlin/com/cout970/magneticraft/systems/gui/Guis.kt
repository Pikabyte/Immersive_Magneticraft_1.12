package com.cout970.magneticraft.systems.gui

import com.cout970.magneticraft.api.internal.registries.machines.hydraulicpress.HydraulicPressRecipeManager
import com.cout970.magneticraft.api.internal.registries.machines.sieve.SieveRecipeManager
import com.cout970.magneticraft.api.registries.machines.hydraulicpress.HydraulicPressMode
import com.cout970.magneticraft.features.automatic_machines.TileInserter
import com.cout970.magneticraft.features.electric_machines.TileAirLock
import com.cout970.magneticraft.features.electric_machines.TileElectricEngine
import com.cout970.magneticraft.features.heat_machines.TileCombustionChamber
import com.cout970.magneticraft.features.heat_machines.TileSteamBoiler
import com.cout970.magneticraft.features.multiblocks.ContainerShelvingUnit
import com.cout970.magneticraft.features.multiblocks.tileentities.*
import com.cout970.magneticraft.misc.gui.SlotType
import com.cout970.magneticraft.misc.inventory.isNotEmpty
import com.cout970.magneticraft.misc.network.IBD
import com.cout970.magneticraft.misc.t
import com.cout970.magneticraft.misc.vector.Vec2d
import com.cout970.magneticraft.misc.vector.vec2Of
import com.cout970.magneticraft.systems.config.Config
import com.cout970.magneticraft.systems.gui.components.ComponentShelvingUnit
import net.minecraft.init.Items
import net.minecraft.tileentity.TileEntityFurnace

fun GuiBuilder.combustionChamberGui(tile: TileCombustionChamber) {
    container {
        slot(tile.invModule.inventory, 0, "fuel_slot", SlotType.INPUT)
        region(0, 1, filter = { it, _ -> it.isNotEmpty && it.item == Items.COAL })
        playerInventory()
    }
    bars {
        heatBar(tile.node)
        fuelBar(tile.combustionChamberModule)
        slotSpacer()
    }
}

fun GuiBuilder.steamBoilerGui(tile: TileSteamBoiler) {
    container {
        playerInventory()
    }
    bars {
        heatBar(tile.node)
        machineFluidBar(tile.boilerModule.production, tile.boilerModule.maxProduction)
        tank(tile.waterTank, TankIO.IN)
        tank(tile.steamTank, TankIO.OUT)
    }
}

fun GuiBuilder.sieveGui(tile: TileSieve) {
    container {
        slot(tile.inventory, 0, "input_slot", SlotType.INPUT)
        slot(tile.inventory, 1, "output_slot", SlotType.OUTPUT, blockInput = true)
        slot(tile.inventory, 2, "output2_slot", SlotType.OUTPUT, blockInput = true)
        slot(tile.inventory, 3, "output3_slot", SlotType.OUTPUT, blockInput = true)
        region(0, 1, filter = { it, _ -> SieveRecipeManager.findRecipe(it) != null })
        region(1, 1, filter = { _, _ -> false })
        region(2, 1, filter = { _, _ -> false })
        region(3, 1, filter = { _, _ -> false })
        playerInventory()
    }
    bars {
        electricBar(tile.node)
        storageBar(tile.storageModule)
        consumptionBar(tile.processModule.consumption, Config.sieveMaxConsumption)
        progressBar(tile.processModule.timedProcess)
        drawable(vec2Of(62, 50), "arrow_offset", "arrow_size", "arrow_uv")
    }
}

fun GuiBuilder.steamEngineGui(tile: TileSteamEngine) {
    container {
        playerInventory()
    }
    bars {
        electricBar(tile.node)
        storageBar(tile.storageModule)
        productionBar(tile.steamGeneratorModule.production, tile.steamGeneratorModule.maxProduction)
        tank(tile.tank, TankIO.IN)
    }
}

fun GuiBuilder.bigCombustionChamberGui(tile: TileBigCombustionChamber) {
    container {
        slot(tile.inventory, 0, "fuel_slot")
        region(0, 1) { it, _ -> TileEntityFurnace.isItemFuel(it) }
        playerInventory()
    }

    bars {
        heatBar(tile.node)
        fuelBar(tile.bigCombustionChamberModule)
        slotSpacer()
    }
}

fun GuiBuilder.bigSteamBoilerGui(tile: TileBigSteamBoiler) {
    container {
        playerInventory()
    }

    bars {
        heatBar(tile.node)
        machineFluidBar(tile.boiler.production, tile.boiler.maxProduction)
        tank(tile.input, TankIO.IN)
        tank(tile.output, TankIO.OUT)
    }
}

fun GuiBuilder.inserterGui(tile: TileInserter) {
    container {
        slot(tile.inventory, 0, "grabbed")
        slot(tile.inventory, 1, "upgrade1")
        slot(tile.inventory, 2, "upgrade2")
        slotGroup(3, 3, tile.filters, 0, "filters", SlotType.FILTER)
        region(3, 9) { _, _ -> false }
        playerInventory()

        switchButtonState("btn0") { tile.inserterModule.whiteList }
        switchButtonState("btn1") { tile.inserterModule.useOreDictionary }
        switchButtonState("btn2") { tile.inserterModule.useMetadata }
        switchButtonState("btn3") { tile.inserterModule.useNbt }
        switchButtonState("btn4") { tile.inserterModule.canDropItems }
        switchButtonState("btn5") { tile.inserterModule.canGrabItems }

        onClick("btn0") { sendToServer(IBD().setInteger(0, 0)) }
        onClick("btn1") { sendToServer(IBD().setInteger(0, 1)) }
        onClick("btn2") { sendToServer(IBD().setInteger(0, 2)) }
        onClick("btn3") { sendToServer(IBD().setInteger(0, 3)) }
        onClick("btn4") { sendToServer(IBD().setInteger(0, 4)) }
        onClick("btn5") { sendToServer(IBD().setInteger(0, 5)) }

        receiveDataFromClient {
            it.getInteger(0) { prop ->
                val mod = tile.inserterModule
                when (prop) {
                    0 -> mod.whiteList = !mod.whiteList
                    1 -> mod.useOreDictionary = !mod.useOreDictionary
                    2 -> mod.useMetadata = !mod.useMetadata
                    3 -> mod.useNbt = !mod.useNbt
                    4 -> mod.canDropItems = !mod.canDropItems
                    5 -> mod.canGrabItems = !mod.canGrabItems
                }
            }
        }
    }

    bars {
        slotSpacer(3, 3)
        slotSpacer(1, 2)

        group(vec2Of(38, 58)) {
            switchButton("btn0", "btn0_offset", "btn0_on", "btn0_off", t("gui.magneticraft.inserter.btn0"), t("gui.magneticraft.inserter.btn0_off"))
            switchButton("btn1", "btn1_offset", "btn1_on", "btn1_off", t("gui.magneticraft.inserter.btn1"), t("gui.magneticraft.inserter.btn1_off"))
            switchButton("btn2", "btn2_offset", "btn2_on", "btn2_off", t("gui.magneticraft.inserter.btn2"), t("gui.magneticraft.inserter.btn2_off"))
            switchButton("btn3", "btn3_offset", "btn3_on", "btn3_off", t("gui.magneticraft.inserter.btn3"), t("gui.magneticraft.inserter.btn3_off"))
            switchButton("btn4", "btn4_offset", "btn4_on", "btn4_off", t("gui.magneticraft.inserter.btn4"), t("gui.magneticraft.inserter.btn4_off"))
            switchButton("btn5", "btn5_offset", "btn5_on", "btn5_off", t("gui.magneticraft.inserter.btn5"), t("gui.magneticraft.inserter.btn5_off"))
        }
    }
}

fun GuiBuilder.hydraulicPressGui(tile: TileHydraulicPress) {
    container {
        slot(tile.inventory, 0, "input_slot", SlotType.INPUT)
        slot(tile.inventory, 1, "output_slot", SlotType.OUTPUT, blockInput = true)
        region(0, 1, filter = { it, _ -> HydraulicPressRecipeManager.findRecipe(it, tile.hydraulicPressModule.mode) != null })
        region(1, 1, filter = { _, _ -> false })
        playerInventory()

        receiveDataFromClient { data ->
            data.getInteger(0) {
                tile.hydraulicPressModule.mode = HydraulicPressMode.values()[it]
            }
        }

        onClick("btn0_0") { sendToServer(IBD().setInteger(0, 0)) }
        onClick("btn0_1") { sendToServer(IBD().setInteger(0, 1)) }
        onClick("btn0_2") { sendToServer(IBD().setInteger(0, 2)) }

        selectButtonState("btn0") {
            tile.hydraulicPressModule.mode.ordinal
        }
    }

    bars {
        electricBar(tile.node)
        storageBar(tile.storageModule)
        consumptionBar(tile.processModule.consumption, Config.hydraulicPressMaxConsumption)
        progressBar(tile.processModule.timedProcess)

        drawable(Vec2d.ZERO, "arrow_offset", "arrow_size", "arrow_uv")
        slotSpacer()
        selectButton(vec2Of(18, 68), "btn0") {
            option("opt0_offset", "opt0_background", t("gui.magneticraft.hydraulic_press.opt0"))
            option("opt1_offset", "opt1_background", t("gui.magneticraft.hydraulic_press.opt1"))
            option("opt2_offset", "opt2_background", t("gui.magneticraft.hydraulic_press.opt2"))
        }
    }
}

fun GuiBuilder.shelvingUnitGui(@Suppress("UNUSED_PARAMETER") tile: TileShelvingUnit) {
    containerClass = ::ContainerShelvingUnit

    container {
        onClick("btn1") { sendToServer(IBD().setBoolean(DATA_ID_SHELVING_UNIT_SORT, true)) }
        onClick("btn0_0") { (container as ContainerShelvingUnit).levelButton(0) }
        onClick("btn0_1") { (container as ContainerShelvingUnit).levelButton(1) }
        onClick("btn0_2") { (container as ContainerShelvingUnit).levelButton(2) }

        selectButtonState("btn0") {
            (container as ContainerShelvingUnit).currentLevel.ordinal
        }
    }

    components {
        searchBar("search0", "search_pos")
        scrollBar("scroll0", "scroll_pos", 19)
        custom { ComponentShelvingUnit() }

        clickButton("btn1", "button_offset")
        drawable("button_icon_pos", "button_icon_size", "button_icon_uv")
        selectButton("btn0") {
            option("opt0_offset", "opt0_background", t("gui.magneticraft.shelving_unit.opt0"))
            option("opt1_offset", "opt1_background", t("gui.magneticraft.shelving_unit.opt1"))
            option("opt2_offset", "opt2_background", t("gui.magneticraft.shelving_unit.opt2"))
        }
    }
}

fun GuiBuilder.electricEngineGui(tile: TileElectricEngine) {
    container {
        playerInventory()
    }

    bars {
        electricBar(tile.node)
        rfBar(tile.storage)
    }
}

fun GuiBuilder.airlockGui(tile: TileAirLock) {
    container {
        playerInventory()
    }

    bars {
        electricBar(tile.node)
    }
}