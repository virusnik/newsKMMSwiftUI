
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import kotlinx.coroutines.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.list

interface INetworkService {
    suspend fun getData(path: String, serializer: KSerializer,completed: (ContentResponse)->Unit)
}

class NetworkService : INetworkService{
    private val httpClient = HttpClient()

    override suspend fun getData(path: String, serializer: KSerializer,completed: (ContentResponse)->Unit){
        //Для ktor используем свой скоуп
        ktorScope {

            var contentResponse = ContentResponse()

            try {

                val json = httpClient.get {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = NetworkConfig.shared.apiUrl
                        encodedPath = path
                        header("X-Api-Key", NetworkConfig.shared.apiKey)
                    }
                }
                print(json)
                val response = kotlinx.serialization.json.Json.nonstrict.parse(serializer, json)

                contentResponse.content = response
            } catch (ex: Exception) {
                val error = ErrorResponse()
                error.message = ex.message.toString()
                contentResponse.errorResponse = error
                print(ex.message.toString())
            }
            //Ответ отдаем в UI-поток
            withContext(uiDispatcher) {
                completed(contentResponse)
            }
        }
    }
}