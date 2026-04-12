package com.cacutler.cardgamepointtracker.data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID
@Entity(tableName = "score_entries", foreignKeys = [ForeignKey(entity = Player::class, parentColumns = ["id"], childColumns = ["playerId"], onDelete = ForeignKey.CASCADE)], indices = [Index("playerId"), Index("round")])
data class ScoreEntry(@PrimaryKey val id: String = UUID.randomUUID().toString(), val playerId: String, val points: Int, val round: Int, val timestamp: Long = System.currentTimeMillis())