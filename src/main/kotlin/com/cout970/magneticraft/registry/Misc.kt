package com.cout970.magneticraft.registry

import com.cout970.magneticraft.Debug
import com.cout970.magneticraft.api.internal.registries.tool.hammer.Hammer
import com.cout970.magneticraft.api.internal.registries.tool.hammer.HammerRegistry
import com.cout970.magneticraft.api.internal.registries.tool.wrench.WrenchRegistry
import com.cout970.magneticraft.misc.info
import com.cout970.magneticraft.misc.inventory.stack
import net.minecraft.init.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries

fun registerMisc() {
    registerHammer("immersiveengineering:tool")

    if (Debug.DEBUG) {
        WrenchRegistry.registerWrench(Items.STICK.stack())
    }
    registerWrench("bigreactors:wrench")
    registerWrench("pneumaticcraft:pneumatic_wrench")
    registerWrench("rftools:smartwrench")
    registerWrench("refinedstorage:wrench")
    registerWrench("thermalfoundation:wrench")
    registerWrench("immersiveengineering:tool")
    registerWrench("pneumaticcraft:logistics_configurator")
    registerWrench("buildcraftcore:wrench")
    registerWrench("teslacorelib:wrench")
    registerWrench("actuallyadditions:item_laser_wrench")
    registerWrench("enderio:item_yeta_wrench")
    registerWrench("extrautils2:wrench")
}

private fun registerWrench(resource: String, meta: Int = 0) {
    ForgeRegistries.ITEMS.getValue(ResourceLocation(resource))?.let {
        info("Adding item: $it as valid wrench")
        WrenchRegistry.registerWrench(it.stack(meta = meta))
    }
}

private fun registerHammer(resource: String, meta: Int = 0) {
    ForgeRegistries.ITEMS.getValue(ResourceLocation(resource))?.let {
        info("Adding item: $it as valid wrench")
        HammerRegistry.registerHammer(it.stack(meta = meta),  Hammer(4, 15, 1))
    }
}