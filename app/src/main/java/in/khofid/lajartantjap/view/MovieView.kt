package `in`.khofid.lajartantjap.view

import `in`.khofid.lajartantjap.model.Movie

interface MovieView {
    fun showLoading()
    fun hideLoading()
    fun loadMovies(data: List<Movie>)
    fun movieNotFound()
}