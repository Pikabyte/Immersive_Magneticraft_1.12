package com.cout970.magneticraft.features.automatic_machines

import com.cout970.magneticraft.misc.RegisterRenderer
import com.cout970.magneticraft.misc.inventory.get
import com.cout970.magneticraft.misc.vector.xd
import com.cout970.magneticraft.misc.vector.yd
import com.cout970.magneticraft.misc.vector.zd
import com.cout970.magneticraft.systems.tilerenderers.*
import com.cout970.modelloader.api.animation.AnimatedModel
import com.cout970.modelloader.api.util.TRSTransformation
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.item.ItemSkull
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3d
import net.minecraftforge.client.ForgeHooksClient

/**
 * Created by cout970 on 2017/08/10.
 */

@RegisterRenderer(TileFeedingTrough::class)
object TileRendererFeedingTrough : BaseTileRenderer<TileFeedingTrough>() {

    override fun init() {
        createModel(Blocks.feedingTrough)
    }

    override fun render(te: TileFeedingTrough) {

        val item = te.invModule.inventory[0]
        val level = when {
            item.count > 32 -> 4
            item.count > 16 -> 3
            item.count > 8 -> 2
            item.count > 0 -> 1
            else -> 0
        }

        Utilities.rotateFromCenter(te.facing, 90f)
        renderModel("default")
        if (level > 0) {
            Utilities.rotateFromCenter(EnumFacing.NORTH, 90f)
            stackMatrix {
                renderSide(level, item)
                rotate(180f, 0.0f, 1.0f, 0.0f)
                translate(-1.0, 0.0, -2.0)
                renderSide(level, item)
            }
        }
    }

