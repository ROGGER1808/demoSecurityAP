package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Employee;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.EmployeeMapper;
import com.transon.securityDemo.requestModel.RequestUpdateEmployee;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IDepartmentService;
import com.transon.securityDemo.services.IEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService ) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/format")
    public ResponseEntity<?> getAllFormat(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Employee> employees = new ArrayList<Employee>();
            Pageable pagingSort = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

            Page<Employee> pageTuts;
            if (filter == null)
                pageTuts = employeeService.findAll(pagingSort);
            else
                pageTuts = employeeService.findAllByFullnameContaining(filter, pagingSort);

            employees = pageTuts.getContent();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", employees);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RequestUpdateEmployee employeeRequest) {

        Employee employee = EmployeeMapper.INSTANCE.employeeRequestToEmployee(employeeRequest);
        employeeService.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody RequestUpdateEmployee employeeRequest) {

        Employee employee = employeeService.findById(id)
                .map(employee1 -> {
                    employee1.setAvatar(employeeRequest.getAvatar());
                    employee1.setBirthday(employeeRequest.getBirthday());
                    employee1.setEmail(employeeRequest.getEmail());
                    employee1.setFullname(employeeRequest.getFullname());
                    employee1.setGender(employeeRequest.getGender());
                    employee1.setPhone(employeeRequest.getPhone());
                    employee1.setSalary(employeeRequest.getSalary());
                    if (employeeRequest.getIsActive() != null){
                        employee1.setActive(employeeRequest.getIsActive());
                    }
                    return employeeService.save(employee1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Employee"));
        employee.setActive(false);
        employeeService.save(employee);

        return new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
