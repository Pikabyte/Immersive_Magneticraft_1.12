package com.cout970.magneticraft.features.multiblocks.tilerenderers

import com.cout970.magneticraft.features.multiblocks.tileentities.TileBigCombustionChamber
import com.cout970.magneticraft.features.multiblocks.tileentities.TileBigSteamBoiler
import com.cout970.magneticraft.features.multiblocks.tileentities.TileHydraulicPress
import com.cout970.magneticraft.features.multiblocks.tileentities.TileSieve
import com.cout970.magneticraft.misc.RegisterRenderer
import com.cout970.magneticraft.misc.inventory.get
import com.cout970.magneticraft.misc.inventory.isNotEmpty
import com.cout970.magneticraft.systems.tilerenderers.*
import net.minecraft.client.Minecraft
import com.cout970.magneticraft.features.multiblocks.Blocks as Multiblocks

@RegisterRenderer(TileSieve::class)
object TileRendererSieve : TileRendererMultiblock<TileSieve>() {

    override fun init() {
        createModel(Multiblocks.sieve, ModelSelector("animation", FilterAlways, FilterRegex("animation", FilterTarget.ANIMATION)))
    }

    override fun render(te: TileSieve) {
        Utilities.rotateFromCenter(te.facing, 180f)
        translate(0, 0, 2)
        if (te.processModule.working) renderModel("animation") else renderModel("default")
    }
}

@RegisterRenderer(TileHydraulicPress::class)
object TileRendererHydraulicPress : TileRendererMultiblock<TileHydraulicPress>() {

    override fun init() {
        createModel(Multiblocks.hydraulicPress, ModelSelector("animation", FilterAlways, FilterRegex("animation", FilterTarget.ANIMATION)))
    }

    override fun render(te: TileHydraulicPress) {
        Utilities.rotateFromCenter(te.facing, 0f)
        translate(0, 0, -1)
        if (te.processModule.working) renderModel("animation") else renderModel("default")

        val stack = te.inventory[0]
        if (stack.isNotEmpty) {
            stackMatrix {
                translate(0.5f, 2f, 0.5f)
                if (!Minecraft.getMinecraft().renderItem.shouldRenderItemIn3D(stack)) {
                    scale(2.0)
                } else {
                    translate(0f, -PIXEL, 0f)
                }
                Utilities.renderItem(stack)
            }
        }
    }
}

@RegisterRenderer(TileBigCombustionChamber::class)
object TileRendererBigCombustionChamber : TileRendererMultiblock<TileBigCombustionChamber>() {

    override fun init() {
        createModel(Multiblocks.bigCombustionChamber,
                ModelSelector("fire_off", FilterString("fire_off")),
                ModelSelector("fire_on", FilterString("fire_on"))
        )
    }

    override fun render(te: TileBigCombustionChamber) {
        Utilities.rotateFromCenter(te.facing, 0f)
        translate(0, 0, -1)
        renderModel("default")
        if (te.bigCombustionChamberModule.working()) renderModel("fire_on") else renderModel("fire_off")
    }
}

@RegisterRenderer(TileBigSteamBoiler::class)
object TileRendererBigSteamBoiler : TileRendererMultiblock<TileBigSteamBoiler>() {

    override fun init() {
        createModel(Multiblocks.bigSteamBoiler)
    }

    override fun render(te: TileBigSteamBoiler) {
        Utilities.rotateFromCenter(te.facing, 0f)
        renderModel("default")
    }
}