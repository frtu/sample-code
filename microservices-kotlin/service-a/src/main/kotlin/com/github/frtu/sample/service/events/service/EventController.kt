package com.github.frtu.sample.service.events.service

import com.github.frtu.sample.service.events.entity.Event
import com.github.frtu.sample.service.events.entity.EventRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/events")
class EventController(val repository: EventRepository) {
    @GetMapping
    fun findAll() = repository.findAll()

    @PostMapping
    fun addEvent(@RequestBody event: Event) = repository.save(event)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) =
        repository.findById(id).orElseThrow { IllegalArgumentException("id:[$id] doesn't exist!") }

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable id: Long, @RequestBody event: Event) {
        assert(event.id == id)
        repository.save(event)
    }

    @DeleteMapping("/{id}")
    fun removeCustomer(@PathVariable id: Long) = repository.deleteById(id)
}