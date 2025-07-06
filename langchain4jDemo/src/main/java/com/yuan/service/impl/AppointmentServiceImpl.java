package com.yuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.entity.Appointment;
import com.yuan.mapper.AppointmentMapper;
import com.yuan.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    private Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
     *
     * @param appointment
     * @return
     */
    @Override
    public Appointment getOne(Appointment appointment) {
        QueryWrapper<Appointment> appointmentQueryWrapper = new QueryWrapper<>();
        appointmentQueryWrapper.eq("username", appointment.getUsername());
        appointmentQueryWrapper.eq("id_card", appointment.getIdCard());
        return appointmentMapper.selectOne(appointmentQueryWrapper);
    }

    @Override
    public Appointment getOneV1(Appointment appointment) {
        LambdaQueryWrapper<Appointment> appointmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        appointmentLambdaQueryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        appointmentLambdaQueryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        return appointmentMapper.selectOne(appointmentLambdaQueryWrapper);
    }

    @Override
    public Appointment getOneV2(Appointment appointment) {
        LambdaQueryWrapper<Appointment> appointmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        appointmentLambdaQueryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        appointmentLambdaQueryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        appointmentLambdaQueryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        appointmentLambdaQueryWrapper.eq(Appointment::getDate, appointment.getDate());
        appointmentLambdaQueryWrapper.eq(Appointment::getDoctorName, appointment.getDoctorName());
        return appointmentMapper.selectOne(appointmentLambdaQueryWrapper);
    }

    /**
     *
     * @param username
     * @param idCard
     * @return
     */
    @Override
    public Appointment getOneByUsernameAndIdCard(String username, String idCard) {
        return appointmentMapper.getOneByUsernameAndIdCard(username, idCard);
    }

    @Override
    public int deleteById(Long id) {
        return appointmentMapper.deleteById(id);
    }
}
