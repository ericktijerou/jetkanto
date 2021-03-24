package com.ericktijerou.jetkanto.data.local.system

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ericktijerou.jetkanto.data.local.dao.RecordDao
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class KantoDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        private const val DB_NAME = "kanto_database"

        fun newInstance(context: Context): KantoDatabase {
            return Room.databaseBuilder(
                context, KantoDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
