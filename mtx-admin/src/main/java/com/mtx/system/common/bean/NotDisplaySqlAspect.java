package com.mtx.system.common.bean;

import com.mtx.common.util.base.ThreadLocalUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotDisplaySqlAspect {
    public static final String DISPLAY_SQL = "DISPLAY_SQL";

    @Pointcut("@annotation(com.mtx.common.interceptor.NotDisplaySql)")//表示对什么样的方法使用切面
    private void myPointCut() {
    }

    /**
     * Before.
     */
    @Before(value = "myPointCut()")
    public void before() {
        ThreadLocalUtil.put(DISPLAY_SQL, Boolean.FALSE);
    }

    /**
     * After.
     */
    @After(value = "myPointCut()")
    public void after() {
        ThreadLocalUtil.remove(DISPLAY_SQL);
    }

}