    private fun renderSide(level: Int, item: ItemStack) {

        if (level >= 1) {
            pushMatrix()
            transform(Vec3d(2.0, 1.1, 6.0),
                    Vec3d(90.0, 0.0, 0.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()


            pushMatrix()
            transform(Vec3d(7.5, 1.5, 7.0),
                    Vec3d(90.0, 30.0, 0.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()
        }

        if (level >= 2) {
            pushMatrix()
            transform(Vec3d(0.0, 2.0, 12.0),
                    Vec3d(90.0, -30.0, 0.0),
                    Vec3d(16.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()

            pushMatrix()
            transform(Vec3d(16.5, 2.5, 12.5),
                    Vec3d(90.0, -30.0, 0.0),
                    Vec3d(16.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()
        }

        if (level >= 3) {
            pushMatrix()
            transform(Vec3d(1.0, 1.0, 7.0),
                    Vec3d(-10.0, 22.0, 4.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()

            pushMatrix()
            transform(Vec3d(0.0, 1.0, 15.0),
                    Vec3d(10.0, -22.0, -4.0),
                    Vec3d(16.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()

            pushMatrix()
            transform(Vec3d(-2.5, -2.0, -4.0),
                    Vec3d(20.0, -90.0, 5.0),
                    Vec3d(0.0, 0.0, 8.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()
        }

        if (level >= 4) {
            pushMatrix()
            transform(Vec3d(5.0, 0.0, 4.5),
                    Vec3d(-10.0, 0.0, 0.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()

            pushMatrix()
            transform(Vec3d(7.0, 0.0, 11.0),
                    Vec3d(14.0, 0.0, 0.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()

            pushMatrix()
            transform(Vec3d(12.0, 0.0, 10.0),
                    Vec3d(14.0, 0.0, 0.0),
                    Vec3d(0.0, 0.0, 0.0),
                    Vec3d(1.0, 1.0, 1.0))
            renderItem(item)
            popMatrix()
        }
    }

    private fun transform(pos: Vec3d, rot: Vec3d, rotPos: Vec3d, scale: Vec3d) {
        translate(PIXEL * 16, 0.0, 0.0)
        rotate(0, -90, 0)

        translate(pos.xd * PIXEL, pos.yd * PIXEL, pos.zd * PIXEL)

        translate(rotPos.xd * PIXEL, rotPos.yd * PIXEL, rotPos.zd * PIXEL)

        rotate(rot.xd, rot.yd, rot.zd)
        translate(-rotPos.xd * PIXEL, -rotPos.yd * PIXEL, -rotPos.zd * PIXEL)

        translate(PIXEL * 4, 0.0, 0.0)
        GlStateManager.scale(scale.xd, scale.yd, scale.zd)
    }

    private fun rotate(x: Number, y: Number, z: Number) {
        rotate(z, 0f, 0f, 1f)
        rotate(y, 0f, 1f, 0f)
        rotate(x, 1f, 0f, 0f)
    }

    private fun renderItem(stack: ItemStack) {
        Minecraft.getMinecraft().renderItem.renderItem(stack, ItemCameraTransforms.TransformType.GROUND)
    }
}

@RegisterRenderer(TileInserter::class)
object TileRendererInserter : BaseTileRenderer<TileInserter>() {

    override fun init() {
        val item = FilterNotString("item")
        createModel(Blocks.inserter,
                ModelSelector("animation0", item, FilterRegex("animation0", FilterTarget.ANIMATION)),
                ModelSelector("animation1", item, FilterRegex("animation1", FilterTarget.ANIMATION)),
                ModelSelector("animation2", item, FilterRegex("animation2", FilterTarget.ANIMATION)),
                ModelSelector("animation3", item, FilterRegex("animation3", FilterTarget.ANIMATION)),
                ModelSelector("animation4", item, FilterRegex("animation4", FilterTarget.ANIMATION)),
                ModelSelector("animation5", item, FilterRegex("animation5", FilterTarget.ANIMATION)),
                ModelSelector("animation6", item, FilterRegex("animation6", FilterTarget.ANIMATION)),
                ModelSelector("animation7", item, FilterRegex("animation7", FilterTarget.ANIMATION)),
                ModelSelector("animation8", item, FilterRegex("animation8", FilterTarget.ANIMATION)),
                ModelSelector("animation9", item, FilterRegex("animation9", FilterTarget.ANIMATION))
        )
    }

    override fun render(te: TileInserter) {
        Utilities.rotateFromCenter(te.facing, 180f)
        val mod = te.inserterModule
        val transition = mod.transition

        val extra = if (mod.moving) ticks else 0f
        time = ((mod.animationTime + extra) / mod.maxAnimationTime).coerceAtMost(1f).toDouble() * 20 * 0.33
        renderModel(transition.animation)

        val item = te.inventory[0]
        if (item.isEmpty) return

        // animation0 is used to get the articulated node because Transition.ROTATING discards the
        // info about translation/rotation of the inner nodes
        val cache0 = getModel("animation0") as? AnimationRenderCache ?: return
        val cache1 = getModel(transition.animation) as? AnimationRenderCache ?: return
        val node = cache0.model.rootNodes.firstOrNull() ?: return
        val anim = cache1.model

        val localTime = ((time / 20.0) % cache1.model.length.toDouble()).toFloat()
        val trs = getGlobalTransform(item, anim, node, localTime)

        pushMatrix()
        ForgeHooksClient.multiplyCurrentGlMatrix(trs.matrix.apply { transpose() })
        translate(0.0, (-7.5).px, 0.0)

        if (!Minecraft.getMinecraft().renderItem.shouldRenderItemIn3D(item) || item.item is ItemSkull) {
            // 2D item
            scale(0.75)
        } else {
            // 3D block
            rotate(180f, 0f, 1f, 0f)
            translate(0.0, (-1.9).px, 0.0)
            scale(0.9)
        }

        Minecraft.getMinecraft().renderItem.renderItem(item, ItemCameraTransforms.TransformType.GROUND)
        popMatrix()
    }

    fun getGlobalTransform(item: ItemStack, anim: AnimatedModel, node: AnimatedModel.Node, localTime: Float): TRSTransformation {
        val trs = anim.getTransform(node, localTime)
        if (node.children.isEmpty()) return trs
        return trs * getGlobalTransform(item, anim, node.children[0], localTime)
    }
}