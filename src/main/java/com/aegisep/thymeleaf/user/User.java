package com.aegisep.thymeleaf.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@Entity
@Table(name = "USER")
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long seq;

    @Column(name = "user_id", nullable = false, length = 20)
    private String user_id;

    @Column(name = "user_name", length = 20)
    private String user_name;

    @Column(name = "passwd")
    private String passwd;

    private String auth_group;
    private String auth_id;
    private String company;
    private String add_road;
    private String add_detail;
    private String post_code;
}
