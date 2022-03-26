package ani.saikou.routes

import ani.saikou.dataclasses.PostURL
import ani.saikou.serviceMap
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loadEpisodes() {
    post("/loadepisodes/{service}") {
        val mediaObj = call.receive<PostURL>().also { println(it) }
        call.respond(
            serviceMap[call.parameters["service"]]!!.loadEpisodes(mediaObj.url)
        )
    }
}