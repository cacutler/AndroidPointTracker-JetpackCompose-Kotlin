package com.cacutler.cardgamepointtracker.data
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [Game::class, Player::class, ScoreEntry::class, Round::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreEntryDao(): ScoreEntryDao
    abstract fun roundDao(): RoundDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE games ADD COLUMN lowestScoreWins INTEGER NOT NULL DEFAULT 0")
            }
        }
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "point_tracker_database").addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
        @VisibleForTesting
        fun clearInstance() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}