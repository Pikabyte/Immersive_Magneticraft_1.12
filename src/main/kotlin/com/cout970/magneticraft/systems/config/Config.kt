package com.cout970.magneticraft.systems.config

/**
 * Created by cout970 on 16/05/2016.
 */

const val CATEGORY_GENERAL = "general"
const val CATEGORY_INSERTERS = "$CATEGORY_GENERAL.inserters"
const val CATEGORY_ENERGY = "$CATEGORY_GENERAL.energy"
const val CATEGORY_GUI = "$CATEGORY_GENERAL.gui"
const val CATEGORY_MACHINES = "$CATEGORY_GENERAL.machines"

object Config {

    var enableDirectRFUsage = true

    @ConfigValue(category = CATEGORY_INSERTERS, comment = "Default: delay between inserter animations in ticks")
    var inserterDefaultDelay = 10f

    @ConfigValue(category = CATEGORY_INSERTERS, comment = "Default: amount of items to take with an inserter on each operation")
    var inserterDefaultStackSize = 8

    @ConfigValue(category = CATEGORY_GENERAL, comment = "Set players on fire when processing blaze" +
            " rods in the crushing table")
    var crushingTableCausesFire = true

    @ConfigValue(category = CATEGORY_GENERAL, comment = "Automatically focus the search bar in the shelving unit when you enter the GUI")
    var autoSelectSearchBar = true

    @ConfigValue(category = CATEGORY_GUI, comment = "Unit of Heat to display, Celsius or Fahrenheit")
    var heatUnitCelsius = true

    @ConfigValue(category = CATEGORY_GUI, comment = "Character used to separate number like , in 1,000,000. Only one character will be used")
    var thousandsSeparator: String = ","

    @ConfigValue(category = CATEGORY_GUI, comment = "Allow players to use the gui of the combustion generator")
    var allowCombustionChamberGui = true

    @ConfigValue(category = CATEGORY_GUI, comment = "When you search something in the shelving unit the JEI search bar will update with the same search text")
    var syncShelvingUnitSearchWithJei: Boolean = false

    var wattsToFE = 1.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Conversion speed for the electric engine in RF/t")
    var electricEngineSpeed = 240

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Combustion chamber max speed in Fuel per tick")
    var combustionChamberMaxSpeed = 4.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Big combustion chamber max speed in Fuel per tick")
    var bigCombustionChamberMaxSpeed = 80.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Small boiler max steam production in mB")
    var boilerMaxProduction = 20

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Multiblock boiler max steam production in mB")
    var multiblockBoilerMaxProduction = 400

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Steam engine max production in W (J/t)")
    var steamEngineMaxProduction = 240

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Airlock: maintenance cost per Air Bubble every " +
            "4 ticks (0.2 sec)")
    var airlockBubbleCost = 1.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Airlock: cost of removing a water block")
    var airlockAirCost = 2.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Hydraulic Press Max Consumption")
    var hydraulicPressMaxConsumption = 3000.0

    @ConfigValue(category = CATEGORY_ENERGY, comment = "Sieve Max Consumption")
    var sieveMaxConsumption = 40.0
}
