package com.aisa.itservice.testcarwash.Entites;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", allocationSize = 1, sequenceName = "user_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "password")
    private String password;

    @Transient
    private String copyPassword;

    @Column(name = "is_admin")
    private Boolean admin;

    @Column(name = "time_registration")
    private Date timeRegistration;

    public User(UserDto userDTO) {
        id = 0;
        name = userDTO.getName();
        email = userDTO.getEmail();
        password = userDTO.getPassword();
        admin = userDTO.getAdmin();
        timeRegistration = new Date();
    }

    public User() {

    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getCopyPassword() {
        return copyPassword;
    }

    public void setCopyPassword(String copyPassword) {
        this.copyPassword = copyPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTimeRegistration() {
        return timeRegistration;
    }

    public void setTimeRegistration(Date timeRegistration) {
        this.timeRegistration = timeRegistration;
    }
}
