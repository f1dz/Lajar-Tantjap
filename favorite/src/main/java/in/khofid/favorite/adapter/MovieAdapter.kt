package `in`.khofid.favorite.adapter

import `in`.khofid.favorite.R
import `in`.khofid.favorite.model.Movie
import `in`.khofid.favorite.utils.Const
import `in`.khofid.lajartantjap.utils.getYear
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    val context: Context,
    var movies: List<Movie>,
    val listener: (Movie) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, pos: Int) {
        holder.bindItem(movies[pos], listener)
    }
}

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(movie: Movie, listener: (Movie) -> Unit) {
        Picasso.get().load(Const.IMG_URL + movie.poster_path).into(itemView.imgPoster)
        itemView.tvTitle.text = movie.title
        itemView.tvYear.text = movie.release_date?.getYear()
        itemView.ratingBar.rating = movie.vote_average/2
        itemView.tvDescription.text = movie.overview

        itemView.setOnClickListener { listener(movie) }
    }
}