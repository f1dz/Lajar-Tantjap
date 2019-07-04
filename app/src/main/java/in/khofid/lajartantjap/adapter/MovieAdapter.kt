package `in`.khofid.lajartantjap.adapter

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*

class MovieAdapter(
    private val context: Context,
    private var movies: List<Movie>,
    private val listener: (Movie) -> Unit
): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bindItem(movies[pos], listener)
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(movie: Movie, listener: (Movie) -> Unit){
        Picasso.get().load(Constants.IMG_URL + movie.poster_path).into(itemView.imgPoster)
        itemView.tvTitle.text = movie.title
        itemView.tvYear.text = movie.release_date?.getYear()
        itemView.ratingBar.rating = movie.vote_average/2
        itemView.tvDescription.text = movie.overview

        itemView.setOnClickListener { listener(movie) }
    }
}