package com.turkcell.data.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class TicketDto(
    val id: String,
    val qrCode: String,
    val status: String,
    val usedAt: String? = null,
    val ticketType: TicketTypeSummaryDto
)

@Serializable
data class TicketTypeSummaryDto(
    val id: String,
    val name: String,
    val priceCents: Long,
    val event: EventSummaryDto
)

@Serializable
data class EventSummaryDto(
    val id: String,
    val name: String,
    val venue: String,
    val startsAt: String
)