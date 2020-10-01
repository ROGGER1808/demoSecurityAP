package com.transon.securityDemo.requestModel;

import com.transon.securityDemo.common.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RequestUpdateEmployee {
    @NotEmpty(message = "fullname is required!")
    private String fullname;

    @NotNull(message = "birthday is required!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String avatar;

    @Email(message = "Invalid email!")
    @NotEmpty(message = "password is required!")
    private String email;

    @NotEmpty(message = "phone is required!")
    private String phone;

    private Double salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private Long departmentId;

    private Boolean isActive = true;
}
