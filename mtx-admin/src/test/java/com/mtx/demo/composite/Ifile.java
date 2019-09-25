package com.mtx.demo.composite;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */
public interface Ifile {//组合模式   递归
	public void display();
	public boolean add(Ifile ifile);
	public boolean remove(Ifile ifile);
	public List<Ifile> getChild();
}
