package com.mtx.demo.composite;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */
public class File implements Ifile{
	private String name;

	public File(String name) {
		this.name = name;
	}

	@Override
	public void display() {
		System.out.println(name);
	}

	@Override
	public boolean add(Ifile ifile) {
		return false;
	}

	@Override
	public boolean remove(Ifile ifile) {
		return false;
	}

	@Override
	public List<Ifile> getChild() {
		return null;
	}
}
