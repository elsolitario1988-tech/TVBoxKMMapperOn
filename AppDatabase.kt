package com.tvbox.kmmapper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tvbox.kmmapper.data.dao.AppProfileDao
import com.tvbox.kmmapper.data.dao.KeyMappingDao
import com.tvbox.kmmapper.data.models.AppProfile
import com.tvbox.kmmapper.data.models.KeyMapping

/**
 * Base de datos Room para almacenar remapeos y perfiles
 */
@Database(
    entities = [KeyMapping::class, AppProfile::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keyMappingDao(): KeyMappingDao
    abstract fun appProfileDao(): AppProfileDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "tvbox_kmmapper.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
