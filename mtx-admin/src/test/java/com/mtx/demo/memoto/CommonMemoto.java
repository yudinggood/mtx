package com.mtx.demo.memoto;

/**
 * Created by Administrator on 2018/4/25.
 */
public class CommonMemoto {//备忘录模式  类似事务     此为备份类
	private String name;
	private String sex;

	public CommonMemoto(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
