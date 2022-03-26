package ani.saikou.routes

import ani.saikou.dataclasses.PostURL
import ani.saikou.serviceMap
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import reference.*

fun Route.loadVideoServer() {
    post("/loadvideoserver/{service}") {
        val mediaObj = call.receive<PostURL>().also { println(it) }
        val arrayOfVideoServer = mutableListOf<VideoServer>()
        serviceMap[call.parameters["service"]]!!.loadVideoServers(mediaObj.url){
            arrayOfVideoServer.add(it)
        }
        call.respond(arrayOfVideoServer)
    }
}