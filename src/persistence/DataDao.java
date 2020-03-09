package persistence;

import java.nio.file.Files;
import java.nio.file.Paths;

import entity.Alphabet;
import entity.Data;
import entity.Encrypter;

public class DataDao {
	public void insert(Data data,String filename) throws Exception{
		Encrypter encrypter = new Encrypter();
		data.setKey(encrypter.generateKey());
		Alphabet alphabet = new Alphabet(data.getKey());
		data = encrypter.encrypt(data, alphabet);
		Files.write(Paths.get(filename+"."+data.getKey()),data.getEncrypted().getBytes());
	}
}
