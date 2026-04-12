package com.cacutler.cardgamepointtracker.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface RoundDao {
    @Insert
    suspend fun insertRound(round: Round)
    @Query("SELECT * FROM rounds WHERE gameId = :gameId ORDER BY number DESC")
    fun getRoundsForGame(gameId: String): Flow<List<Round>>
}