package com.github.frtu.workflow.domain

import com.github.frtu.workflow.model.Employee
import com.github.frtu.workflow.model.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class EmployeeService {
    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    // create the list of Employees into the database who perform the task
    fun createEmployee() {
        if (employeeRepository.findAll().size == 0) {
            employeeRepository.save(Employee("Prince", "Software Enginner"))
            employeeRepository.save(Employee("Gaurav", "Technical Lead"))
            employeeRepository.save(Employee("Abhinav", "Test Lead"))
        }
    }
}