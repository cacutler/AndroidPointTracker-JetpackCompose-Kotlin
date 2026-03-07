package com.cacutler.cardgamepointtracker
import com.cacutler.cardgamepointtracker.data.*
import com.cacutler.cardgamepointtracker.repository.GameRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
@OptIn(ExperimentalCoroutinesApi::class)
class GameRepositoryTest {
    private lateinit var repository: GameRepository
    private lateinit var database: AppDatabase
    private lateinit var gameDao: GameDao
    private lateinit var playerDao: PlayerDao
    private lateinit var scoreEntryDao: ScoreEntryDao
    @Before
    fun setup() {
        database = mockk()//Create mocks
        gameDao = mockk()
        playerDao = mockk()
        scoreEntryDao = mockk()
        every {database.gameDao()} returns gameDao//Setup database to return DAOs
        every {database.playerDao()} returns playerDao
        every {database.scoreEntryDao()} returns scoreEntryDao
        repository = GameRepository(database)
    }
    @After
    fun tearDown() {
        unmockkAll()
    }
    @Test
    fun `createGame should insert game and players`() = runTest {
        coEvery {gameDao.insertGame(any())} just Runs
        coEvery {playerDao.insertPlayers(any())} just Runs
        repository.createGame("Test Game", listOf("Alice", "Bob", "Charlie"), lowestScoreWins = false)
        coVerify(exactly = 1) {gameDao.insertGame(match { it.name == "Test Game" && !it.lowestScoreWins })}
        coVerify(exactly = 1) {playerDao.insertPlayers(match { it.size == 3 })}
    }
    @Test
    fun `createGame should pass lowestScoreWins flag to game`() = runTest {
        coEvery {gameDao.insertGame(any())} just Runs
        coEvery {playerDao.insertPlayers(any())} just Runs
        repository.createGame("Golf Night", listOf("Alice", "Bob"), lowestScoreWins = true)
        coVerify(exactly = 1) {gameDao.insertGame(match {it.lowestScoreWins})}
    }
    @Test
    fun `addPoints should create score entry and update player score`() = runTest {
        val playerId = "player123"//Arrange
        val points = 50
        val round = 2
        coEvery {scoreEntryDao.insertScoreEntry(any())} just Runs
        coEvery {playerDao.addPoints(playerId, points)} just Runs
        repository.addPoints(playerId, points, round)//Act
        coVerify {scoreEntryDao.insertScoreEntry(match {it.playerId == playerId && it.points == points && it.round == round})}//Assert
        coVerify {playerDao.addPoints(playerId, points)}
    }
    @Test
    fun `undoLastScore should delete entry and subtract points`() = runTest {
        val playerId = "player123"//Arrange
        val lastEntry = ScoreEntry(id = "entry1", playerId = playerId, points = 30, round = 1)
        coEvery {scoreEntryDao.getLastScoreEntry(playerId)} returns lastEntry
        coEvery {scoreEntryDao.deleteScoreEntry(any())} just Runs
        coEvery {playerDao.addPoints(any(), any())} just Runs
        repository.undoLastScore(playerId)//Act
        coVerify {scoreEntryDao.deleteScoreEntry(lastEntry)}//Assert
        coVerify {playerDao.addPoints(playerId, -30)}
    }
    @Test
    fun `undoLastScore should do nothing when no last entry`() = runTest {
        val playerId = "player123"//Arrange
        coEvery {scoreEntryDao.getLastScoreEntry(playerId)} returns null
        repository.undoLastScore(playerId)//Act
        coVerify(exactly = 0) {scoreEntryDao.deleteScoreEntry(any())}//Assert
        coVerify(exactly = 0) {playerDao.addPoints(any(), any())}
    }
    @Test
    fun `getWinner should return player with highest score by default`() = runTest {
        val gameId = "game123"
        val game = Game(id = gameId, name = "Test", lowestScoreWins = false)
        val gameWithPlayers = GameWithPlayers(game, emptyList())
        val players = listOf(Player(id = "p1", gameId = gameId, name = "Alice", score = 100), Player(id = "p2", gameId = gameId, name = "Bob", score = 150), Player(id = "p3", gameId = gameId, name = "Charlie", score = 75))
        every {gameDao.getGameWithPlayers(gameId)} returns flowOf(gameWithPlayers)
        every {playerDao.getPlayersForGame(gameId)} returns flowOf(players)
        val winner = repository.getWinner(gameId)
        assertEquals("Bob", winner?.name)
        assertEquals(150, winner?.score)
    }
    @Test
    fun `resetGame should clear scores and reset game state`() = runTest {
        val gameId = "game123"//Arrange
        coEvery {scoreEntryDao.deleteAllScoresForGame(gameId)} just Runs
        coEvery {playerDao.resetScores(gameId)} just Runs
        coEvery {gameDao.resetGame(gameId)} just Runs
        repository.resetGame(gameId)//Act
        coVerifyOrder {//Assert
            scoreEntryDao.deleteAllScoresForGame(gameId)
            playerDao.resetScores(gameId)
            gameDao.resetGame(gameId)
        }
    }
    @Test
    fun `getWinner should return player with lowest score when lowestScoreWins`() = runTest {
        val gameId = "game123"
        val game = Game(id = gameId, name = "Test", lowestScoreWins = true)
        val gameWithPlayers = GameWithPlayers(game, emptyList())
        val players = listOf(Player(id = "p1", gameId = gameId, name = "Alice", score = 100), Player(id = "p2", gameId = gameId, name = "Bob", score = 150), Player(id = "p3", gameId = gameId, name = "Charlie", score = 75))
        every {gameDao.getGameWithPlayers(gameId)} returns flowOf(gameWithPlayers)
        every {playerDao.getPlayersForGame(gameId)} returns flowOf(players)
        val winner = repository.getWinner(gameId)
        assertEquals("Charlie", winner?.name)
        assertEquals(75, winner?.score)
    }
    @Test
    fun `getWinner should return null when game not found`() = runTest {
        val gameId = "nonexistent"
        every {gameDao.getGameWithPlayers(gameId)} returns flowOf(null)
        val winner = repository.getWinner(gameId)
        assertNull(winner)
    }
}