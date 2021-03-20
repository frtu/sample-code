package com.github.frtu.sample.service.events.entity

import org.springframework.data.repository.CrudRepository

interface EventRepository : CrudRepository<Event, Long>