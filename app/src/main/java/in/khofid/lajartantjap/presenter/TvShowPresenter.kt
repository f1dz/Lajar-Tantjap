package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.model.TvResponse
import `in`.khofid.lajartantjap.view.tv.TvShowView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowPresenter(
    private var view: TvShowView,
    private var apiRepository: ApiRepository = ApiRepository(),
    private var gson: Gson = Gson()
) {
    fun getTvShowList(){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheMovieDatabaseApi.getPopularTvShows()).await(),
                TvResponse::class.java
            )

            view.loadTvShows(data.results)
            view.hideLoading()
        }
    }
}