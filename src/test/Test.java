package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
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
	public void t2() {
		
	}
	public static void main(String [] args) {
		t1();
	}
}
