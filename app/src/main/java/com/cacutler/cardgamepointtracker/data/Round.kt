package com.cacutler.cardgamepointtracker.data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID
@Entity(tableName = "rounds", foreignKeys = [ForeignKey(entity = Game::class, parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)], indices = [Index("gameId")])
data class Round(@PrimaryKey val id: String = UUID.randomUUID().toString(), val gameId: String, val number: Int, val timestamp: Long = System.currentTimeMillis())