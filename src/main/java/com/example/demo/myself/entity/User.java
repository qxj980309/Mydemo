package com.example.demo.myself.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


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
//    @NotNull(message = "id不能为空")
    private Integer userId;
    /**
     * 用户名
     */
    @TableField(value = "user_name")
//    @NotBlank(message = "名字不能为空")
    private String userName;
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
    @TableField(value = "role_id")
    private Integer roleId;
    /**
     * 用户权限, 0-游客, 1-普通用户, 2-会员用户, 3-管理员
     */
    private Integer role;
    /**
     * 注册日期
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-8")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleid) {
        this.roleId = roleid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId().equals(user.getUserId()) &&
                getUserName().equals(user.getUserName()) &&
                getAge().equals(user.getAge()) &&
                getMobile().equals(user.getMobile()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getState().equals(user.getState()) &&
                getRoleId().equals(user.getRoleId()) &&
                getRole().equals(user.getRole()) &&
                getCreateTime().equals(user.getCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserName(), getAge(), getMobile(),
                getEmail(), getPassword(), getState(), getRoleId(), getRole(),
                getCreateTime());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", roleId=" + roleId +
                ", role=" + role +
                ", createTime=" + createTime +
                '}';
    }

    //    @Override
//    public void afterAssembly() {
//        System.out.println("--------使用了afterAssembly方法----------");
//    }



}
