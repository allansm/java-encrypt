package allansm.security;

import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.Base64;

public class Encrypt{
	private byte[] data;
	private String dict;

	public Encrypt(byte[] data, String dict){
		this.data = data;
		this.dict = dict;
	}

	public Encrypt(String data, String dict){
		this.data = data.getBytes();
		this.dict = dict;
	}
	
	private String random(int x){
		String tmp = "";

		while(x != 0){
			int rand = (int) ((Math.random() * (((this.dict.length()-2) - 0) + 1)) + 0);
			
			if(rand <= x){
				x-=rand;
				tmp+=""+this.dict.charAt(rand);
			}
		}

		return tmp;
	}

	private String linear(int x){
		String tmp = "";

		while(x != 0){
			int i = x%(this.dict.length()-2);

			if(i == 0) i+=1;

			if(i <= x){
				tmp+=""+this.dict.charAt(i);
				x-=i;
			}
		}

		return tmp;
	}

	private int translate(String data){
		int x = 0;
		int current = 0;
		
		while(current < data.length()){
			for(int i=0;i<this.dict.length()-1;i++){
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

		for(int i=0;i<this.data.length;i++){
			if(i > 0) tmp+=this.dict.charAt(this.dict.length()-1);
			tmp+=random(this.data[i]);
		}
		
		Deflater deflater = new Deflater();
		deflater.setInput(tmp.getBytes());
		deflater.finish();

		byte[] b = new byte[Short.MAX_VALUE];
		int size = deflater.deflate(b);

		byte[] compressed = new byte[size];
		System.arraycopy(b, 0, compressed, 0, size);

		deflater.end();
		
		this.data = Base64.getEncoder().encodeToString(compressed).getBytes();	
		
		return this.data;
	}

	public byte[] linear(){
		String tmp = "";

		for(int i=0;i<this.data.length;i++){
			if(i > 0) tmp+=this.dict.charAt(this.dict.length()-1);
			tmp+=linear(this.data[i]);
		}
		
		Deflater deflater = new Deflater();
		deflater.setInput(tmp.getBytes());
		deflater.finish();

		byte[] b = new byte[Short.MAX_VALUE];
		int size = deflater.deflate(b);

		byte[] compressed = new byte[size];
		System.arraycopy(b, 0, compressed, 0, size);

		deflater.end();
		
		this.data = Base64.getEncoder().encodeToString(compressed).getBytes();
		
		return this.data;	
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

			byte[] tmp = new byte[new String(data).split(""+this.dict.charAt(this.dict.length()-1)).length];
			
			int i=0;
			for(String n : Arrays.asList(new String(data).split(""+this.dict.charAt(this.dict.length()-1)))){
				tmp[i++]=(byte)translate(n);
			}
			
			this.data = tmp;

			return this.data;
		}catch(Exception e){
			e.printStackTrace();

			return new byte[0];
		}
	}
}
