package com.bms.schoolmanagementsystem.service.units_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bms.schoolmanagementsystem.dto.request.subject.CreateSubjectRequest;
import com.bms.schoolmanagementsystem.dto.request.subject.UpdateSubjectRequest;
import com.bms.schoolmanagementsystem.enums.StatusEnum;
import com.bms.schoolmanagementsystem.exception.subject.SubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.exception.subject.SubjectNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.model.Subject;
import com.bms.schoolmanagementsystem.repository.SubjectRepository;
import com.bms.schoolmanagementsystem.service.SubjectService;
import com.bms.schoolmanagementsystem.service.impl.SubjectServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTests {

    @Mock
    SubjectRepository subjectRepository;

    SubjectService subjectService;

    // AutoCloseable autoCloseable;

    @BeforeEach
    void setup(){
        // autoCloseable = MockitoAnnotations.openMocks(this);
        subjectService = new SubjectServiceImpl(subjectRepository);
    }

    // @AfterEach
    // void tearDown() throws Exception{
    //     autoCloseable.close();
    // }

    @Test
    void shouldReturnListOfSubjectWithSuccess(){

        // Given
        Subject subject = new Subject();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);
        Subject subject1 = new Subject();
        subject1.setIntitule("Maths");
        subject1.setCoefficient(6);

        List<Subject> mockSubjects =  new ArrayList<Subject>();
        mockSubjects.add(subject);
        mockSubjects.add(subject1);
        //When
        when(subjectRepository.findAll()).thenReturn(mockSubjects);
        //Then
        List<Subject> subjectList = subjectService.findAllSubjects();
        assertEquals(2, subjectList.size());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnListOfSubjectThrowListEmpty(){
        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.findAllSubjects();
        }).getMessage();

        assertTrue(msg.equals(BusinessMessage.Subject.SUBJECT_LIST_EMPTY));
    }

    @Test
    void canAddSubjectWithSuccess(){
        //Given
        CreateSubjectRequest subject = new CreateSubjectRequest();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);
        //when
        subjectService.createSubject(subject);

        //then
        ArgumentCaptor<Subject> createSubjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository, times(1)).save(createSubjectArgumentCaptor.capture());

        Subject subjectCaptured = createSubjectArgumentCaptor.getValue();

        assertEquals("Maths", subjectCaptured.getIntitule());
    }

    @Test
    void canAddSubjectWithThrowWhenSubjectAlreadyExist(){
        //Given
        CreateSubjectRequest subject = new CreateSubjectRequest();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);

        Subject subjectmock = new Subject();
        subjectmock.setIntitule(subject.getIntitule());
        subjectmock.setCoefficient(subject.getCoefficient());

        List<Subject> mockSubjects = new ArrayList<Subject>();
        mockSubjects.add(subjectmock);
        
        ArgumentCaptor<String> intituleCaptor = ArgumentCaptor.forClass(String.class);

        // given(subjectRepository.findByIntitule(subject.getIntitule())).;
        //when
        when(subjectRepository.findByIntitule("Maths")).thenReturn(mockSubjects);
        //then
        String msg = assertThrows(SubjectAlreadyExistException.class, ()->{
            subjectService.createSubject(subject);
        }).getMessage();
        
        assertEquals(BusinessMessage.Subject.SUBJECT_ALREADY_EXISTS, msg);

        verify(subjectRepository, never()).save(any());
        verify(subjectRepository, times(1)).findByIntitule(intituleCaptor.capture());
        assertEquals("Maths",intituleCaptor.getValue());
    }

    @Test
    void findSubjectByIdWithSuccess(){
        //given
        Subject subject = new Subject();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);


        //when
        when(subjectRepository.findById("vers")).thenReturn(Optional.of(subject));
        Subject subjectFind = subjectService.findSubjectById("vers");
        //then

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        verify(subjectRepository, times(1)).findById(idCaptor.capture());
        assertEquals("vers", idCaptor.getValue());
        assertEquals("Maths", subjectFind.getIntitule());
        assertEquals(6, subjectFind.getCoefficient());
    }

    @Test
    void findSubjectByIdWithThrowNotFound(){


        //when
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.findSubjectById("vers");
        }).getMessage();
        //then

        verify(subjectRepository, times(1)).findById( idCaptor.capture());
        assertEquals("vers", idCaptor.getValue());
        assertTrue(msg.equals(BusinessMessage.Subject.SUBJECT_NOT_FOUND));
    }

    @Test
    void updateSubjectWithSuccess(){

        //Given

        UpdateSubjectRequest subjectRequest = new UpdateSubjectRequest();
        subjectRequest.setIntitule("Physique");
        subjectRequest.setCoefficient(5);

        Subject subject = new Subject();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);

        //When
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(subject));
        subjectService.updateSubject("vers", subjectRequest);

        //Then
        ArgumentCaptor<Subject> updateSubjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        verify(subjectRepository,times(1)).findById(idCaptor.capture());
        verify(subjectRepository, times(1)).save(updateSubjectArgumentCaptor.capture());
        verify(subjectRepository, times(1)).findById(anyString());
        Subject subjectUpd = updateSubjectArgumentCaptor.getValue();
        assertEquals("vers", idCaptor.getValue());
        assertEquals("Physique", subjectUpd.getIntitule());
        assertEquals(5, subjectUpd.getCoefficient());
    }

    @Test
    void updateSubjectWithThrowsAlreadySubjectExist(){

        //Given

        UpdateSubjectRequest subjectRequest = new UpdateSubjectRequest();
        subjectRequest.setIntitule("Physique");
        subjectRequest.setCoefficient(5);

        Subject subject = new Subject();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);

        List<Subject> mockSubjects = new ArrayList<Subject>();
        mockSubjects.add(subject);

        //When
        when(subjectRepository.findByIntitule(anyString())).thenReturn(mockSubjects);
        
        // Then
        ArgumentCaptor<String> intituleCaptor = ArgumentCaptor.forClass(String.class);
        String msg = assertThrows(SubjectAlreadyExistException.class, ()->{
            subjectService.updateSubject("vers", subjectRequest);
        }).getMessage();

        verify(subjectRepository, times(1)).findByIntitule(intituleCaptor.capture());
        verify(subjectRepository, never()).save(any());
        verify(subjectRepository, never()).findById(anyString());
        assertEquals(BusinessMessage.Subject.SUBJECT_ALREADY_EXISTS, msg);

    }

    @Test
    void updateSubjectWithThrowsSubjectNotFound(){

        //Given

        UpdateSubjectRequest subjectRequest = new UpdateSubjectRequest();
        subjectRequest.setIntitule("Physique");
        subjectRequest.setCoefficient(5);
        
        // Then
        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.updateSubject("vers", subjectRequest);
        }).getMessage();

        ArgumentCaptor<String> intituleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        verify(subjectRepository, times(1)).findByIntitule(intituleCaptor.capture());
        verify(subjectRepository, times(1)).findById(idCaptor.capture());
        verify(subjectRepository, never()).save(any());
        assertEquals("Physique", intituleCaptor.getValue());
        assertEquals("vers", idCaptor.getValue());
        assertEquals(BusinessMessage.Subject.SUBJECT_NOT_FOUND, msg);

    }

    @Test
    void getAllActiveSubjectsWithSuccess(){

        //given
        Subject subject = new Subject();
        subject.setIntitule("physique");
        subject.setCoefficient(6);
        Subject subject1 = new Subject();
        subject1.setIntitule("Maths");
        subject1.setCoefficient(6);
        subject1.setStatut(StatusEnum.ACTIVE);

        List<Subject> mockSubjects =  new ArrayList<Subject>();
        mockSubjects.add(subject);
        mockSubjects.add(subject1);

        //when
        when(subjectRepository.findAll()).thenReturn(mockSubjects);
        List<Subject> results = subjectService.getAllActiveSubjects();
        //then
        verify(subjectRepository, times(1)).findAll();
        assertEquals( 1, results.size());
        assertEquals("Maths", results.get(0).getIntitule());
        assertEquals(6, results.get(0).getCoefficient());
    }

    @Test
    void getAllActiveSubjectsWithThrowEmptySubjectList(){
        //given
        Subject subject = new Subject();
        subject.setIntitule("physique");
        subject.setCoefficient(6);
        Subject subject1 = new Subject();
        subject1.setIntitule("Maths");
        subject1.setCoefficient(6);

        List<Subject> mockSubjects =  new ArrayList<Subject>();
        mockSubjects.add(subject);
        mockSubjects.add(subject1);
        //when
        when(subjectRepository.findAll()).thenReturn(mockSubjects);
        //then

        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.getAllActiveSubjects();
        }).getMessage();

        verify(subjectRepository, times(1)).findAll();
        assertEquals(msg, BusinessMessage.Subject.SUBJECT_LIST_EMPTY);
    }

    @Test
    void updateSubjectStatusWithSuccess(){
        //given
        Subject subject = new Subject();
        subject.setIntitule("physique");
        subject.setCoefficient(6);
        //when
        when(subjectRepository.findById("vers")).thenReturn(Optional.of(subject));
        Subject subjectUpd = subjectService.updateSubjectStatus("vers", StatusEnum.ACTIVE);

        ArgumentCaptor<Subject> subjArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        //then
        verify(subjectRepository, times(1)).findById(idCaptor.capture());
        verify(subjectRepository, times(1)).save(subjArgumentCaptor.capture());

        assertEquals("physique", subjectUpd.getIntitule());
        assertEquals("vers", idCaptor.getValue());
        assertEquals("physique", subjArgumentCaptor.getValue().getIntitule());
        assertEquals(6, subjArgumentCaptor.getValue().getCoefficient());
        assertEquals(StatusEnum.ACTIVE, subjectUpd.getStatut());
        assertEquals(StatusEnum.ACTIVE, subjArgumentCaptor.getValue().getStatut());
        assertEquals(6, subjectUpd.getCoefficient());
    }

    @Test
    void updateSubjectStatusWithThrowSubjectNotFound(){

        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.updateSubjectStatus("vers", StatusEnum.INACTIVE);
        }).getMessage();

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        verify(subjectRepository, times(0)).save(any());
        verify(subjectRepository, times(1)).findById(idCaptor.capture());
        assertEquals("vers", idCaptor.getValue());
        assertEquals(BusinessMessage.Subject.SUBJECT_NOT_FOUND, msg);
    }

    @Test
    void deleteSubjectWithSuccess(){
        //given
        Subject subject = new Subject();
        subject.setCoefficient(6);
        subject.setIntitule("physique");
        //when
        when(subjectRepository.findById("vers")).thenReturn(Optional.of(subject));
        Subject deletSubject = subjectService.deleteSubject("vers");
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        //then 
        verify(subjectRepository, times(1)).findById("vers");
        verify(subjectRepository, times(1)).save(subjectArgumentCaptor.capture());
        assertEquals(StatusEnum.DELETED, subjectArgumentCaptor.getValue().getStatut());
        assertEquals(StatusEnum.DELETED, deletSubject.getStatut());
        assertEquals("physique", subjectArgumentCaptor.getValue().getIntitule());
        assertEquals(6, subjectArgumentCaptor.getValue().getCoefficient());
    }

    @Test
    void deleteSubjectWithThrowSubjectNotFound(){
        //when
        String msg = assertThrows(SubjectNotFoundException.class, ()->{
            subjectService.deleteSubject("vers");
        }).getMessage();
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        //then
        verify(subjectRepository, times(1)).findById(idCaptor.capture());
        verify(subjectRepository, never()).save(any());   
        assertEquals("vers", idCaptor.getValue());
        assertEquals(BusinessMessage.Subject.SUBJECT_NOT_FOUND, msg);   
    }

}
