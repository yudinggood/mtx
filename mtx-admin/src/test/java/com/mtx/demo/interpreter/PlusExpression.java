package com.mtx.demo.interpreter;

/**
 * Created by Administrator on 2018/4/25.
 */
public class PlusExpression extends Expression{
	public void interpreter(Context context){
		System.out.println("自动递增");
		int intinput=Integer.parseInt(context.getInput());
		intinput++;
		context.setInput(String.valueOf(intinput));
		context.setOutput(intinput);
	}

}
