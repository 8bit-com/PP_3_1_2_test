package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(name = "name")
    @Pattern(regexp = "[A-Za-z]{2,15}", message = "Name should be between 2 and 15 latin characters")
    private String name;

    @Column(name = "surname")
    @Pattern(regexp = "[A-Za-z]{2,15}", message = "Surname should be between 2 and 15 latin characters")
    private String surname;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be >= 0")
    @Max(value = 127, message = "Age should be < 128")
    private byte age;

    @Column(name = "email")
    @Pattern(regexp = "([A-z0-9_.-]+)@([A-z0-9_.-]+).([A-z]{2,8})", message = "Enter correct email")
    private String email;

    @Column(name = "password")
    @Pattern(regexp = "([A-z0-9_.-]+)@([A-z0-9_.-]+).([A-z]{2,8})", message = "Enter correct password")
    private String password;

    @Column(name = "username")
    @Pattern(regexp = "([A-z0-9_.-]+)@([A-z0-9_.-]+).([A-z]{2,8})", message = "Enter correct username")
    private String username;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String surname, byte age, String email, String password, String username, Status status, Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.username = username;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + user_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}