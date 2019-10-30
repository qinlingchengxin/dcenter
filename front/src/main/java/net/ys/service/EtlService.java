package net.ys.service;

import net.ys.dao.EtlDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EtlService {

    @Resource
    private EtlDao etlDao;

}
