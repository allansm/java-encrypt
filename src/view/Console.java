package view;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import entity.Alphabet;
import entity.Data;
import entity.Encrypter;
import persistence.DataDao;

public class Console {
	public void run() throws Exception{
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("type file name:");
			String fn = scanner.next();
			System.out.print("url of file to encrypt:");
			String url = scanner.next();
			Data data = new Data();
			data.setDecrypted(Files.readAllBytes(Paths.get(url)));
			DataDao dataDao = new DataDao();
			dataDao.insert(data, fn);
			System.out.println("\nok");
			break;
		}
	}
	public void test()throws Exception {
		byte[] b = Files.readAllBytes(Paths.get("aaba.16"));
		System.out.println(new String(b));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, new Alphabet(16));
		Files.write(Paths.get("testa"), data.getDecrypted());
	}
	public static void main(String[] args) {
		try {
			new Console().run();
			//new Console().test();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
