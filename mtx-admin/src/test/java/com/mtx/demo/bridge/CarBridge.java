package com.mtx.demo.bridge;

/**
 * Created by Administrator on 2018/4/25.
 */
public abstract class CarBridge {//桥接模式
	private Engine engine;

	public CarBridge(Engine engine) {
		this.engine = engine;
	}

	public abstract void installEngine();

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
