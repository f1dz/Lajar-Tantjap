package `in`.khofid.lajartantjap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    var id: Int? = null,
    var name: String? = null,
    var overview: String? = null,
    var vote_average: Int? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null,
    var first_air_date: String? = null
): Parcelable