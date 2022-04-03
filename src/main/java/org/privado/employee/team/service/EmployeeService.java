package org.privado.employee.team.service;

import org.privado.employee.team.model.EmployeeDto;
import org.privado.employee.team.model.EmployeesTeam;

import java.util.List;

public interface EmployeeService {
    EmployeesTeam groupEmployees(List<EmployeeDto> employees);
}
