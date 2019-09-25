package com.mtx.demo.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Folder implements Ifile{
	private String name;
	private List<Ifile> children;
	public Folder(String name) {
		children=new ArrayList<Ifile>();
		this.name = name;
	}
	@Override
	public void display() {
		System.out.println(name);
	}

	@Override
	public boolean add(Ifile ifile) {
		return children.add(ifile);
	}

	@Override
	public boolean remove(Ifile ifile) {
		return children.remove(ifile);
	}

	@Override
	public List<Ifile> getChild() {
		return children;
	}
}
