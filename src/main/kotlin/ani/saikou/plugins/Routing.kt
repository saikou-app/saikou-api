package ani.saikou.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ani.saikou.routes.*

fun Application.configureRouting() {
    routing {
        search()
        loadEpisodes()
        loadVideoServer()
        malInfo()
    }
}