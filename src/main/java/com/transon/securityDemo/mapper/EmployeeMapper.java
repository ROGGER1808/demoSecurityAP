package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Employee;
import com.transon.securityDemo.requestModel.RequestUpdateEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee employeeRequestToEmployee(RequestUpdateEmployee requestUpdateEmployee);
}
