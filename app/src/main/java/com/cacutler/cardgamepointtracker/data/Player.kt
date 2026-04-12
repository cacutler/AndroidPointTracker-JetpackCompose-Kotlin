package com.cacutler.cardgamepointtracker.data
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID
@Entity(tableName = "players", foreignKeys = [ForeignKey(entity = Game::class, parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)], indices = [Index("gameId")])
data class Player(@PrimaryKey val id: String = UUID.randomUUID().toString(), val gameId: String, val name: String, val score: Int = 0)
data class PlayerWithScores(@Embedded val player: Player, @Relation(parentColumn = "id", entityColumn = "playerId") val scoreHistory: List<ScoreEntry>)