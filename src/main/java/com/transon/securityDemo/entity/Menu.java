package com.transon.securityDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Menu extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    private String name;

    private String url;

    @Column(name = "order_index")
    private int orderIndex;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "menus", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Role> roles = new HashSet<>();

    @Transient
    private Set<Menu> childs;
}
