package org.privado.employee.team.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.privado.employee.team.domain.Employee;
import org.privado.employee.team.model.EmployeeDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends DataMapper<EmployeeDto, Employee> {
    List<Employee> mapData(List<EmployeeDto> dto);
    Employee mapData(EmployeeDto dto);
}
