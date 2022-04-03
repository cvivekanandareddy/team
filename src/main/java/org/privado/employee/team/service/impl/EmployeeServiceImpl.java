package org.privado.employee.team.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.privado.employee.team.domain.Employee;
import org.privado.employee.team.model.EmployeeDto;
import org.privado.employee.team.mapper.EmployeeDtoMapper;
import org.privado.employee.team.mapper.EmployeeMapper;
import org.privado.employee.team.model.EmployeesTeam;
import org.privado.employee.team.repository.EmployeeRepository;
import org.privado.employee.team.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDtoMapper employeeDtoMapper;
    private final EmployeeMapper employeeMapper;

    /**
     * It saves the data into the database & makes the teams based on the criteria
     * @param employees List of employees to be saved & to make the teams
     * @return Returns the object after making the teams with groups & no-groups
     */
    @Override
    public EmployeesTeam groupEmployees(List<EmployeeDto> employees) {
        log.info("action=groupEmployees, message=processStarted");
        List<Employee> employeeList = employeeMapper.mapData(employees);
        employeeRepository.saveAll(employeeList);

        List<EmployeeDto> employeeDtoList = employeeDtoMapper.mapData(employeeList);
        Map<String, List<EmployeeDto>> employeesByLocationMap = employeeDtoList.stream()
                .collect(Collectors.groupingBy(EmployeeDto::getLocation));

        EmployeesTeam employeesTeam = EmployeesTeam.builder().build();
        for (Map.Entry<String, List<EmployeeDto>> entry : employeesByLocationMap.entrySet()) {
            makeTeamsByLocation(entry.getValue(), employeesTeam, true);
        }

        log.info("action=groupEmployees, message=processEnded");
        return employeesTeam;
    }

    /**
     * It will build the teams based on the employees category
     * @param employees List of employees by the location
     * @param teams It holds the data after making the teams
     */
    private void makeTeamsByLocation(List<EmployeeDto> employees, EmployeesTeam teams, boolean isFirstTime) {
        log.debug("action=makeTeamsByLocation, employees={}", employees);
        Predicate<EmployeeDto> marketingPredicate = emp -> emp.getRole().equals("Marketing");
        Predicate<EmployeeDto> engineerPredicate = emp -> emp.getRole().equals("Engineer");
        Predicate<EmployeeDto> salesPredicate = emp -> emp.getRole().equals("Sales");
        Predicate<EmployeeDto> agePredicate = emp -> emp.getAge() > 35;

        List<EmployeeDto> employeesTeam = new ArrayList<>();
        Iterator<EmployeeDto> iterator = employees.iterator();

        while (iterator.hasNext()) {
            EmployeeDto employee = iterator.next();

            if ((marketingPredicate.test(employee) && isEmployeeNotExist(employeesTeam, marketingPredicate)) ||
                    (salesPredicate.test(employee) && isEmployeeNotExist(employeesTeam, salesPredicate)) ||
                    (agePredicate.test(employee) && isEmployeeNotExist(employeesTeam, agePredicate)) ||
                    (engineerPredicate.test(employee))) {

                employeesTeam.add(employee);
                iterator.remove();
            }

            if (employeesTeam.size() == 3) {
                teams.addGroups(employeesTeam);
                employeesTeam = new ArrayList<>();
            }
        }

        if (isFirstTime && (employees.size() + employeesTeam.size()) >= 3 ) {
            employees.addAll(employeesTeam);
            makeTeamsByLocation(employees, teams, false);
        } else {
            employeesTeam.forEach(teams::addNonGroups);
            employees.forEach(teams::addNonGroups);
        }
    }

    /**
     * It identifies the object is exist in the list by the predicate
     * @param employees List of employees to check the existence
     * @param predicate Conditional predicate to verify
     * @return Returns true if the object is not exist otherwise false
     */
    private boolean isEmployeeNotExist(List<EmployeeDto> employees, Predicate<EmployeeDto> predicate) {
        return employees.stream().noneMatch(predicate);
    }

}
