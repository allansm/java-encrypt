package entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Encrypter {
	public int generateKey() {
		return (int)(Math.random() * ((33 - 10) + 1)) + 10;
	}
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
			System.out.print(data.getDecrypted()[i]+"\n");
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
					System.out.println(rand);
					//rand = (int) ((Math.random() * (((alphabet.getValues().size()-1) - 0) + 1)) + 0);
					try {
						rand = (rand<0)?alphabet.getValues().size()-1:rand;
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
}
