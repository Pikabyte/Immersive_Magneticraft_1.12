
package com.cout970.magneticraft.registry

import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

/**
 * Created by cout970 on 2017/03/26.
 */

var items: List<Item> = emptyList()
    private set

fun initItems(registry: IForgeRegistry<Item>) {
    blocks.forEach { (_, itemBlock) -> itemBlock?.let { registry.register(it) } }
}