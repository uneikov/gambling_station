package com.uran.gamblingstation.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty
    @SafeHtml
    private String name;

    @Email
    @NotEmpty
    @SafeHtml
    private String email;

    @Size(min = 5, max = 64, message = " must between 5 and 64 characters")
    @SafeHtml
    private String password;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "UserDTO{"
                + "id=" + id +
                ", name='" + name + '\''
                + ", email='" + email + '\''
                + '}';
    }
}
