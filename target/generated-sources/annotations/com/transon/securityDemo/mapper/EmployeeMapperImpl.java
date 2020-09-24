package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Employee;
import com.transon.securityDemo.requestModel.RequestUpdateEmployee;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-24T11:07:17+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_265 (AdoptOpenJDK)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee employeeRequestToEmployee(RequestUpdateEmployee requestUpdateEmployee) {
        if ( requestUpdateEmployee == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setFullname( requestUpdateEmployee.getFullname() );
        employee.setBirthday( requestUpdateEmployee.getBirthday() );
        employee.setAvatar( requestUpdateEmployee.getAvatar() );
        employee.setEmail( requestUpdateEmployee.getEmail() );
        employee.setPhone( requestUpdateEmployee.getPhone() );
        employee.setSalary( requestUpdateEmployee.getSalary() );
        employee.setGender( requestUpdateEmployee.getGender() );

        return employee;
    }
}
