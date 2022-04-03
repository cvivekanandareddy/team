package org.privado.employee.team.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.privado.employee.team.mapper.EmployeeDtoMapper;
import org.privado.employee.team.mapper.EmployeeMapper;
import org.privado.employee.team.model.EmployeeDto;
import org.privado.employee.team.model.EmployeesTeam;
import org.privado.employee.team.repository.EmployeeRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(employeeService, "employeeMapper", Mappers.getMapper(EmployeeMapper.class));
        ReflectionTestUtils.setField(employeeService, "employeeDtoMapper", Mappers.getMapper(EmployeeDtoMapper.class));
    }

    @Test
    void groupEmployeesWithEmptyList() {
        EmployeesTeam response = employeeService.groupEmployees(emptyList());
        assertNotNull(response);
        assertNull(response.getGroups());
        assertNull(response.getNoGroups());
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    void groupEmployeesWithListOfSingleEmployee() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .name("Pramod")
                .age(44)
                .location("Pune")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto1);
        EmployeesTeam response = employeeService.groupEmployees(employeeDtoList);
        assertNotNull(response);
        assertNull(response.getGroups());
        assertEquals(1, response.getNoGroups().size());
        assertEquals(employeeDto1, response.getNoGroups().get(0));
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    void groupEmployeesWithListOfDifferentLocationEmployee() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .name("Pramod")
                .age(44)
                .location("Pune")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto1);
        EmployeeDto employeeDto2 = EmployeeDto.builder()
                .name("Amit")
                .age(38)
                .location("Delhi")
                .role("Engineer")
                .build();
        employeeDtoList.add(employeeDto2);

        EmployeeDto employeeDto3 = EmployeeDto.builder()
                .name("Priyesh")
                .age(32)
                .location("Banglore")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto3);

        EmployeesTeam response = employeeService.groupEmployees(employeeDtoList);
        assertNotNull(response);
        assertNull(response.getGroups());
        assertNotNull(response.getNoGroups());
        assertEquals(3, response.getNoGroups().size());
        assertThat(employeeDtoList).containsAll(response.getNoGroups());
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    void groupEmployeesWithListOfEmployees() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .name("Pramod")
                .age(44)
                .location("Pune")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto1);
        EmployeeDto employeeDto2 = EmployeeDto.builder()
                .name("Amit")
                .age(38)
                .location("Delhi")
                .role("Engineer")
                .build();
        employeeDtoList.add(employeeDto2);

        EmployeeDto employeeDto3 = EmployeeDto.builder()
                .name("Priyesh")
                .age(32)
                .location("Banglore")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto3);

        EmployeeDto employeeDto4 = EmployeeDto.builder()
                .name("Kunal")
                .age(44)
                .location("Pune")
                .role("Sales")
                .build();
        employeeDtoList.add(employeeDto4);
        EmployeeDto employeeDto5 = EmployeeDto.builder()
                .name("Eric")
                .age(29)
                .location("Pune")
                .role("Engineer")
                .build();
        employeeDtoList.add(employeeDto5);

        EmployeesTeam response = employeeService.groupEmployees(employeeDtoList);
        assertNotNull(response);
        assertNotNull(response.getGroups());
        assertEquals(1, response.getGroups().size());
        assertEquals(3, response.getGroups().get(0).size());
        assertThat(asList(employeeDto1, employeeDto4, employeeDto5)).containsAll(response.getGroups().get(0));

        assertNotNull(response.getNoGroups());
        assertEquals(2, response.getNoGroups().size());
        assertThat(asList(employeeDto2, employeeDto3)).containsAll(response.getNoGroups());
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    void groupEmployeesWithListOfEmployeesInNoGroups() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .name("Pramod")
                .age(44)
                .location("Pune")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto1);
        EmployeeDto employeeDto2 = EmployeeDto.builder()
                .name("Amit")
                .age(38)
                .location("Pune")
                .role("Sales")
                .build();
        employeeDtoList.add(employeeDto2);

        EmployeeDto employeeDto3 = EmployeeDto.builder()
                .name("Priyesh")
                .age(32)
                .location("Pune")
                .role("Marketing")
                .build();
        employeeDtoList.add(employeeDto3);

        EmployeeDto employeeDto4 = EmployeeDto.builder()
                .name("Kunal")
                .age(44)
                .location("Pune")
                .role("Sales")
                .build();
        employeeDtoList.add(employeeDto4);

        EmployeesTeam response = employeeService.groupEmployees(employeeDtoList);
        assertNotNull(response);
        assertNull(response.getGroups());
        assertNotNull(response.getNoGroups());
        assertEquals(4, response.getNoGroups().size());
        assertThat(employeeDtoList).containsAll(response.getNoGroups());
        verify(employeeRepository).saveAll(anyList());
    }
}
