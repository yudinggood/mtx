package com.mtx.demo.iterator;

/**
 * Created by Administrator on 2018/4/20.
 */
public class Book {
	private String TSBN;
	private String name;
	private Double price;

	public Book(String TSBN, String name, Double price) {
		this.TSBN = TSBN;
		this.name = name;
		this.price = price;
	}

	public String getTSBN() {
		return TSBN;
	}

	public void setTSBN(String TSBN) {
		this.TSBN = TSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	public void display(){
		System.out.println(TSBN+"---"+name);
	}
}
