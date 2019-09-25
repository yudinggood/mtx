package com.mtx.demo.bridge;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Bus extends CarBridge {
	public Bus(Engine engine) {
		super(engine);
	}

	@Override
	public void installEngine() {
		System.out.print("Bus--");
		this.getEngine().installEngine();
	}
}
