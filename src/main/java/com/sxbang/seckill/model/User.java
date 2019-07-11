package com.sxbang.seckill.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author kaneki
 * @date 2019-06-21 13:28
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -5294332360278401121L;

    @Id
    @Column(name = "username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "re_password", nullable = false)
    private String rePassword;

    @Column(name = "db_flag")
    private String dbFlag;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
