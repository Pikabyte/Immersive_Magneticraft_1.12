package com.cout970.magneticraft.features.multiblocks.tilerenderers

import com.cout970.magneticraft.features.multiblocks.tileentities.TileSteamEngine
import com.cout970.magneticraft.misc.RegisterRenderer
import com.cout970.magneticraft.systems.tilerenderers.*
import com.cout970.magneticraft.features.multiblocks.Blocks as Multiblocks

@RegisterRenderer(TileSteamEngine::class)
object TileRendererSteamEngine : TileRendererMultiblock<TileSteamEngine>() {

    override fun init() {
        val gears = FilterOr(
            FilterString("gearbox_lid_side"),
            FilterString("gear_box_lid_top"),
            FilterString("gearbox_lid_lock")
        )

        val notGears = FilterAnd(
            FilterNotString("gearbox_lid_side"),
            FilterNotString("gear_box_lid_top"),
            FilterNotString("gearbox_lid_lock")
        )

        createModel(Multiblocks.steamEngine,
            ModelSelector("base", notGears),
            ModelSelector("gears", gears),

            ModelSelector("animation", notGears,
                FilterRegex("animation", FilterTarget.ANIMATION)
            )
        )
    }

    override fun render(te: TileSteamEngine) {
        Utilities.rotateFromCenter(te.facing, 0f)
        translate(-1, 0, -1)

        if (te.steamGeneratorModule.working()) {
            renderModel("animation")
        } else {
            renderModel("base")
        }

        val step = Math.max(0.0, (te.steamEngineMbModule.auxTime - ticks) / 20.0)
        val clock = if (te.steamEngineMbModule.lidOpen) 1 - step else step

        translate(-0.5 * PIXEL * clock, -2.5 * PIXEL * clock, 0)
        translate(-4 * PIXEL, 1, 0)
        rotate(-120 * clock, 0, 0, 1)
        translate(4 * PIXEL, -1, 0)
        renderModel("gears")
    }
}
