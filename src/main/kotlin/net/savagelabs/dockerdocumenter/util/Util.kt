package net.savagelabs.dockerdocumenter.util

import net.savagelabs.dockerdocumenter.Documenter

fun log(message: String) {
    Documenter.INSTANCE.logger.info(message)
}