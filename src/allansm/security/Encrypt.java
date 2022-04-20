package allansm.security;

import java.util.Arrays;

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

		return tmp.getBytes();
	}

	public byte[] decrypt(){
		byte[] tmp = new byte[new String(this.data).split(delimiter).length];
		
		int i=0;
		for(String data : Arrays.asList(new String(this.data).split(delimiter))){
			tmp[i++]=(byte)translate(data);	
		}

		return tmp;
	}
}
