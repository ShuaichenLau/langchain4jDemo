package com.yuan.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 预约表
 * @author liusc
 *
 * 2025年7月6日09:39:54
 */
public class Appointment {

    @TableId(type = IdType.AUTO)
    private Long id;//  BIGINT NOT NULL AUTO_INCREMENT,
    private String username;//  VARCHAR(50) NOT NULL,
    private String idCard;//  VARCHAR(18) NOT NULL,
    private String department;//  VARCHAR(50) NOT NULL,
    private String date;//  VARCHAR(10) NOT NULL,
    private String time;//  VARCHAR(10) NOT NULL,
    private String doctorName;//  VARCHAR(50) DEFAULT NULL,

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
