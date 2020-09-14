import kotlinx.coroutines.launch

class NewsPresenter:BasePresenter<INewsListView>(defaultDispatcher){
    var service: NewsApi = NewsApi.shared
    var data: ArrayList<NewsItem> = arrayListOf()


    fun loadData() {
        //запускаем в скоупе
        scope.launch {
            service.getNewsList {
                val result = it
                if (result.errorResponse == null) {
                    data = arrayListOf()
                    data.addAll(result.content?.articles ?: arrayListOf())

                    view?.setupNews(data)
                }
            }
        }
    }
}