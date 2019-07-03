package `in`.khofid.lajartantjap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int? = null,
    var title: String? = null,
    var overview: String? = null,
    var vote_average: Double? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null,
    var release_date: String? = null
): Parcelable