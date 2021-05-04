package com.cout970.magneticraft.features.heat_machines

import com.cout970.magneticraft.misc.RegisterRenderer
import com.cout970.magneticraft.misc.vector.vec3Of
import com.cout970.magneticraft.systems.tilerenderers.*

/**
 * Created by cout970 on 2017/08/10.
 */

@RegisterRenderer(TileCombustionChamber::class)
object TileRendererCombustionChamber : BaseTileRenderer<TileCombustionChamber>() {

    override fun init() {
        createModel(Blocks.combustionChamber,
            ModelSelector("door", FilterString("Door"))
        )
    }

    override fun render(te: TileCombustionChamber) {
        Utilities.rotateFromCenter(te.facing, 180f)
        renderModel("default")

        if (te.combustionChamberModule.doorOpen) {
            Utilities.customRotate(vec3Of(0, -135, 0), vec3Of(13.5 * PIXEL, 0.0, 0.5 * PIXEL))
        }
        renderModel("door")
    }
}

@RegisterRenderer(TileSteamBoiler::class)
object TileRendererSteamBoiler : BaseTileRenderer<TileSteamBoiler>() {

    override fun init() {
        createModel(Blocks.steamBoiler)
    }

    override fun render(te: TileSteamBoiler) {
        renderModel("default")
    }
}