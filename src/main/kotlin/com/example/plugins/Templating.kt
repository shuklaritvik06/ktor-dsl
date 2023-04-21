package com.example.plugins

import io.ktor.server.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.configureTemplating() {
    routing {
        get("/html") {
            call.respondHtml {
                head {
                    link(rel = "stylesheet", href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css", type = "text/css")
                    link(rel = "stylesheet", href = "/style.css", type = "text/css")
                }
                body(classes = "d-flex flex-column justify-content-center align-items-center") {
                    h1 { +"HTML DSL Learning" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                    p {
                        +"Username: "
                        span {
                            +User(1,"Ramesh").name
                        }
                    }
                    form(action = "/login",encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                        p {
                            +"Username: "
                            textInput(name = "username"){
                                placeholder = "Username"
                            }
                        }
                        p{
                            +"First Name:"
                            textInput(name = "first"){
                                placeholder = "First Name"
                            }
                        }
                        p{
                            +"Last Name:"
                            textInput(name = "last"){
                                placeholder = "Last Name"
                            }
                        }
                        submitInput(formEncType = InputFormEncType.applicationXWwwFormUrlEncoded, classes = "btn btn-warning p-2"){value = "Login"}
                    }
                    footer {
                        +"Copyright Ritvik Shukla"
                    }
                }
            }
        }
        post("/login") {
            val data = call.receiveParameters()
            call.respond("Hello "+data["username"].toString())
        }
        get("/style.css") {
            call.respondCss {
                body {
                    backgroundColor = Color.white
                    margin(0.px)
                    padding(0.px)
                }
                rule("h1.page-title") {
                    fontSize = (33.px)
                }
            }
        }
    }
}
suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
   this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

data class User(val id: Int, val name: String)
