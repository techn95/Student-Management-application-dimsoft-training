package com.bms.schoolmanagementsystem.dto.request.studentsubject;

import javax.validation.constraints.NotNull;

import com.bms.schoolmanagementsystem.helper.ValidationMessage;
import com.bms.schoolmanagementsystem.model.Student;
import com.bms.schoolmanagementsystem.model.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStudentSubjectRequest {

    @NotNull(message = ValidationMessage.StudentSubject.STUDENTSUBJECT_YEAR_NOT_NULL)
    private int year;

    @NotNull(message = ValidationMessage.StudentSubject.STUDENTSUBJECT_NOTE_NOT_NULL)
    private double note;

    @NotNull(message = ValidationMessage.StudentSubject.STUDENTSUBJECT_STUDENT_NOT_NULL)
    private Student student;

    @NotNull(message = ValidationMessage.StudentSubject.STUDENTSUBJECT_SUBJECT_NOT_NULL)
    private Subject subject;

}
