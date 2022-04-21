package allansm.security;

import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.Base64;

public class Encrypt{
	private byte[] data;
	private String dict;
	private String delimiter;

	public Encrypt(byte[] data, String dict, String delimiter){
		this.data = data;
		this.dict = dict;
		this.delimiter = delimiter;
	}
	
	private String random(int x){
		String tmp = "";

		while(x != 0){
			int rand = (int) ((Math.random() * (((this.dict.length()-1) - 0) + 1)) + 0);
			
			if(rand <= x){
				x-=rand;
				tmp+=this.dict.charAt(rand);
			}
		}

		return tmp;
	}

	private int translate(String data){
		int x = 0;
		int current = 0;
		
		while(current < data.length()){
			for(int i=0;i<dict.length();i++){
				if(data.charAt(current) == dict.charAt(i)){
					x+=i;
					break;
				}
			}

			current+=1;
		}

		return x;
	}

	public byte[] random(){
		String tmp = "";

		for(int i=0;i<data.length;i++){
			if(i > 0) tmp+=delimiter;
			tmp+=random(data[i]);
		}
		
		Deflater deflater = new Deflater();
		deflater.setInput(tmp.getBytes());
		deflater.finish();

		byte[] b = new byte[Short.MAX_VALUE];
		int size = deflater.deflate(b);

		byte[] compressed = new byte[size];
		System.arraycopy(b, 0, compressed, 0, size);

		deflater.end();
		
		return Base64.getEncoder().encodeToString(compressed).getBytes();
	}

	public byte[] decrypt(){
		try{
			byte[] compressed = Base64.getDecoder().decode(new String(this.data));
			
			Inflater inflater = new Inflater();
			inflater.setInput(compressed, 0, compressed.length);

			byte[] b = new byte[Short.MAX_VALUE];
			int size = inflater.inflate(b);

			inflater.end();

			byte[] data = new byte[size];
			System.arraycopy(b, 0, data, 0, size);

			byte[] tmp = new byte[new String(data).split(delimiter).length];
			
			int i=0;
			for(String n : Arrays.asList(new String(data).split(delimiter))){
				tmp[i++]=(byte)translate(n);
			}

			return tmp;
		}catch(Exception e){
			e.printStackTrace();

			return new byte[0];
		}
	}
}
