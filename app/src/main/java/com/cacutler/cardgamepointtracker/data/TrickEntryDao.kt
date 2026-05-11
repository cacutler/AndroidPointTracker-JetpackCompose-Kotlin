package com.cacutler.cardgamepointtracker.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TrickEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTrickEntry(entry: TrickEntry)
    @Query("SELECT * FROM trick_entries WHERE playerId = :playerId AND round = :round LIMIT 1")
    fun getTrickEntryFlow(playerId: String, round: Int): Flow<TrickEntry?>
    @Query("SELECT * FROM trick_entries WHERE playerId = :playerId ORDER BY round ASC")
    fun getAllTrickEntriesForPlayer(playerId: String): Flow<List<TrickEntry>>
    @Query("DELETE FROM trick_entries WHERE playerId IN (SELECT id FROM players WHERE gameId = :gameId)")
    suspend fun deleteAllTricksForGame(gameId: String)
    @Query("SELECT * FROM trick_entries WHERE playerId = :playerId AND round = :round LIMIT 1")
    suspend fun getTrickEntry(playerId: String, round: Int): TrickEntry?
}