package org.privado.employee.team.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesTeam {
    @JsonProperty("groups")
    private List<List<EmployeeDto>> groups;
    @JsonProperty("no-groups")
    private List<EmployeeDto> noGroups;

    public void addGroups(List<EmployeeDto> employees) {
        if (groups == null) {
            groups = new ArrayList<>();
        }

        groups.add(employees);
    }

    public void addNonGroups(EmployeeDto employee) {
        if (noGroups == null) {
            noGroups = new ArrayList<>();
        }

        noGroups.add(employee);
    }
}
