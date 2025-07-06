package com.yuan.tools;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.entity.Appointment;
import com.yuan.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppointmentTools {
    private Logger logger = LoggerFactory.getLogger(AppointmentTools.class);


    @Autowired
    private AppointmentService appointmentService;

    @Tool(name = "预约挂号,记录挂号记录", value = "根据参数,先执行queryAppointment方法查询患者信息是否可以预约,并直接给患者回答是否可以预约,患者回复确认后在进行预约挂号")
    public String bookAppointment(Appointment appointment) {
        logger.info("1查找对应的预约记录 {} ", JSON.toJSONString(appointment));
        appointment.setId(null); // 防止大模型手动赋值 大模型出现幻觉设置ID
        Appointment oneByUsernameAndIdCard = appointmentService.getOneV2(appointment);
        if (Objects.isNull(oneByUsernameAndIdCard)) {
            if (appointmentService.save(appointment)) {
                return "预约成功";
            } else {
                return "预约失败";
            }
        }
        return "您已预约过此号源";
    }

    @Tool(name = "取消预约挂号,删除预约挂号记录", value = "根据参数,查询预约是否存在,如果存在则删除,并返回给患者取消预约成功,否则返回给患者取消预约失败")
    public String cancelBookAppointment(Appointment appointment) {
        logger.info("2查找对应的预约记录 {} ", JSON.toJSONString(appointment));
        Appointment oneByUsernameAndIdCard = appointmentService.getOneV2(appointment);

        if (Objects.isNull(oneByUsernameAndIdCard)) {
            return "患者您好,你没有预约记录";
        } else {
            logger.info("取消预约 {} ", JSON.toJSONString(oneByUsernameAndIdCard));
            appointmentService.deleteById(oneByUsernameAndIdCard.getId());
            return "患者您好,挂号预约已经取消";
        }
    }


    @Tool(name = "查询是否有号源", value = "根据参数科室名称,日期,时间,医生查询是否号源,并返回给患者")
    public boolean queryAppointment(@P(value = "科室名称") String department,
                                    @P(value = "日期") String date,
                                    @P(value = "时间, 可选值:上午,下午") String time,
                                    @P(value = "医生名称", required = false) String doctorName) {
        logger.info("查询预约挂号 科室名称[{}]  日期[{}]  时间[{}]  医生名称[{}] ", department, date, time, doctorName);

        LambdaQueryWrapper<Appointment> appointmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        appointmentLambdaQueryWrapper.eq(Appointment::getDepartment, department);
        appointmentLambdaQueryWrapper.eq(Appointment::getDate, date);
        appointmentLambdaQueryWrapper.eq(Appointment::getTime, time);
        appointmentLambdaQueryWrapper.eq(Appointment::getDoctorName, doctorName);

        Appointment oneByUsernameAndIdCard = appointmentService.getOne(appointmentLambdaQueryWrapper);
        if (Objects.isNull(oneByUsernameAndIdCard)) {
            return true;
        }
        return false;
    }
}
