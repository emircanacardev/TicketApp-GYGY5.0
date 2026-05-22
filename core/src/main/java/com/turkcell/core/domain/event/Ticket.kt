package com.turkcell.core.domain.event

data class Ticket(
    val id: String,
    val qrCode: String,
    val status: String,
    val usedAt: String?,
    val ticketType: TicketTypeSummary
)

data class TicketTypeSummary(
    val id: String,
    val name: String,
    val priceCents: Long,
    val event: EventSummary
)

data class EventSummary(
    val id: String,
    val name: String,
    val venue: String,
    val startsAt: String
)