package ru.red.recipe.calculator

import net.minecraftforge.common.config.Configuration

import java.io.File

object Config {
    var greeting = "I'm gonna do these recipes for you"

    def synchronizeConfiguration(configFile: File): Unit = {
        val configuration = new Configuration(configFile)
        greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, greeting, "How shall I greet?")
        if (configuration.hasChanged) configuration.save()
    }
}
