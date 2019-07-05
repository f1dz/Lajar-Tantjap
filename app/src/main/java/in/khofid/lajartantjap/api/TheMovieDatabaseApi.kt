package `in`.khofid.lajartantjap.api

import `in`.khofid.lajartantjap.BuildConfig

object TheMovieDatabaseApi {

    private const val ENDPOINT = "https://api.themoviedb.org/3/"
    private val API_KEY = BuildConfig.TMDB_API_KEY

    fun getPopularMovies(language: String = "en-EN") = "${ENDPOINT}movie/popular?api_key=${API_KEY}&language=${language}"
    fun getPopularTvShows(language: String = "en-EN") = "${ENDPOINT}tv/popular?api_key=${API_KEY}&language=${language}"
}