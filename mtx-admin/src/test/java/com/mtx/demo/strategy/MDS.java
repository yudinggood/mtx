package com.mtx.demo.strategy;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MDS implements Strategy{
	@Override
	public void secret() {
		System.out.println("执行MDS加密");
	}
}
