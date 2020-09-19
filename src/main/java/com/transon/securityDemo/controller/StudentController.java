package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Student;
import com.transon.securityDemo.repositories.StudentRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/management/students")
public class StudentController {
    private final StudentRepository studentRepository;
    final Environment evn;

    public StudentController(StudentRepository studentRepository, Environment evn) {
        this.studentRepository = studentRepository;
        this.evn = evn;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){

        List<Student> result = studentRepository.findAll();

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){

        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Student student){

        Student result = studentRepository.save(student);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Student student, @PathVariable("id") Long id){

        Optional<Student> result = studentRepository.findById(id);

        if (!result.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        result.get().setAge(student.getAge());
        result.get().setName(student.getName());
        Student studentResult = studentRepository.save(result.get());
        return new ResponseEntity<>(studentResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){

        Optional<Student> result = studentRepository.findById(id);

        if (!result.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }

        studentRepository.delete(result.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
