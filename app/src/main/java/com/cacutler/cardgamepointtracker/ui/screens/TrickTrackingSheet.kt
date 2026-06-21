package com.cacutler.cardgamepointtracker.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cacutler.cardgamepointtracker.data.Player
import com.cacutler.cardgamepointtracker.repository.GameRepository
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrickTrackingSheet(player: Player, currentRound: Int, repository: GameRepository, onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    val trickEntry by repository.getTrickEntryFlow(player.id, currentRound).collectAsState(initial = null)
    var bidInput by remember(trickEntry) {mutableIntStateOf(trickEntry?.tricksBid ?: 0)}
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(player.name, style = MaterialTheme.typography.titleLarge)
            Text("Round $currentRound — Trick Goal", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
            Text("Tricks to win this round", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                FilledTonalIconButton(onClick = {if (bidInput > 0) {bidInput--}}) {
                    Text("−", fontSize = 20.sp)
                }
                Text("$bidInput", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(horizontal = 32.dp))
                FilledTonalIconButton(onClick = {bidInput++}) {
                    Icon(Icons.Default.Add, null)
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    scope.launch {
                        repository.setBid(player.id, currentRound, bidInput)
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (trickEntry == null) {"Set Goal"} else {"Update Goal"})
            }
            OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}