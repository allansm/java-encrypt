package entity;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encrypter {
	public int generateKey() {
		return (int)(Math.random() * ((33 - 10) + 1)) + 10;
	}
	//wrong
	public byte[][] arraySmash(byte[] array) {
		try {
			int[] sizes = new int[9];
			for(int i=0;i<10;i++) {
				sizes[i] = array.length/i+1;
			}
			//int start =0;
		
			byte[][] bytes = new byte[array.length/10][9];
			int count = 0;
			for(int i=0;i<9;i++) {
				for(int ii=0;ii<array.length/10;ii++) {
					bytes[ii][i] = array[count++];
				}
			}
			System.out.println("array smashed");
			return bytes;
		}catch(Exception e) {
			System.out.println(array.length);
			byte[][] bytes = new byte[array.length][1];
			for(int i=0;i<array.length;i++) {
				//System.out.println(array[i]);
				bytes[i][0] = array[i];
				//System.out.println("end");
			}
			return bytes;
		}
	}
	public Data encrypt(Data data,Alphabet alphabet)throws Exception {
		long st = System.currentTimeMillis();
		/*Data data = new Data();
		data.setDecrypted(new byte[toEncrypt.length()]);
		data.setDecrypted(toEncrypt.getBytes());
		data.setKey(generateKey());*/
		//Alphabet alphabet = new Alphabet(data.getKey());
		//int higher = 0;
		String encrypted = "";
		int rand;
		int typeOfByte;
		byte valueBefore = 0;
		byte original = 0;
		byte[] bytes = data.getDecrypted();
		for(int i=0;i<data.getDecrypted().length;i++) {
			int aux;
			System.out.print(data.getDecrypted()[i]+"\n");
			if(data.getDecrypted()[i] >=0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=(typeOfByte==1)?1:0;
			
			original = bytes[i];
			aux=bytes[i]*typeOfByte;
			while(true) {
				//Thread.sleep(1);
				rand = (int) ((Math.random() * (((alphabet.getValues().size()-1) - 0) + 1)) + 0);
				try {
					System.out.println("i:"+i+"/"+data.getDecrypted().length+" "+aux+" "+alphabet.getValues().get(rand)+" "+valueBefore+" "+((100/data.getDecrypted().length)*i)+"% "+typeOfByte);
					if(aux >= alphabet.getValues().get(rand) && alphabet.getValues().get(rand) != 0) {
						aux-=alphabet.getValues().get(rand);
						encrypted +=alphabet.getKeys().get(rand);
					}
					if(aux == 0) {
						encrypted+=".";
						typeOfByte = 0;
						System.out.println("end");
						break;
					}
					if(aux < 0) {
						Thread.sleep(1000);
						System.out.println(data.getDecrypted()[i]+" "+typeOfByte);
						aux *=typeOfByte;
					}
				}catch(Exception e) {
					System.out.println(rand);
					System.out.println(alphabet.getValues().size());
					e.printStackTrace();
				}
			}
			valueBefore = original;
		}
		//System.out.println();
		//System.out.println("encrypted:"+encrypted);
		//System.out.println("size:"+encrypted.length());
		//t3(encrypted);
		data.setEncrypted(encrypted);
		System.out.println("time:"+((System.currentTimeMillis()-st)/1000));
		return data;
	}
	public Data fastEncrypt(Data data,Alphabet alphabet) {
		long st = System.currentTimeMillis();
		/*Data data = new Data();
		data.setDecrypted(new byte[toEncrypt.length()]);
		data.setDecrypted(toEncrypt.getBytes());
		data.setKey(generateKey());*/
		//Alphabet alphabet = new Alphabet(data.getKey());
		//int higher = 0;
		String encrypted = "";
		//int rand;
		int typeOfByte;
		byte valueBefore = 0;
		byte original = 0;
		byte[] bytes = data.getDecrypted();
		for(int i=0;i<data.getDecrypted().length;i++) {
			int aux;
			//System.out.print(data.getDecrypted()[i]+"\n");
			if(data.getDecrypted()[i] >=0) {
				typeOfByte = 1;
			}else {
				typeOfByte = -1;
			}
			encrypted+=(typeOfByte==1)?1:0;
			
			original = bytes[i];
			aux=bytes[i]*typeOfByte;
			//while(true) {
				//Thread.sleep(1);
				for(int rand =alphabet.getValues().size()-1;true;rand--) {
					//System.out.println(rand);
					//rand = (int) ((Math.random() * (((alphabet.getValues().size()-1) - 0) + 1)) + 0);
					try {
						rand = (rand<0)?alphabet.getValues().size()-1:rand;
						//System.out.println("i:"+i+"/"+data.getDecrypted().length+" "+aux+" "+alphabet.getValues().get(rand)+" "+valueBefore+" "+((100/data.getDecrypted().length)*i)+"% "+typeOfByte);
						if(aux >= alphabet.getValues().get(rand) && alphabet.getValues().get(rand) != 0) {
							aux-=alphabet.getValues().get(rand);
							encrypted +=alphabet.getKeys().get(rand);
						}
						if(aux == 0) {
							encrypted+=".";
							typeOfByte = 0;
							//System.out.println("end");
							break;
						}
						if(aux < 0) {
							Thread.sleep(1000);
							//System.out.println(data.getDecrypted()[i]+" "+typeOfByte);
							aux *=typeOfByte;
						}
					}catch(Exception e) {
						System.out.println(rand);
						System.out.println(alphabet.getValues().size());
						e.printStackTrace();
					}
				}
			//}
			//valueBefore = original;
		}
		//System.out.println();
		//System.out.println("encrypted:"+encrypted);
		//System.out.println("size:"+encrypted.length());
		//t3(encrypted);
		data.setEncrypted(encrypted);
		System.out.println("time:"+((System.currentTimeMillis()-st)/1000));
		return data;
	}
	private List<String> enc;
	private int index=0;
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
		//System.out.println("index:"+index);
		return this.index;
	}
	public Data teste(Data data,Alphabet alphabet) throws Exception{
		byte[] b = data.getDecrypted();
		byte[] bytes = null;
		int count = 0;
		this.enc = new ArrayList<>();
		int size = b.length/100;
		//System.out.println(size);
		//System.exit(0);
		for(int i=0;i<size;i++) {
			this.enc.add(i,"");
		}
		for(int i=0;i<b.length;i++) {
			if(count == 0) {
				if(b.length-i > 98) {
					//System.out.println("b > 98");
					bytes = new byte[99];
				}else {
					//System.out.println("b < 98");
					//System.out.println(b.length);
					bytes = new byte[b.length];
				}
			}
			//System.out.println("count:"+count+" i:"+i);
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
							String txt = fastEncrypt(d, alphabet).getEncrypted();
							addToEnc(txt,index);
							System.err.println("acabou thread :"+index);
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
		new Scanner(System.in).nextLine();
		System.out.println(getEnc());
		for(String s:getEnc()) {
			encrypted+=s;
		}
		encrypted.trim();
		data.setEncrypted(encrypted);
		return data;
	}
	public Data fastestEncrypt(Data data,Alphabet alphabet)throws Exception {
		//byte[][] bytes = arraySmash(data.getDecrypted());
		byte[][] bytes;
		byte[] bts = data.getDecrypted();
		int sz = bts.length/10;
		if(sz*10 == bts.length) {
			
		}
		bytes = new byte[bts.length/10][9];
		/*
		 2956 = 0.1
		 29567 = 1
		 
		 */
		byte[] current = new byte[data.getDecrypted().length/10];
		System.out.println("size:"+data.getDecrypted().length/10+" tot:"+data.getDecrypted().length);
		System.exit(0);
		List<Data> list = new ArrayList<>();
		enc = new ArrayList<>();
		for(int i=0;i<10;i++) {
			enc.add(i, "");
		}
		//wrong
		for(int i=0;i<10;i++) {
			try {
				for(int ii=0;true;ii++) {
					current[ii]=bytes[ii][i];
				}
			}catch(Exception e) {
				
			}
			data.setDecrypted(current);
			list.add(data);
		}
		String encrypted = "";
		for(Data d:list) {
			Thread.sleep(100);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						int i = getIndex();
						addIndex();
						String txt = fastEncrypt(d, alphabet).getEncrypted();
						addToEnc(txt,i);
						System.err.println("acabou thread :"+i);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
				}
			}).start();
			//
			//break;
		}
		new Scanner(System.in).nextLine();
		for(String s:getEnc()) {
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
		//Alphabet alphabet = new Alphabet(data.getKey());
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
	/*public static void main(String[]args) {
		Data data = new Data();
		data.setDecrypted("test".getBytes());
		Alphabet alphabet = new Alphabet(new Encrypter().generateKey());
		data.setEncrypted(new Encrypter().teste(data, alphabet).getEncrypted());
		System.out.println(new String(new Encrypter().decrypt(data, alphabet).getDecrypted()));
	}*/
}
