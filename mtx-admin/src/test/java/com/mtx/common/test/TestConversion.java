package com.mtx.common.test;

import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.secret.MD5Util;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import lombok.extern.java.Log;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

@Log
public class TestConversion {
    @Test
    public void test(){

        System.out.print(TypeConversionUtil.blobToString(null));

    }

    @Test
    public void test2(){
        String password = MD5Util.md5("111111a"+"8a1701b52b2e4d15a1b9bd349ab76e9a");
        System.out.print(password);
    }

    @Test//现在的加密方式
    public void test3(){
        String s = "90f3e8f130ba42b59c0a3a119dcf1051";
        Object salt = ByteSource.Util.bytes(s);
        SimpleHash simpleHash=new SimpleHash("MD5", "111111a", salt, 2);


        System.out.print(s+"&"+String.valueOf(simpleHash));
    }


}
