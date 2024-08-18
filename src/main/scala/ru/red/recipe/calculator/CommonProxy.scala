package ru.red.recipe.calculator

import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import ru.red.recipe.calcualtor.Tags

class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    def preInit(event: FMLPreInitializationEvent): Unit = {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile)
        RecipeCalculator.LOG.info(Config.greeting)
        RecipeCalculator.LOG.info("I am Lazy Man's Recipe Calculator at version " + Tags.VERSION)
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    def init(event: FMLInitializationEvent): Unit = {
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    def postInit(event: FMLPostInitializationEvent): Unit = {
    }

    // register server commands in this event handler (Remove if not needed)
    def serverStarting(event: FMLServerStartingEvent): Unit = {
    }

}
