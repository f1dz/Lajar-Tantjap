package `in`.khofid.favorite.ui

import `in`.khofid.favorite.adapter.MovieAdapter
import `in`.khofid.favorite.R
import `in`.khofid.favorite.model.Movie
import `in`.khofid.favorite.utils.MappingHelper
import `in`.khofid.lajartantjap.utils.hide
import `in`.khofid.lajartantjap.utils.show
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    val AUTHORITY = "in.khofid.lajartantjap"
    val TABLE_NAME = "Movies"

    var mMovies: MutableList<Movie> = mutableListOf()
    lateinit var adapter: MovieAdapter

    val CONTENT_URI = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieAdapter(this, mMovies) {
            startActivity<MovieDetailActivity>("movie" to it)
        }

        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter

        supportLoaderManager.initLoader(100, null, this)

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        progress.show()
        return CursorLoader(this, CONTENT_URI, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        mMovies.clear()
        mMovies.addAll(MappingHelper.mapCursorToList(cursor))
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        progress.hide()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mMovies.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        supportLoaderManager.restartLoader(100, null, this)
    }

}
