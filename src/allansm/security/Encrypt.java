package allansm.security;

public class Encrypt{
	private String data;
	private String dict;
	private String delimiter;

	public Encrypt(String data, String dict, String delimiter){
		this.data = data;
		this.dict = dict;
		this.delimiter = delimiter;
	}
	
	private String random(int x){
		String tmp = "";

		while(x > 0){
			int rand = (int) ((Math.random() * (((this.dict.length()-1) - 0) + 1)) + 0);
			
			if(rand <= x){
				x-=rand;
				tmp+=this.dict.charAt(rand);
			}
		}

		return tmp;
	}
}
