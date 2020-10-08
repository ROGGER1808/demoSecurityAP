package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IDepartmentService;
import com.transon.securityDemo.services.IRoleService;
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
    private final IRoleService roleService;

    public DepartmentController(IDepartmentService departmentService, IRoleService roleService) {
        this.departmentService = departmentService;
        this.roleService = roleService;
    }

    /*
    @getAll department
    @transon
    */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }


    /*
    @getAll have sort and paging, filter
    @transon
    */
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

    /*
    @create Department
    @transon
    */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Department department) {
        if (departmentService.existsDepartmentByName(department.getDepartmentCode())) {

            return new ResponseEntity<>(new ResponseMessage("name already exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        departmentService.save(department);
        Role role = new Role();
        role.setName(department.getDepartmentCode());
        roleService.save(role);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /*
    @get Department by code
    @transon
    */
    @GetMapping("/{departmentCode}")
    public ResponseEntity<?> get(@PathVariable String departmentCode) {
        Department department = departmentService.findDepartmentsByDepartmentCode(departmentCode)
                .orElseThrow(() -> new NotFoundEntityException(departmentCode, "Department"));

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /*
    @update Department
    @transon
    */
    @PutMapping("/{departmentCode}")
    public ResponseEntity<?> update(@PathVariable String departmentCode, @Valid @RequestBody Department departmentRequest) {

        Department department = departmentService.findDepartmentsByDepartmentCode(departmentCode)
                .map(department1 -> {
                    department1.setName(departmentRequest.getName());
                    department1.setDescription(departmentRequest.getDescription());
                    if (departmentRequest.isActive() != null){
                        department1.setActive(departmentRequest.isActive());
                    }
                    return departmentService.save(department1);
                })
                .orElseThrow(() -> new NotFoundEntityException(departmentCode, "Department"));

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /*
    @delete Department by code
    @transon
    */
    @DeleteMapping("/{departmentCode}")
    public ResponseEntity<?> delete(@PathVariable String departmentCode) {
        roleService.deleteRoleByName(departmentCode);
        departmentService.deleteDepartmentByDepartmentCode(departmentCode);
        return new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    /*
    @getUsers get users have this department
    @transon
    */
    @GetMapping("/{departmentCode}/users")
    public ResponseEntity<?> getUsers(@PathVariable String departmentCode) {
        Department department = departmentService.findDepartmentsByDepartmentCode(departmentCode)
                .orElseThrow(() -> new NotFoundEntityException(departmentCode, "Department"));

        return new ResponseEntity<>(department.getUsers(), HttpStatus.OK);
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


