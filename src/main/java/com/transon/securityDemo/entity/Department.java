package com.transon.securityDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Department extends AbstractEntity implements Serializable {

    @NotNull(message = "departmentCode is required!")
    @Id
    @Column(name = "department_code")
    private String departmentCode;

    @NotEmpty(message = "name is required!")
    @Column(unique = true, nullable = false, length = 250)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "departments", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();
}
