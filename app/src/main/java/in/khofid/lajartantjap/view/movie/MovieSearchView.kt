package `in`.khofid.lajartantjap.view.movie

import `in`.khofid.lajartantjap.model.Movie

interface MovieSearchView {
    fun showLoading()
    fun hideLoading()
    fun showMovieList(data: List<Movie>)
}