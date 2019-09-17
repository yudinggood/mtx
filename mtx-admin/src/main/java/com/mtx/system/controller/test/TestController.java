package com.mtx.system.controller.test;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mtx.common.base.BaseController;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.common.util.validator.LengthValidator;
import com.mtx.common.util.wrapper.WrapMapper;
import com.mtx.common.util.wrapper.Wrapper;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.rpc.api.SystemPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Api(value = "工具类", description = "工具类与测试类")
@RequestMapping(value="/test")
public class TestController extends BaseController{
    @Autowired
    private SystemPermissionService systemPermissionService;

    @ApiOperation(value = "当前cookies查看")
    //@RequiresPermissions("system:test:cookies")
    @RequestMapping(value = "/cookies", method = RequestMethod.GET)
    @ResponseBody
    public Object getCookies(HttpServletRequest request){//为当前浏览器中所有cookie
        Map<Object,Object> map=new HashMap<>(0);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for(Cookie cookieTemp : cookies){
                map.put(cookieTemp.getName(),cookieTemp.getValue());
            }
        }
        return map;
        //{"upms-server-session-id":"05177d9a-b4c8-45b9-b0c1-bb667fc1a8fe","JSESSIONID":"1cye8zl1okqcy5k5xmc5yfrzl"}
    }

    @ApiOperation(value = "当前session查看")
    //@RequiresPermissions("system:test:session")
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public Object getSessions(){
        Map<Object,Object> map=new HashMap<>();
        HttpSession session=getSession();
        map.put("id", session.getId().toString());
        Enumeration enumeration =session.getAttributeNames();
        while(enumeration.hasMoreElements()){
            String addFileName=enumeration.nextElement().toString();
            String value=session.getAttribute(addFileName).toString();
            map.put(addFileName,value);
        }
        return map;
        //{"sessionSecCode":"03J5","upms.type":"server","id":"05177d9a-b4c8-45b9-b0c1-bb667fc1a8fe"}
    }

    @ApiOperation(value = "当前header查看")
    //@RequiresPermissions("system:test:header")
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    @ResponseBody
    public Object getHeader( HttpServletRequest request){
        Map<Object,Object> map=new HashMap<>();
        Enumeration enu = request.getHeaderNames();//取得全部头信息
        while (enu.hasMoreElements()) {
            String headerName = (String) enu.nextElement();
            String headerValue = request.getHeader(headerName);
            map.put(headerName,headerValue);
        }
        return map;
        //{"Cookie":"JSESSIONID=1f73j4s4psayqenzxm8dv9kox","Accept":"text/html,application/xhtml+xml,application/xml;
        // q=0.9,image/webp,image/apng,*/*;q=0.8","Upgrade-Insecure-Requests":"1","Connection":"keep-alive",
        // "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36",
        // "Host":"admin.mtx.com:1111","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}
    }

    // http://admin.mtx.com:1111/system/test/test
    @ApiOperation(value = "page测试")
    @RequiresPermissions("system:test:test")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(){
        //throw new UnauthorizedException();
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("/system/test/test");
        return mv;
    }
    // http://admin.mtx.com:1111/system/test/test2
    @ApiOperation(value = "service测试2")
    @RequiresPermissions("system:test:test")
    @RequestMapping(value = "/test2", method    = RequestMethod.GET)
    @ResponseBody
    public Object test2(){
        //int a=1/0;

        String s =systemPermissionService.test();
        return s;
    }
    // http://admin.mtx.com:1111/system/test/test3
    @ApiOperation(value = "业务单元测试3")
    @RequiresPermissions("system:test:test")
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    @ResponseBody
    public Object test3(){
        SystemPermissionDto systemPermissionDto =new SystemPermissionDto();
        systemPermissionDto.setName("44");

        ComplexResult result = FluentValidator.checkAll()
                .on(systemPermissionDto.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_,errorListToString(result.getErrors()));
        }

        //int count = systemPermissionService.insertDto(systemPermissionDto);
        return WrapMapper.wrap(1);
    }
    // http://admin.mtx.com:1111/system/test/test4
    @ApiOperation(value = "method测试4")
    @RequiresPermissions("system:test:test")
    @RequestMapping(value = "/test4", method    = RequestMethod.GET)
    @ResponseBody
    public Object test4(){
        log.info(GlobalProperties.me().getValueByCodeProperties(PropertiesEnum.COMMON_INIT_PASSWORD));
        log.info(GlobalProperties.me().getValueByCode(PropertiesEnum.INDEX_PAGE));


        return WrapMapper.wrap(0);

    }
    //
    @ApiOperation(value = "method测试5")
    @RequestMapping(value = "/test5", method    = RequestMethod.GET)
    @ResponseBody
    public Object test5(){


        return RequestUtil.getIpAddr(getHttpServletRequest());

    }
}
