package com.mtx.demo.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MyCharacterFactory {//享元模式   共享变量
	private Map<Character,MyCharacter> pool;

	public MyCharacterFactory() {
		pool=new HashMap<Character,MyCharacter>();
	}

	public MyCharacter getMyCharacter(Character character){
		MyCharacter myCharacter=pool.get(character);
		if(myCharacter == null){
			myCharacter=new MyCharacter(character);
			pool.put(character,myCharacter);
		}
		return myCharacter;
	}

}
