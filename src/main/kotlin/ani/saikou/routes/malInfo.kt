package ani.saikou.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import okhttp3.Request
import org.jsoup.Jsoup
import reference.HttpClient

private data class MALParse(
    val name:String?,
    val type:String?,
    val openings:ArrayList<String>,
    val endings:ArrayList<String>
)

fun Route.malInfo() {
    post("/mal/{type}/{malid}") {
        val type = call.parameters["type"]!!
        val malid = call.parameters["malid"]!!

        val nameMAL:String?;
        val typeMAL:String?;
        val openings = arrayListOf<String>()
        val endings = arrayListOf<String>()

        val res = Jsoup.parse(HttpClient.newCall(
            Request.Builder().url("https://myanimelist.net/$type/$malid").build()
        ).execute().body!!.string())

        if(type == "anime") {
            val a = res.select(".title-english").text()
            nameMAL = if (a!="") a else res.select(".title-name").text()
            typeMAL = if(res.select("div.spaceit_pad > a").isNotEmpty()) res.select("div.spaceit_pad > a")[0].text() else null
            res.select(".opnening > table > tbody > tr").forEach {
                val text = it.text()
                if(!text.contains("Help improve our database"))
                    openings.add(it.text())
            }
            res.select(".ending > table > tbody > tr").forEach {
                val text = it.text()
                if(!text.contains("Help improve our database"))
                    endings.add(it.text())
            }

        } else {
            val b = res.select(".title-english").text()
            nameMAL = res.select(".h1-title").text().removeSuffix(b)
            typeMAL = if(res.select("div.spaceit_pad > a").isNotEmpty()) res.select("div.spaceit_pad > a")[0].text() else null
        }

        call.respond(MALParse(nameMAL,typeMAL,openings,endings))

    }
}