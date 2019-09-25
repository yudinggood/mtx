package com.mtx.demo.strategy;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Context {
	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	public void secret(){
		strategy.secret();
	}
}
