package entity;

import java.util.ArrayList;
import java.util.List;

public class Alphabet {
	private List<Character> keys;
	private List<Integer> values;
	public Alphabet(int key) {
		setKeys(key);
		setValues(key);
	}
	public List<Character> getKeys() {
		return keys;
	}
	public void setKeys(int key) {
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
	public void setValues(int key) {
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
}
