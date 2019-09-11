package `in`.khofid.lajartantjap.dao

import `in`.khofid.lajartantjap.model.TvShow
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface TvShowDao {
    @get:Query("SELECT * FROM TvShow")
    val all: List<TvShow>

    @Query("SELECT * FROM TvShow WHERE id = :id")
    fun getById(id: Int?): TvShow?

    @Insert(onConflict = REPLACE)
    fun insert(tvShow: TvShow)

    @Delete
    fun delete(tvShow: TvShow)
}