package com.cacutler.cardgamepointtracker.data
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID
@Entity(tableName = "games")
data class Game(@PrimaryKey val id: String = UUID.randomUUID().toString(), val name: String, val date: Long = System.currentTimeMillis(), val isActive: Boolean = true, val currentRound: Int = 1, val lowestScoreWins: Boolean = false)
data class GameWithPlayers(@Embedded val game: Game, @Relation(parentColumn = "id", entityColumn = "gameId") val players: List<Player>)