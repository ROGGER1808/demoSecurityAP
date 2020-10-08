package com.transon.securityDemo.entity;

import com.transon.securityDemo.common.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Employee extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "fullname is required!")
    @Column(unique = true, nullable = false, length = 250)
    private String fullname;

    @NotNull(message = "birthday is required!")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String avatar;

    @Email(message = "Invalid email!")
    @NotEmpty(message = "password is required!")
    @Column(nullable = false, length = 200)
    private String email;

    @NotEmpty(message = "phone is required!")
    @Column(nullable = false, length = 11)
    private String phone;

    private Double salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


}
