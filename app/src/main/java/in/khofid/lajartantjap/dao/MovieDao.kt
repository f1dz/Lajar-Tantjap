package `in`.khofid.lajartantjap.dao

import `in`.khofid.lajartantjap.model.Movie
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM Movies")
    val all: List<Movie>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getById(id: Int?): Movie

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}