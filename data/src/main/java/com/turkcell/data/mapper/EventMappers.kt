package com.turkcell.data.mapper

import com.turkcell.core.domain.event.Event
import com.turkcell.core.domain.event.EventSummary
import com.turkcell.core.domain.event.Ticket
import com.turkcell.core.domain.event.TicketType
import com.turkcell.core.domain.event.TicketTypeSummary
import com.turkcell.data.dto.event.EventDto
import com.turkcell.data.dto.event.EventSummaryDto
import com.turkcell.data.dto.event.TicketDto
import com.turkcell.data.dto.event.TicketTypeDto
import com.turkcell.data.dto.event.TicketTypeSummaryDto

internal fun EventDto.toDomain(): Event = Event(
    id= id,
    name= name,
    description= description.orEmpty(),
    venue= place.orEmpty(),
    startsAt = startsAt.orEmpty(),
    endsAt = endsAt.orEmpty(),
    ticketTypes = ticketTypes.map { it.toDomain() }
)

internal fun TicketTypeDto.toDomain() : TicketType = TicketType(
    id=id,
    name=name,
    priceCents=priceCents,
    capacity=capacity,
    soldCount=soldCount,
    remaining=remaining
)

fun TicketDto.toDomain() = Ticket(
    id = id,
    qrCode = qrCode,
    status = status,
    usedAt = usedAt,
    ticketType = ticketType.toDomain()
)

fun TicketTypeSummaryDto.toDomain() = TicketTypeSummary(
    id = id,
    name = name,
    priceCents = priceCents,
    event = event.toDomain()
)

fun EventSummaryDto.toDomain() = EventSummary(
    id = id,
    name = name,
    venue = venue,
    startsAt = startsAt
)