package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.model.MovieResponse
import `in`.khofid.lajartantjap.view.movie.MovieView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviePresenter(
    private val view: MovieView,
    private val apiRepository: ApiRepository = ApiRepository(),
    private val gson: Gson = Gson()
) {
    fun getMovieList(lang: String){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheMovieDatabaseApi.getPopularMovies(lang)).await(),
                MovieResponse::class.java)

            if(data.results.isNotEmpty())
                view.loadMovies(data.results)
            else
                view.movieNotFound()
            view.hideLoading()
        }
    }
}