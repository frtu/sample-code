package com.github.frtu.sample.service.events.entity

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface EventRepository : CrudRepository<Event, Long> {
    @Query("SELECT p FROM Event p WHERE fts(:comments) = true")
    fun search(@Param("comments") comments: String): List<Event>
}