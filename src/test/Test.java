package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	public void sockTest() throws Exception{
		//System.setProperty("file.encoding", "ISO-8859-1");
		while(true) {
			//SortedMap<String,Charset> s = Charset.availableCharsets();
			//System.out.println(s);
			ServerSocket server = new ServerSocket(80);
			//OutputStream out = server.accept().getOutputStream();
			//byte[] file = Files.readAllBytes(Paths.get("c:\\users\\allan\\downloads\\34779.jpg"));
			
			File f = new File("c:\\users\\allan\\downloads\\34779.jpg");
			byte[] file = new byte[(int)f.length()];
			new BufferedInputStream(new FileInputStream(f)).read(file,0,file.length);
			OutputStream ou = server.accept().getOutputStream();//OutputStreamWriter ou = new OutputStreamWriter(server.accept().getOutputStream());
			int i = 0;
			for(byte b:file) {
				System.out.print(b+",");
				if(i++ >9) {
					System.out.print("\n");
					i=0;
				}
			}
			//file = "<div style='width:20px;height:20px;background:blue'></div>".getBytes();
			ou.write(file);
			Thread.sleep(1000);
			ou.close();
			server.close();
			System.out.println("foi");
		}
	}
	public static void t1() {
		System.out.print("put the text:");
		byte[] bytes = new Scanner(System.in).next().getBytes();
		System.out.println();
		int i = 0;
		System.out.println("normal");
		for(byte b:bytes) {
			System.out.print(b+" ");
			if(i++ >9) {
				System.out.print("\n");
				i=0;
			}
		}
		System.out.println();
		System.out.println(new String(bytes));
		System.out.println();
		i = 0;
		int count=0;
		byte[] newByte = new byte[bytes.length];
		for(byte b:bytes) {
			newByte[count++] = (byte)(b+10);
			if(i++ >9) {
				System.out.print("\n");
				i=0;
			}
		}
		System.out.println("encrypted");
		for(byte b:newByte) {
			System.out.print(b+" ");
			if(i++ >9) {
				System.out.print("\n");
				i=0;
			}
		}
		System.out.println();
		System.out.println(new String(newByte));
		System.out.println();
		byte[] decrypt = new byte[newByte.length];
		System.out.println("decrypted");
		count = 0;
		for(byte b:newByte) {
			b = (byte)(b-10);
			System.out.print(b+" ");
			decrypt[count++] = b;
			if(i++ >9) {
				System.out.print("\n");
				i=0;
			}
		}
		System.out.println();
		System.out.println(new String(decrypt));
	}
	public static void t2() {
		char start = 'a';
		char[] b1  = new char[19];
		int[] b2 = new int[19];
		int count = 0;
		for(int i=1;i<=10;i++) {
			//System.out.println(start+" >> "+i);
			b1[count] = start;
			b2[count++] = i;
			start++;
		}
		for(int i=20;i<=100;i+=10) {
			//System.out.println(start+" >> "+i);
			b1[count] = start;
			b2[count++] = i;
			start++;
		}
		//(Math.random() * ((max - min) + 1)) + min
		String toEncrypt = new Scanner(System.in).next();
		byte[] bytes = new byte[toEncrypt.length()];
		bytes = toEncrypt.getBytes();
		String encrypted = "";
		int rand;
		int typeOfByte;
		for(int i=0;i<bytes.length;i++) {
			if(bytes[i] >0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=typeOfByte+">>";
			while(true) {
				rand = (int) ((Math.random() * ((19 - 0) + 1)) + 0);
				try {
					//b2[rand] = b2[rand]*typeOfByte;
					
					if(typeOfByte >0) {
						System.out.println(bytes[i]+ ">="+ b2[rand]);
					if(bytes[i] >= b2[rand]) {
						System.out.println(bytes[i]);
						System.out.println(b2[rand]);
						bytes[i]-=b2[rand];
						encrypted +=b1[rand];
						
					}
					}else {
						System.out.println(bytes[i]+ "<="+ b2[rand]);
						if(bytes[i] <= b2[rand]) {
							System.out.println(bytes[i]);
							System.out.println(b2[rand]);
							bytes[i]+=b2[rand];
							encrypted +=b1[rand];
						}
					}
					if(bytes[i] == 0) {
						encrypted+=".";
						typeOfByte = 0;
						break;
					}
				}catch(Exception e) {
					
				}
			}
		}
		System.out.println("encrypted:"+encrypted);
		
	}
	public static void main(String [] args)throws Exception {
		new Test().sockTest();
		//t1();
		//t2();
	}
}
