package org.privado.employee.team.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.privado.employee.team.model.EmployeeDto;
import org.privado.employee.team.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping("/ef")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(value = "groups", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> employees(@RequestBody @Size(min = 1) List<@Valid EmployeeDto> employees,
                                       @RequestParam("batch") Integer batch) {
        log.info("action=employees, message=requestReceived, batch={}", batch);
        return ResponseEntity.ok(employeeService.groupEmployees(employees));
    }
}
