package com.yuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author rog
 * @description AppointmentMapper
 * @date 2025年8月6日22:49:57
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {


    /**
     * getOneByUsernameAndIdCard
     * 获取用户名和身份证匹配的预约信息
     * @param username
     * @param idCard
     * @return
     */
    Appointment getOneByUsernameAndIdCard(@Param("username") String username,
                                          @Param("idCard") String idCard);


}
