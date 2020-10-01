package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IDepartmentService;
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
@RequestMapping("/api/v1/management/departments")
public class DepartmentController {

    private final IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/format")
    public ResponseEntity<?> getAllFormat(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Department> departments = new ArrayList<Department>();
            Pageable pagingSort = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

            Page<Department> pageTuts;
            if (filter == null)
                pageTuts = departmentService.findAll(pagingSort);
            else
                pageTuts = departmentService.findAllByNameContaining(filter, pagingSort);

            departments = pageTuts.getContent();

            if (departments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", departments);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        if (departmentService.existsDepartmentByName(department.getName())) {

            return new ResponseEntity<>(new ResponseMessage("name already exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Department departmentRequest) {

        Department department = departmentService.findById(id)
                .map(department1 -> {
                    department1.setName(departmentRequest.getName());
                    department1.setDescription(departmentRequest.getDescription());
                    if (departmentRequest.isActive() != null){
                        department1.setActive(departmentRequest.isActive());
                    }
                    return departmentService.save(department1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));
        department.setActive(false);
        departmentService.save(department);

        return new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<?> getEmployees(@PathVariable Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Department"));

        return new ResponseEntity<>(department.getEmployees(), HttpStatus.OK);
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


