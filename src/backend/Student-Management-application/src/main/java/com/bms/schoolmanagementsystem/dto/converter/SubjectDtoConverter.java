package com.bms.schoolmanagementsystem.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bms.schoolmanagementsystem.dto.SubjectDto;
import com.bms.schoolmanagementsystem.model.Subject;

@Component
public class SubjectDtoConverter {

    public SubjectDto convert(Subject subject) {
        return new SubjectDto(
            subject.getId(),
            subject.getIntitule(),
            subject.getCoefficient()
        );
    }

    public List<SubjectDto> convert(List<Subject> from){
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

}
