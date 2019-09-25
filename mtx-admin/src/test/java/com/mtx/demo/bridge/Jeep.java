package com.mtx.demo.bridge;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Jeep extends CarBridge {
	public Jeep(Engine engine) {
		super(engine);
	}

	@Override
	public void installEngine() {
		System.out.print("Jeep--");
		this.getEngine().installEngine();
	}
}
