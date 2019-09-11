package `in`.khofid.lajartantjap.provider

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.Movie
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MovieProvider: ContentProvider() {

    private val AUTHORITY = "in.khofid.lajartantjap"
    private val MOVIES = 1
    private val MOVIE_ITEM = 2
    private lateinit var db: AppDatabase
    private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

    private val uriMatcher = MATCHER.apply {
        addURI(AUTHORITY, "Movies", MOVIES)
        addURI(AUTHORITY, "Movies/#", MOVIE_ITEM)
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(MATCHER.match(uri)){
            MOVIES -> {
                if(context == null) return null
                var id = db.movieDao().insert(Movie.fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            MOVIE_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri)
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if(context == null) return null
        var cursor: Cursor?
        var favoriteMovies = db.movieDao()
        val code = MATCHER.match(uri)
        if(code in MOVIES..MOVIE_ITEM) {

            cursor = when (uriMatcher.match(uri)) {
                MOVIES -> favoriteMovies.allFavorite()
                MOVIE_ITEM -> favoriteMovies.getFavoriteById(ContentUris.parseId(uri))
                else -> {
                    null
                }
            }

            cursor?.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else throw IllegalArgumentException(context.getString(R.string.unknown_uri, uri))
    }

    override fun onCreate(): Boolean {
        db = AppDatabase.getDatabase(context)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        when(MATCHER.match(uri)){
            MOVIES -> throw IllegalArgumentException("Invalid URI, cannot update without ID" + uri)
            MOVIE_ITEM -> {
                if(context == null) return 0
                var movie = Movie.fromContentValues(values)
                movie.id = ContentUris.parseId(uri).toInt()
                var count = db.movieDao().update(movie)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(MATCHER.match(uri)){
            MOVIES -> throw IllegalArgumentException("Invalid URI, cannot update without ID" + uri)
            MOVIE_ITEM -> {
                if (context == null) return 0
                var count = db.movieDao().deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
    }

    override fun getType(uri: Uri): String? {
        when(MATCHER.match(uri)) {
            MOVIES -> return "vnd.android.cursor.dir/" + AUTHORITY + ".Movies"
            MOVIE_ITEM -> return "vnd.android.cursor.item/" + AUTHORITY + ".Movies"
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
    }
}