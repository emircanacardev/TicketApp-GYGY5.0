package com.turkcell.ticketapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turkcell.core.domain.event.Event
import com.turkcell.core.domain.event.Ticket
import com.turkcell.ticketapp.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp)) {

            Text(
                text = "Yaklaşan Etkinlikler",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(8.dp))
            EventsRow(
                isLoading = state.isEventsLoading,
                error = state.eventsError,
                events = state.events
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Biletlerim",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(8.dp))
            TicketsRow(
                isLoading = state.isTicketsLoading,
                error = state.ticketsError,
                tickets = state.tickets
            )
        }
    }
}

@Composable
private fun EventsRow(isLoading: Boolean, error: String?, events: List<Event>) {
    when {
        isLoading -> Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        error != null -> Text(text = error, modifier = Modifier.padding(horizontal = 24.dp))
        events.isEmpty() -> Text(text = "Şimdilik hiç etkinlik yok.", modifier = Modifier.padding(horizontal = 24.dp))
        else -> LazyRow(contentPadding = PaddingValues(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items = events, key = { it.id }) { EventCard(it) }
        }
    }
}

@Composable
private fun EventCard(event: Event) {
    Card(modifier = Modifier.width(260.dp).height(200.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = event.name.take(1).uppercase().ifBlank { "?" },
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = event.name, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Text(text = event.venue, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            }
        }
    }
}

@Composable
private fun TicketsRow(isLoading: Boolean, error: String?, tickets: List<Ticket>) {
    when {
        isLoading -> Box(modifier = Modifier.fillMaxWidth().height(140.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        error != null -> Text(text = error, modifier = Modifier.padding(horizontal = 24.dp))
        tickets.isEmpty() -> Text(text = "Henüz biletiniz yok.", modifier = Modifier.padding(horizontal = 24.dp))
        else -> LazyRow(contentPadding = PaddingValues(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items = tickets, key = { it.id }) { TicketCard(it) }
        }
    }
}

@Composable
private fun TicketCard(ticket: Ticket) {
    Card(modifier = Modifier.width(220.dp).height(120.dp)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = ticket.ticketType.event.name, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Text(text = ticket.ticketType.name, style = MaterialTheme.typography.bodySmall)
            Text(text = ticket.ticketType.event.venue, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            Text(
                text = if (ticket.status == "VALID") "✓ Geçerli" else "✗ Kullanıldı",
                style = MaterialTheme.typography.labelSmall,
                color = if (ticket.status == "VALID") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}