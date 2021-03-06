package com.mtx.system.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath*:spring/*.xml"
})
@Transactional
public class BaseJunit {//单元测试的时候要开启service层，才会有数据


}
