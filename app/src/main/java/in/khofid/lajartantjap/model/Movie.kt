package `in`.khofid.lajartantjap.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.ContentValues
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "vote_average")
    var vote_average: Float = 0f,

    @ColumnInfo(name = "poster_path")
    var poster_path: String? = null,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String? = null,

    @ColumnInfo(name = "release_date")
    var release_date: String? = null
): Parcelable {
    companion object {
        fun fromContentValues(values: ContentValues?): Movie {
            return Movie(
                values?.getAsInteger("id"),
                values?.getAsString("title"),
                values?.getAsString("overview"),
                values!!.getAsFloat("vote_average"),
                values.getAsString("poster_path"),
                values.getAsString("backdrop_path"),
                values.getAsString("release_date")
            )
        }
    }
}