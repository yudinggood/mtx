package com.mtx.demo.interpreter;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Context {
	private String input;
	private int output;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

	public Context(String input) {
		this.input = input;
	}
}
