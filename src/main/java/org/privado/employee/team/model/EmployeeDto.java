package org.privado.employee.team.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotNull
    private Integer age;
    @NotNull
    @JsonProperty("Role")
    @Pattern(regexp = "^Sales|Engineer|Marketing$", message = "role, possible values [Sales|Engineer|Marketing]")
    private String role;
}
