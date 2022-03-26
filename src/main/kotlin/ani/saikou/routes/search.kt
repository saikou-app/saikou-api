package ani.saikou.routes

import ani.saikou.serviceMap
import io.ktor.network.sockets.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import anilist.Media

fun Route.search() {
    post("/search/{service}") {
        val mediaObj = call.receive<Media>()
        val serviceName = call.parameters["service"]!!
        call.respond(serviceMap[serviceName]!!.search(mediaObj))
    }
}