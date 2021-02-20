package net.savagelabs.dockerdocumenter

import net.savagelabs.dockerdocumenter.docs.DocumentationProvider
import net.savagelabs.dockerdocumenter.util.log
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.plugin.java.JavaPlugin

class Documenter : JavaPlugin(), Listener {

    companion object {
        lateinit var INSTANCE: Documenter
        val providers = ArrayList<DocumentationProvider>()
    }

    override fun onEnable() {
        INSTANCE = this
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onDisable() {
        providers.clear()
    }

    fun registerDocumentationProvider(documentationProvider: DocumentationProvider) {
        providers.add(documentationProvider)
    }

    @EventHandler
    fun onLoad(event: ServerLoadEvent) {
        log("server loaded --- started processing...")
        providers.forEach {
            log("Processing ${it.identifier}...")
            it.generateDocs()
        }
    }





}