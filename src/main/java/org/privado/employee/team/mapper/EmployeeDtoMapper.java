package org.privado.employee.team.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.privado.employee.team.domain.Employee;
import org.privado.employee.team.model.EmployeeDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeDtoMapper extends DataMapper<Employee, EmployeeDto> {
    List<EmployeeDto> mapData(List<Employee> dto);
    EmployeeDto mapData(Employee dto);
}
