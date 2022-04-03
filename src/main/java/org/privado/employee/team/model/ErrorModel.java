package org.privado.employee.team.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {
    private String errorCode;
    private Integer httpStatus;
    private String errorMessage;
}
