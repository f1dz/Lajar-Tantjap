package `in`.khofid.lajartantjap.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TvShow")
data class TvShow(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "vote_average")
    var vote_average: Float = 0f,

    @ColumnInfo(name = "poster_path")
    var poster_path: String? = null,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String? = null,

    @ColumnInfo(name = "first_air_date")
    var first_air_date: String? = null
): Parcelable