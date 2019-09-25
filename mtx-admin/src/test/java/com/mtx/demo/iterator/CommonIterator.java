package com.mtx.demo.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */
public class CommonIterator  {//迭代模式
	private List<Book> bookList ;
	private int i=0;
	private Iterator iterator;

	public CommonIterator() {
		bookList = new ArrayList<Book>();
	}
	public Iterator iterator(){
		return new Itr();
	}
	private class Itr implements Iterator{

		@Override
		public boolean hasNext() {
			if(i>=bookList.size()){
				return false;
			}
			return true;
		}

		@Override
		public Object next() {
			return bookList.get(i++);
		}

		@Override
		public void remove() {

		}
	}
	public void addBook(Book book){
		bookList.add(book);
	}
	public void deleteBook(Book book){
		int index=bookList.indexOf(book);
		bookList.remove(index);
	}

}
