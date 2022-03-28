package ani.saikou

import ani.saikou.plugins.*
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import parsers.*
import reference.Parser

val serviceMap = mutableMapOf<String,Map<String,Parser>>(
    "anime" to mapOf(
        "nineanime" to NineAnime(),
        "gogoanime" to GogoAnime(),
        "twist" to Twist(),
        "tenshi" to Tenshi()
    ),
//    "manga" to mapOf(
//        "mangabuddy" to Nothing(),
//        "mangasee" to Nothing(),
//        "mangapill" to Nothing(),
//        "mangadex" to Nothing(),
//        "mangareader" to Nothing()
//    )
)

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CORS){
            method(HttpMethod.Options)
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            method(HttpMethod.Patch)
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            anyHost()
        }
        install(ContentNegotiation){
            jackson{
                configure(SerializationFeature.INDENT_OUTPUT, true)
                setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                    indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                    indentObjectsWith(DefaultIndenter("  ", "\n"))
                })
            }
        }
        configureRouting()
    }.start(wait = true)
}