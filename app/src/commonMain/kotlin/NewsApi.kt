import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
class NewsApi {
    @ThreadLocal
    companion object {
        val shared = NewsApi()
    }

    val networkService = NetworkService()

    suspend fun getNewsList(completed: (ContentResponse<NewsList>)->Unit){
        var contentResponse = ContentResponse<List<NewsItem>>()
        val path = "v2/top-headlines?language=en"
        networkService.getData(path, NewsList.serializer(),completed)
    }
}