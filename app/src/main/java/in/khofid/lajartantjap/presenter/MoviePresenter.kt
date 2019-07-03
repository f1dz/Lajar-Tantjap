package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.model.MovieResponse
import `in`.khofid.lajartantjap.view.MovieView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviePresenter(
    private val view: MovieView,
    private val apiRepository: ApiRepository = ApiRepository(),
    private val gson: Gson = Gson()
) {
    fun getMovieList(){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheMovieDatabaseApi.getPopularMovies()).await(),
                MovieResponse::class.java)

            view.loadMovies(data.results)
            view.hideLoading()
        }
    }
}