package `in`.khofid.lajartantjap.adapter

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import `in`.khofid.lajartantjap.utils.show
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*

class TvAdapter(var context: Context, var tvShows: List<TvShow>, var favorites: List<TvShow>, var listener: (TvShow) -> Unit): RecyclerView.Adapter<TvViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TvViewHolder =
        TvViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item, p0, false))

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvViewHolder, pos: Int) {
        holder.bindItem(tvShows[pos], favorites, listener)
    }
}

class TvViewHolder(view: View): RecyclerView.ViewHolder(view){
    fun bindItem(tv: TvShow, favorites: List<TvShow>, listener: (TvShow) -> Unit) {
        itemView.tvTitle.text = tv.name
        itemView.tvYear.text = tv.first_air_date?.getYear()
        itemView.ratingBar.rating = tv.vote_average/2
        itemView.tvDescription.text = tv.overview
        Picasso.get().load(Constants.IMG_URL + tv.poster_path).into(itemView.imgPoster)

        if(favorites.filter { it.id == tv.id }.size > 0)
            itemView.imgFavorite.show()

        itemView.setOnClickListener { listener(tv) }
    }
}