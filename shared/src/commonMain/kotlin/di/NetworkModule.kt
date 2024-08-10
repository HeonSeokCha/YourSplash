package di

import data.api.UnSplashService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sourceModule = module {
    singleOf(::UnSplashService)
}

val provideHttpClientModule = module {
    single {
        HttpClient {
            expectSuccess = true
            defaultRequest {
                headers {
                    this.append("Accept-Version", "v1")
                    this.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
                }

                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.UNSPLAH_BASE_URL
                }
            }

//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, request ->
                    val clientException =
                        exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = exception.response
                    if (exceptionResponse.status == HttpStatusCode.Forbidden) {
                        val exceptionResponseText = exceptionResponse.bodyAsText()
                        throw ClientRequestException(exceptionResponse, exceptionResponseText)
                    }
                }
            }
        }
    }
}