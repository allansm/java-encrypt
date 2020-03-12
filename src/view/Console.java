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
	private String inputFileUrl;
	private String outputFileUrl;
	private String customKey;
	private int key;
	
	public void setInputFileUrl(Scanner scanner) {
		System.out.print("type the input file url:");
		this.inputFileUrl = scanner.next();
	}
	public void setOutputFileUrl(Scanner scanner) {
		System.out.print("type the output file url:");
		this.outputFileUrl = scanner.next();
	}
	public void setKey(Scanner scanner) {
		System.out.print("type key number:");
		this.key = scanner.nextInt();
	}
	public void setCustomKey() throws Exception {
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		this.customKey = new String(new Encrypter().decrypt(data, new Alphabet(key)).getDecrypted());
	}
	public void encryptFile()throws Exception {
		Data data = new Data();
		data.setDecrypted(Files.readAllBytes(Paths.get(inputFileUrl)));
		DataDao dataDao = new DataDao();
		dataDao.write(data, outputFileUrl,Encrypter.TYPE.FASTRANDOM);
		System.out.println("\nok");
	}
	public void decryptFile() throws Exception{
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, new Alphabet(key));
		Files.write(Paths.get(outputFileUrl), data.getDecrypted());
	}
	public void encryptUsingCustomKey() throws Exception{
		Data data = new Data();
		data.setDecrypted(Files.readAllBytes(Paths.get(inputFileUrl)));
		DataDao dataDao = new DataDao();
		dataDao.write(data, outputFileUrl,Encrypter.TYPE.FASTRANDOM,new Alphabet(customKey));
		System.out.println("\nok");
	}
	public void decryptUsingCustomKey() throws Exception{
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, new Alphabet(customKey));
		Files.write(Paths.get(outputFileUrl), data.getDecrypted());
	}
	public void showVars() {
		System.out.println("inputFileUrl:"+inputFileUrl);
		System.out.println("outputFileUrl:"+outputFileUrl);
		System.out.println("key:"+key);
		System.out.println("customKey:"+customKey);
	}
	public void run() throws Exception{
		Scanner scanner;
		while(true) {
			System.out.print("10110101>");
			scanner = new Scanner(System.in);
			String op  = scanner.next();
			if(op.equals("setInputFileUrl")) {
				setInputFileUrl(scanner);
			}else if(op.equals("setOutputFileUrl")) {
				setOutputFileUrl(scanner);
			}else if(op.equals("setKey")) {
				setKey(scanner);
			}else if(op.equals("setCustomKey")) {
				setCustomKey();
			}else if(op.equals("encryptFile"))  {
				encryptFile();
			}else if(op.equals("decryptFile"))  {
				decryptFile();
			}else if(op.equals("encryptUsingCustomKey"))  {
				encryptUsingCustomKey();
			}else if(op.equals("decryptUsingCustomKey")) {
				decryptUsingCustomKey();
			}else if(op.equals("showVars")) {
				showVars();
			}
		}
	}
	public static void main(String[] args) {
		try {
			new Console().run();
			//new Console().test();
			//new Console().test2();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
