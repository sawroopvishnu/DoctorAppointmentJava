package com.doctorappointmentapp.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UniversalErrorResponseDTO {
    private Integer status;
    private String message;
    private Object error;

    public UniversalErrorResponseDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
