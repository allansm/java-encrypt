package view;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
		byte[] b = Files.readAllBytes(Paths.get("as.33"));
		System.out.println(new String(b));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, new Alphabet(33));
		Files.write(Paths.get("testa"), data.getDecrypted());
	}
	public void test2() {
		List<String> lista = new ArrayList<>();
		for(int i=0;i<3;i++) {
			lista.add(i, "");
		}
		lista.add(2, "2");
		lista.add(1, "1");
		lista.add(0, "0");
		for(String s:lista) {
			System.out.println(s);
		}
	}
	public static void main(String[] args) {
		try {
			//new Console().run();
			new Console().test();
			//new Console().test2();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
