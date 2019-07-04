package `in`.khofid.lajartantjap.view.tv

import `in`.khofid.lajartantjap.model.TvShow

interface TvShowView {
    fun showLoading()
    fun hideLoading()
    fun loadTvShows(tv: List<TvShow>)
}