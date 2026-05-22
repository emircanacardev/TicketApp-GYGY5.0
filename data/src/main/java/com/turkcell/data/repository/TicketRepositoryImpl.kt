package com.turkcell.data.repository

import com.turkcell.core.domain.event.Ticket
import com.turkcell.core.domain.event.TicketRepository
import com.turkcell.data.mapper.toDomain
import com.turkcell.data.remote.EventApi
import com.turkcell.data.util.runCatchingApi

class TicketRepositoryImpl(
    private val eventApi: EventApi
) : TicketRepository {
    override suspend fun getMyTickets(): Result<List<Ticket>> =
        runCatchingApi { eventApi.getMyTickets() }.map { list -> list.map { it.toDomain() } }
}