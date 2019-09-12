package com.mtx.common.util.base;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
public class RegularUtil {
	public static final String PHONE="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	public static final String EMAIL="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	//把正则匹配到的结果  放入数组
	public static List<String> getRegularByString(String reg, String content){
		List<String> result=new ArrayList<>();
		if(StringUtils.isEmpty(content)){
			return result;
		}
		Pattern p = Pattern.compile(reg);//(\d+):(\d+)
		Matcher m = p.matcher(content);
		while(m.find())
		{
			for(int i = 0; i <= m.groupCount(); i++){
				result.add(m.group(i));
			}
		}
		return  result;
	}

	//正则校验
	public static boolean getRegularResult(String regex,String content){
		if(StringUtils.isEmpty(content)){
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		return m.matches();
	}
}
