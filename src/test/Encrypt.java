package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encrypt {
	private char b1[] = new char[60];
	private int b2[] = new int[60];
	private int key;
	private byte[] bytes;
	public void setKey() {
		this.key = (int)(Math.random() * ((12 - 5) + 1)) + 12;
		System.out.println("KEY:"+key);
	}
	public void setKey(int key) {
		this.key = key;
	}
	public void setBases() {
		char start = 'a';
		int count = 0;
		for(int i=1;i<=2;i++) {
			b1[count] = start++;
			b2[count] = i;
			System.out.println(b1[count]+" >> "+b2[count]);
			count++;
		}
		for(int i=5;i<=10;i+=5) {
			b1[count] = start++;
			b2[count] = i;
			System.out.println(b1[count]+" >> "+b2[count]);
			count++;
		}
		for(int i=20;i<=255;i+=key) {
			b1[count] = start++;
			b2[count] = i;
			System.out.println(b1[count]+" >> "+b2[count]);
			count++;
		}
	}
	public void releaseBases() {
		b1 = null;
		b2 = null;
	}
	public int findHigher(int val) {
		for(int ii = b2.length;ii>0;ii--) {
			System.out.println(val+":"+b2[ii]);
			if(val >= b2[ii]) {
				System.out.println("END");
				return ii;
			}
		}
		return 0;
	}
	public String encrypt(String toEncrypt) {
		bytes = new byte[toEncrypt.length()];
		bytes = toEncrypt.getBytes();
		int higher = 0;
		String encrypted = "";
		int rand;
		int typeOfByte;
		for(int i=0;i<bytes.length;i++) {
			System.out.print(bytes[i]+" ");
			if(bytes[i] >0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=(typeOfByte==1)?1:0;
			bytes[i]*=typeOfByte;
			while(true) {
				rand = (int) ((Math.random() * ((b2.length - 0) + 1)) + 0);
				//System.out.println("rand:"+rand);
				try {
					//b2[rand] = b2[rand]*typeOfByte;
					//higher = findHigher(bytes[i]);
					//bytes[i]-=b2[higher];
					//encrypted +=b1[higher];
					if(bytes[i] >= b2[rand] && b2[rand] != 0) {
						//System.out.println("bytes:"+bytes[i]);
						//System.out.println("b2:"+b2[rand]);
						bytes[i]-=b2[rand];
						encrypted +=b1[rand];
					}
					if(bytes[i] == 0) {
						encrypted+=".";
						typeOfByte = 0;
						break;
					}
				}catch(Exception e) {
					//e.printStackTrace();
				}
			}
		}
		System.out.println();
		System.out.println("encrypted:"+encrypted);
		System.out.println("size:"+encrypted.length());
		//t3(encrypted);
		return encrypted;
	}
	public byte[] decrypt(String encrypted) {
		List<String> list = new ArrayList<>();
		String aux = "";
		for(int i=0;i<encrypted.length();i++) {
			if(encrypted.charAt(i)=='.') {
				list.add(aux);
				aux = "";
			}else {
				aux+=encrypted.charAt(i);
			}
		}
		System.out.println(list);
		byte[] bytes = new byte[list.size()];
		int count = 0;
		for(String s:list) {
			for(int i=1;i<s.length();i++) {
				for(int ii=0;ii<b1.length;ii++) {
					if(b1[ii] == s.charAt(i)) {
						bytes[count]+=b2[ii];
					}
				}
			}
			bytes[count]*=(s.charAt(0)=='1')?1:-1;
			count++;
		}
		return bytes;
	}
	public void test() {
		setKey();
		setBases();
		System.out.print("type:");
		String encrypted = encrypt(new Scanner(System.in).next());
		byte[] bytes = decrypt(encrypted);
		System.out.println(new String(bytes));
	}
	public static void main(String[] args) {
		new Encrypt().test();
	}
}
