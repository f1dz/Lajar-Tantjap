package `in`.khofid.favorite.utils

import `in`.khofid.favorite.model.Movie
import android.database.Cursor

class MappingHelper {

    companion object {
        fun mapCursorToList(cursor: Cursor): List<Movie> {
            var movies = mutableListOf<Movie>()

            while (cursor.moveToNext()) {
                val movie = Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("overview")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("vote_average")),
                    cursor.getString(cursor.getColumnIndexOrThrow("poster_path")),
                    cursor.getString(cursor.getColumnIndexOrThrow("backdrop_path")),
                    cursor.getString(cursor.getColumnIndexOrThrow("release_date"))
                )
                movies.add(movie)
            }

            return movies
        }
    }

}