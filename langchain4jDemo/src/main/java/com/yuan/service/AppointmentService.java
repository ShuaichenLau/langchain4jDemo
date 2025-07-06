package com.yuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.entity.Appointment;

/**
 *
 */
public interface AppointmentService extends IService<Appointment> {

    Appointment getOne(Appointment appointment);
    Appointment getOneV1(Appointment appointment);

    Appointment getOneByUsernameAndIdCard(String username, String idCard);
}
