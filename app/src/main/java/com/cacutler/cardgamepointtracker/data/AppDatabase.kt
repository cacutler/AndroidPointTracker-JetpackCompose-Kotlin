package com.cacutler.cardgamepointtracker.data
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [Game::class, Player::class, ScoreEntry::class, Round::class, TrickEntry::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreEntryDao(): ScoreEntryDao
    abstract fun roundDao(): RoundDao
    abstract fun trickEntryDao(): TrickEntryDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE games ADD COLUMN lowestScoreWins INTEGER NOT NULL DEFAULT 0")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS trick_entries (id TEXT PRIMARY KEY NOT NULL, playerId TEXT NOT NULL, round INTEGER NOT NULL, tricksBid INTEGER NOT NULL, FOREIGN KEY(playerId) REFERENCES players(id) ON DELETE CASCADE)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_trick_entries_playerId ON trick_entries(playerId)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_trick_entries_round ON trick_entries(round)")
            }
        }
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "point_tracker_database").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
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