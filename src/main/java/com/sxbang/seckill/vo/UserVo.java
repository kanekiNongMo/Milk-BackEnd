package com.sxbang.seckill.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kaneki
 * @date 2019/6/22 0:08
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -7860362944750581057L;
    private String username;
    private String password;
    private Integer id;
    private String rePassword;
    private String dbFlag;

    @Override
    public String toString() {
        return "UserVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", rePassword='" + rePassword + '\'' +
                ", dbFlag='" + dbFlag + '\'' +
                '}';
    }
}
