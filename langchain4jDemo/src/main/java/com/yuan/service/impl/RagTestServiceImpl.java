package com.yuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.entity.RagTestEntity;
import com.yuan.mapper.RagTestMapper;
import com.yuan.service.RagTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RagTestServiceImpl extends ServiceImpl<RagTestMapper, RagTestEntity> implements RagTestService {

    private Logger logger = LoggerFactory.getLogger(RagTestServiceImpl.class);

    @Autowired
    private RagTestMapper ragTestMapper;


}
