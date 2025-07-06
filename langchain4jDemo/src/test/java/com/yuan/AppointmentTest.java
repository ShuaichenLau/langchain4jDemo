package com.yuan;

import com.alibaba.fastjson.JSON;
import com.yuan.entity.Appointment;
import com.yuan.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LangChainDemoMain.class)
public class AppointmentTest {

    private Logger logger = LoggerFactory.getLogger(AppointmentTest.class);
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testGetOne() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("43012319990101001X");
        Appointment one = appointmentService.getOne(appointment);
        logger.info("one: {}", JSON.toJSONString(one));

    }

    @Test
    public void testGetOne1() {

        Appointment one1 = appointmentService.getOneByUsernameAndIdCard("张三", "43012319990101001X");
        logger.info("one1: {}", JSON.toJSONString(one1));
    }
}
