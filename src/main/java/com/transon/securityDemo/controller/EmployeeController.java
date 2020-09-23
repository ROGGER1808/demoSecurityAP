package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.entity.Employee;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.EmployeeMapper;
import com.transon.securityDemo.repositories.DepartmentRepository;
import com.transon.securityDemo.repositories.EmployeeRepository;
import com.transon.securityDemo.requestModel.RequestUpdateEmployee;
import com.transon.securityDemo.responseModel.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/management/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody @Valid RequestUpdateEmployee employeeRequest){
        Employee employee = EmployeeMapper.INSTANCE.employeeRequestToEmployee(employeeRequest);
        if (employeeRequest.getDepartmentId() != null){
            Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                    .orElseThrow(() -> new NotFoundEntityException(employeeRequest.getDepartmentId(), "Department"));
            employee.setDepartment(department);
        }
        employee.setCreatedAt(new Date());
        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));

        return  new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestUpdateEmployee employeeRequest){

        Employee employee = employeeRepository.findById(id)
                .map(employee1 -> {
                    if (employeeRequest.getDepartmentId() != null){
                        Department department = departmentRepository.findById(id)
                                .orElseThrow(() -> new NotFoundEntityException(employeeRequest
                                        .getDepartmentId(), "Department"));
                        if (!department.isActive()){
                            throw new NotFoundEntityException(employeeRequest.getDepartmentId(), "Department");
                        }
                        employee1.setDepartment(department);
                    }
                    employee1.setAvatar(employeeRequest.getAvatar());
                    employee1.setBirthday(employeeRequest.getBirthday());
                    employee1.setEmail(employeeRequest.getEmail());
                    employee1.setFullname(employeeRequest.getFullname());
                    employee1.setGender(employeeRequest.getGender());
                    employee1.setPhone(employeeRequest.getPhone());
                    employee1.setSalary(employeeRequest.getSalary());
                    return employeeRepository.save(employee1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));

        return  new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));
        employee.setActive(false);
        employeeRepository.save(employee);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }
}
