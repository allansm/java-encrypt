package persistence;

import java.nio.file.Files;
import java.nio.file.Paths;

import entity.Alphabet;
import entity.Data;
import entity.Encrypter;

public class DataDao {
	public void write(Data data,String filename,Encrypter.TYPE type) throws Exception{
		Encrypter encrypter = new Encrypter();
		data.setKey(encrypter.generateKey());
		Alphabet alphabet = new Alphabet(data.getKey());
		if(type.equals(Encrypter.TYPE.FASTRANDOM)) {
			data = encrypter.fastRandomEncrypt(data, alphabet);
		}else if(type.equals(Encrypter.TYPE.FASTLINEAR)) {
			data = encrypter.fastLinearEncrypt(data, alphabet);
		}else if(type.equals(Encrypter.TYPE.RANDOM)) {
			data = encrypter.randomEncrypt(data, alphabet);
		}else if(type.equals(Encrypter.TYPE.LINEAR)) {
			data = encrypter.linearEncrypt(data, alphabet);
		}
		Files.write(Paths.get(filename+"."+data.getKey()),data.getEncrypted().getBytes());
	}
	public Data read(String path,Alphabet alphabet) throws Exception{
		byte[] b = Files.readAllBytes(Paths.get(path));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, alphabet);
		return data;
	}
}
