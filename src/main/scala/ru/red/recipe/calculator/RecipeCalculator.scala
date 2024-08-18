package ru.red.recipe.calculator

import cpw.mods.fml.common.{Mod, SidedProxy}
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import org.apache.logging.log4j.{LogManager, Logger}
import ru.red.recipe.calcualtor.Tags

@Mod(
    modid = RecipeCalculator.MOD_ID,
    version = Tags.VERSION,
    name = "Lazy Man's Recipe calculator",
    acceptedMinecraftVersions = "[1.7.10]",
)
class RecipeCalculator {
    import RecipeCalculator._

    @Mod.EventHandler // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    def preInit(event: FMLPreInitializationEvent): Unit = {
        proxy.preInit(event)
    }

    @Mod.EventHandler // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    def init(event: FMLInitializationEvent): Unit = {
        proxy.init(event)
    }

    @Mod.EventHandler // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    def postInit(event: FMLPostInitializationEvent): Unit = {
        proxy.postInit(event)
    }

    @Mod.EventHandler // register server commands in this event handler (Remove if not needed)
    def serverStarting(event: FMLServerStartingEvent): Unit = {
        proxy.serverStarting(event)
    }
}

object RecipeCalculator {
    val MOD_ID = "LazyManRecipeCalculator"
    val LOG: Logger = LogManager.getLogger(MOD_ID)

    //noinspection VarCouldBeVal
    @SidedProxy(
        clientSide = "ru.red.recipe.calculator.ClientProxy",
        serverSide = "ru.red.recipe.calculator.CommonProxy",
    )
    private var proxy: CommonProxy = _
}
