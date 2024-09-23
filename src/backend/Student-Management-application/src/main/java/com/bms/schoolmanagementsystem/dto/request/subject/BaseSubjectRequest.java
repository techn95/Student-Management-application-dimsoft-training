package com.bms.schoolmanagementsystem.dto.request.subject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bms.schoolmanagementsystem.helper.ValidationMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseSubjectRequest {

    @NotNull(message = ValidationMessage.Subject.SUBJECT_INTITULE_NOT_NULL)
    @NotEmpty(message = ValidationMessage.Subject.SUBJECT_INTITULE_NOT_EMPTY)
    private String intitule;

    @NotNull(message = ValidationMessage.Subject.SUBJECT_COEFFICIENT_NOT_NULL)
    private int coefficient;

}
