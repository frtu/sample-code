package com.github.frtu.workflow.model

import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByName(name: String): Employee
}