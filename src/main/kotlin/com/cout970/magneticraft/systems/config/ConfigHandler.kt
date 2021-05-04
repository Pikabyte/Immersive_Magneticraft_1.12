package com.cout970.magneticraft.systems.config

import com.cout970.magneticraft.MOD_ID
import com.cout970.magneticraft.Magneticraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.fml.client.config.IConfigElement
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.lang.reflect.Field

/**
 * Created by cout970 on 16/05/2016.
 */
object ConfigHandler {

    val instance = Config
    val wrappers = mutableListOf<FieldWrapper>()
    val config: IConfig

    init {
        config = ForgeConfiguration(Magneticraft.configFile)
        loadFields()
    }

    fun init(){
        MinecraftForge.EVENT_BUS.register(this)
        load()
        read()
        save()
    }

    @SubscribeEvent
    fun onConfigReload(event: ConfigChangedEvent.OnConfigChangedEvent){
        if(event.modID == MOD_ID){
            read()
            save()
        }
    }

    fun save() {
        if (config.hasChanged()) {
            config.save()
        }
    }

    fun load() {
        config.load()
    }

    fun read() {
        for (fw in wrappers) {
            try {
                fw.read(this)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    fun loadFields() {
        val clazz = instance::class.java
        val fields = clazz.declaredFields
        wrappers.clear()

        for (f in fields) {
            if (f.isAnnotationPresent(ConfigValue::class.java)) {
                f.isAccessible = true
                val annotation = f.getAnnotation(ConfigValue::class.java)

                when (f.type) {
                    Int::class.java -> IntegerFieldWrapper(f, annotation)
                    Double::class.java -> DoubleFieldWrapper(f, annotation)
                    Boolean::class.java -> BooleanFieldWrapper(f, annotation)
                    String::class.java -> StringFieldWrapper(f, annotation)
                    Float::class.java -> FloatFieldWrapper(f, annotation)
                    else -> null
                }?.let {
                    wrappers += it
                }
            }
        }
    }

    fun getConfigElements(): List<IConfigElement> {
        val cnf = config as ForgeConfiguration
        return listOf(
                ConfigElement(cnf.getCategory(CATEGORY_GENERAL)),
                ConfigElement(cnf.getCategory(CATEGORY_ENERGY)),
                ConfigElement(cnf.getCategory(CATEGORY_INSERTERS)),
                ConfigElement(cnf.getCategory(CATEGORY_GUI)),
                ConfigElement(cnf.getCategory(CATEGORY_MACHINES))
        )
    }

    abstract class FieldWrapper(val field: Field, val annotation: ConfigValue, val type: ConfigValueType) {

        fun getKey(): String {
            if (annotation.key == "") {
                return field.name
            }
            return annotation.key
        }

        abstract fun read(handler: ConfigHandler)
    }

    class IntegerFieldWrapper(field: Field, annotation: ConfigValue) : FieldWrapper(field, annotation,
            ConfigValueType.INT) {

        override fun read(handler: ConfigHandler) {
            val value = handler.config.getInteger(annotation.category, getKey(), field.getInt(handler.instance),
                    annotation.comment)
            field.set(handler.instance, value)
        }
    }

    class DoubleFieldWrapper(field: Field, annotation: ConfigValue) : FieldWrapper(field, annotation,
            ConfigValueType.DOUBLE) {

        override fun read(handler: ConfigHandler) {
            val value = handler.config.getDouble(annotation.category, getKey(), field.getDouble(handler.instance),
                    annotation.comment)
            field.set(handler.instance, value)
        }
    }

    class FloatFieldWrapper(field: Field, annotation: ConfigValue) : FieldWrapper(field, annotation,
            ConfigValueType.DOUBLE) {

        override fun read(handler: ConfigHandler) {
            val value = handler.config.getDouble(annotation.category, getKey(),
                    field.getFloat(handler.instance).toDouble(), annotation.comment)
            field.set(handler.instance, value.toFloat())
        }
    }

    class BooleanFieldWrapper(field: Field, annotation: ConfigValue) : FieldWrapper(field, annotation,
            ConfigValueType.BOOLEAN) {

        override fun read(handler: ConfigHandler) {
            val value = handler.config.getBoolean(annotation.category, getKey(), field.getBoolean(handler.instance),
                    annotation.comment)
            field.set(handler.instance, value)
        }
    }

    class StringFieldWrapper(field: Field, annotation: ConfigValue) : FieldWrapper(field, annotation,
            ConfigValueType.STRING) {

        override fun read(handler: ConfigHandler) {
            val value = handler.config.getString(annotation.category, getKey(), field.get(handler.instance) as String,
                    annotation.comment)
            field.set(handler.instance, value)
        }
    }
}