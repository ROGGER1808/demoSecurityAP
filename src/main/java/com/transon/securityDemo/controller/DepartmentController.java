package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.repositories.DepartmentRepository;
import com.transon.securityDemo.responseModel.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/management/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody @Valid Department department){
        if (departmentRepository.existsDepartmentByName(department.getName())){

            return new ResponseEntity<>(new ResponseMessage("name already exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        department.setCreatedAt(new Date());
        departmentRepository.save(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));

        return  new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Department departmentRequest){

        Department department = departmentRepository.findById(id)
                .map(department1 -> {
                    department1.setName(departmentRequest.getName());
                    return departmentRepository.save(department1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));

        return  new ResponseEntity<>(department, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));
        department.setActive(false);
        departmentRepository.save(department);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }
}
