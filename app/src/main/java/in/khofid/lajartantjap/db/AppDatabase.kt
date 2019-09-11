package `in`.khofid.lajartantjap.db

import `in`.khofid.lajartantjap.dao.MovieDao
import `in`.khofid.lajartantjap.dao.TvShowDao
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.model.TvShow
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Movie::class, TvShow::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        private const val DB_NAME = "lajar_tantjap_db"
        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if(dbInstance == null) {
                dbInstance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance as AppDatabase
        }
    }
}