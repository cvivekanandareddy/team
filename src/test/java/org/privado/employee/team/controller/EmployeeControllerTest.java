package org.privado.employee.team.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.privado.employee.team.model.EmployeesTeam;
import org.privado.employee.team.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    EmployeeController controller;
    @Mock
    EmployeeService employeeService;

    @Test
    void emptyListWithoutMock() {
        ResponseEntity<?> response = controller.employees(emptyList(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    void emptyListWithMock() {
        when(employeeService.groupEmployees(anyList())).thenReturn(EmployeesTeam.builder().build());
        ResponseEntity<?> response = controller.employees(emptyList(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void emptyListThrowsException() {
        when(employeeService.groupEmployees(anyList())).thenThrow(new RuntimeException("exception"));
        assertThrows(RuntimeException.class, () -> controller.employees(emptyList(), 1));
    }
}
