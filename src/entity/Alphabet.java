package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Alphabet {
	private List<Character> keys;
	private List<Integer> values;
	
	public Alphabet(int key) {
		setKeys(key);
		setValues(key);
	}
	
	public Alphabet(String format) {
		custom(format);
	}
	
	public List<Character> getKeys() {
		return keys;
	}
	
	private void setKeys(int key) {
		this.keys = new ArrayList<>();
		char start = 'a';
		for(int i=1;i<=2;i++) {
			this.keys.add(start++);
		}
		for(int i=5;i<=10;i+=5) {
			this.keys.add(start++);
		}
		for(int i=20;i<=255;i+=key) {
			this.keys.add(start++);
		}
	}
	
	public List<Integer> getValues() {
		return values;
	}
	
	private void setValues(int key) {
		this.values = new ArrayList<>();
		for(int i=1;i<=2;i++) {
			this.values.add(i);
		}
		for(int i=5;i<=10;i+=5) {
			this.values.add(i);
		}
		for(int i=20;i<=255;i+=key) {
			this.values.add(i);
		}
	}

	public void custom(String format) {
		List<String> data = Arrays.asList(format.split(";"));
		List<Character> keys = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		
		for(String n : data){
			String[] tmp = n.split(":");

			keys.add(tmp[0].charAt(0));
			values.add(Integer.parseInt(tmp[1]));
		}

		this.keys = keys;
		this.values = values;
	}
}
