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
public class UniversalSuccessResponseDTO<T> {
    private Integer status;
    private String message;
    private T data;

    public UniversalSuccessResponseDTO(Integer status, T data) {
        this.status=status;
        this.data=data;
    }

}
