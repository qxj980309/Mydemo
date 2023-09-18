package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@TableName("user")
//@JsonIgnoreProperties(value = {"password"})
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "userId")
//    @NotNull(message = "id不能为空")
    private Integer userid;
    /**
     * 用户名
     */
    @TableField(value = "userName")
//    @NotBlank(message = "名字不能为空")
    private String username;
    /**
     * 年龄
     */
    private Integer age;  // 年龄（u.age）
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户密码
     */
//    @JSONField(serialize = false)
    @JsonIgnore  //隐藏这个字段返回
    private String password;
    /**
     * 用户是否被封禁, 0-未封禁，1-已封禁
     */
    private Integer state;
    /**
     * 用户权限ID, 0-游客, 1-普通用户, 2-会员用户, 3-管理员
     */
    @TableField(value = "roleId")
    private Integer roleid;
    /**
     * 用户权限, 0-游客, 1-普通用户, 2-会员用户, 3-管理员
     */
    private Integer role;
    /**
     * 注册日期
     */
    @TableField(value = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-8")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createtime;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserid().equals(user.getUserid()) &&
                getUsername().equals(user.getUsername()) &&
                getAge().equals(user.getAge()) &&
                getMobile().equals(user.getMobile()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getState().equals(user.getState()) &&
                getRoleid().equals(user.getRoleid()) &&
                getRole().equals(user.getRole()) &&
                getCreatetime().equals(user.getCreatetime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserid(), getUsername(), getAge(), getMobile(), getEmail(), getPassword(), getState(), getRoleid(), getRole(), getCreatetime());
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", roleid=" + roleid +
                ", role=" + role +
                ", createtime=" + createtime +
                '}';
    }

    //    @Override
//    public void afterAssembly() {
//        System.out.println("--------使用了afterAssembly方法----------");
//    }



}
