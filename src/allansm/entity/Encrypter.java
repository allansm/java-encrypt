package allansm.entity;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encrypter {
	public static enum TYPE{RANDOM,LINEAR,FASTRANDOM,FASTLINEAR};
	public int generateKey() {
		return (int)(Math.random() * ((33 - 10) + 1)) + 10;
	}
	public Data randomEncrypt(Data data,Alphabet alphabet)throws Exception {
		long st = System.currentTimeMillis();
		String encrypted = "";
		int rand;
		int typeOfByte;
		byte valueBefore = 0;
		byte original = 0;
		byte[] bytes = data.getDecrypted();
		for(int i=0;i<data.getDecrypted().length;i++) {
			int aux;
			if(data.getDecrypted()[i] >=0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=(typeOfByte==1)?1:0;
			
			original = bytes[i];
			aux=bytes[i]*typeOfByte;
			while(true) {
				rand = (int) ((Math.random() * (((alphabet.getValues().size()-1) - 0) + 1)) + 0);
				try {
					if(aux >= alphabet.getValues().get(rand) && alphabet.getValues().get(rand) != 0) {
						aux-=alphabet.getValues().get(rand);
						encrypted +=alphabet.getKeys().get(rand);
					}
					if(aux == 0) {
						encrypted+=".";
						typeOfByte = 0;
						break;
					}
					if(aux < 0) {
						Thread.sleep(1000);
						aux *=typeOfByte;
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			valueBefore = original;
		}
		data.setEncrypted(encrypted);
		return data;
	}
	public Data linearEncrypt(Data data,Alphabet alphabet) {
		long st = System.currentTimeMillis();
		String encrypted = "";
		int typeOfByte;
		byte valueBefore = 0;
		byte original = 0;
		byte[] bytes = data.getDecrypted();
		for(int i=0;i<data.getDecrypted().length;i++) {
			int aux;
			if(data.getDecrypted()[i] >=0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=(typeOfByte==1)?1:0;
			original = bytes[i];
			aux=bytes[i]*typeOfByte;
			for(int rand =alphabet.getValues().size()-1;true;rand--) {
				try {
					rand = (rand<0)?alphabet.getValues().size()-1:rand;
					if(aux >= alphabet.getValues().get(rand) && alphabet.getValues().get(rand) != 0) {
						aux-=alphabet.getValues().get(rand);
						encrypted +=alphabet.getKeys().get(rand);
					}
					if(aux == 0) {
						encrypted+=".";
						typeOfByte = 0;
						break;
					}
					if(aux < 0) {
						Thread.sleep(1000);
						aux *=typeOfByte;
					}
				}catch(Exception e) {
					System.out.println(rand);
					System.out.println(alphabet.getValues().size());
					e.printStackTrace();
				}
			}
		}
		data.setEncrypted(encrypted);
		System.out.println("time:"+((System.currentTimeMillis()-st)/1000));
		return data;
	}
	private List<String> enc;
	private int index=0;
	private boolean isTheEnd;
	private List<String> getEnc() {
		return enc;
	}
	
	private synchronized void addToEnc(String enc,int i) {
		this.enc.add(i,enc);
	}
	private synchronized void addIndex() {
		this.index+=1;
	}
	private synchronized int getIndex() {
		return this.index;
	}
	private synchronized void startEnd() {
		this.isTheEnd = true;
	}
	private synchronized boolean isTheEnd() {
		return this.isTheEnd;
	}
	public Data fastLinearEncrypt(Data data,Alphabet alphabet) throws Exception{
		byte[] b = data.getDecrypted();
		byte[] bytes = null;
		int count = 0;
		this.enc = new ArrayList<>();
		int size = b.length/100;
		for(int i=0;i<size;i++) {
			this.enc.add(i,"");
		}
		for(int i=0;i<b.length;i++) {
			if(count == 0) {
				if(b.length-i > 98) {
					bytes = new byte[99];
				}else {
					bytes = new byte[b.length-i];
				}
			}
			bytes[count] = b[i];
			count++;
			if(count > 98 || i ==b.length-1) {
				Data d = new Data();
				d.setDecrypted(bytes);
				Thread.sleep(100);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("thread start");
						try {
							int index = getIndex();
							System.out.println("start:"+index);
							addIndex();
							String txt = linearEncrypt(d, alphabet).getEncrypted();
							addToEnc(txt,index);
							System.err.println("end thread :"+index);
							if(index == size) {
								startEnd();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.exit(0);
						}
					}
				}).start();
				count = 0;
			}
		}
		String encrypted = "";
		while(!isTheEnd()) {
			Thread.sleep(100);
		}
		Thread.sleep(5000);
		System.out.println(getEnc());
		for(String s:getEnc()) {
			Thread.sleep(10);
			encrypted+=s;
		}
		data.setEncrypted(encrypted);
		return data;
	}
	public Data fastRandomEncrypt(Data data,Alphabet alphabet) throws Exception{
		byte[] b = data.getDecrypted();
		byte[] bytes = null;
		int count = 0;
		this.enc = new ArrayList<>();
		int size = b.length/100;
		for(int i=0;i<size;i++) {
			this.enc.add(i,"");
		}
		for(int i=0;i<b.length;i++) {
			if(count == 0) {
				if(b.length-i > 98) {
					bytes = new byte[99];
				}else {
					bytes = new byte[b.length-i];
				}
			}
			bytes[count] = b[i];
			count++;
			if(count > 98 || i ==b.length-1) {
				Data d = new Data();
				d.setDecrypted(bytes);
				Thread.sleep(100);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("thread start");
						try {
							int index = getIndex();
							System.out.println("start:"+index);
							addIndex();
							String txt = randomEncrypt(d, alphabet).getEncrypted();
							addToEnc(txt,index);
							System.err.println("end thread :"+index);
							if(index == size) {
								startEnd();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.exit(0);
						}
					}
				}).start();
				count = 0;
			}
			
		}
		String encrypted = "";
		while(!isTheEnd()) {
			Thread.sleep(100);
		}
		System.out.println("waiting..");
		Thread.sleep(5000);
		System.out.println(getEnc());
		for(String s:getEnc()) {
			Thread.sleep(10);
			encrypted+=s;
		}
		data.setEncrypted(encrypted);
		return data;
	}
	public List<String> split(String encrypted){
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
		return list;
	}
	public Data decrypt(Data data,Alphabet alphabet) {
		List<String> list = split(data.getEncrypted());
		byte[] bytes = new byte[list.size()];
		int count = 0;
		for(String s:list) {
			for(int i=1;i<s.length();i++) {
				for(int ii=0;ii<alphabet.getKeys().size();ii++) {
					if(alphabet.getKeys().get(ii) == s.charAt(i)) {
						bytes[count]+=alphabet.getValues().get(ii);
					}
				}
			}
			bytes[count]*=(s.charAt(0)=='1')?1:-1;
			count++;
		}
		data.setDecrypted(bytes);
		return data;
	}
}
