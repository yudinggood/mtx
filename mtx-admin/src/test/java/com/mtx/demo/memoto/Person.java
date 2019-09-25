package com.mtx.demo.memoto;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Person {
	private String name;
	private String sex;

	public Person() {
	}

	public Person(String name, String sex) {
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

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", sex='" + sex + '\'' +
				'}';
	}
	public CommonMemoto create(){//创建备份
		return new CommonMemoto(name,sex);
	}
	public void setCommonMemoto(CommonMemoto commonMemoto){//还原 回滚
		this.name=commonMemoto.getName();
		this.sex=commonMemoto.getSex();
	}

}
