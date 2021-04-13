package net.savagelabs.dockerdocumenter.docs

import net.savagelabs.dockerdocumenter.util.log
import java.lang.StringBuilder

abstract class DocumentationProvider {

    abstract fun getIdentifier(): String

    abstract fun getCommands(): List<DocCommand>

    abstract fun getPermissions(): List<DocPermission>

    fun generateDocs(): String {
        val commandContent = StringBuilder()
        getCommands().forEach { command ->
            log("Processing command ${command.name}")
            processSubCommands(command, commandContent, 0)
        }

        return commandContent.toString()
    }


    private fun processSubCommands(command: DocCommand, commandString: StringBuilder, depth: Int) {
        for (child in command.children) {
            // this possibly goes after??
            if (child.children.isNotEmpty()) {
                processSubCommands(child, commandString, depth + 1)
            }
            // append tabs for depth.
            repeat(depth) {
                commandString.append("\t")
            }

            commandString.append("${child.name} - ${child.helpInfo}\n")
        }
    }

}