package com.mtx.common.lombok;

import lombok.NonNull;
import org.junit.Test;

public class LombokTest {//Lombok相关测试

    @Test
    public void testNull(){//空检查注解,如果是null则抛出异常
        getPerson(null);
    }
    private void getPerson(@NonNull Person person){

    }

}
