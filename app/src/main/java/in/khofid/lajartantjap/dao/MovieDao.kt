package `in`.khofid.lajartantjap.dao

import `in`.khofid.lajartantjap.model.Movie
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.database.Cursor

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM Movies")
    val all: List<Movie>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getById(id: Int?): Movie?

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie): Long

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM Movies WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM Movies")
    fun allFavorite(): Cursor

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getFavoriteById(id: Long): Cursor

    @Update
    fun update(movie: Movie): Int
}