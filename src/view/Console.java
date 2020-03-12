package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
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
	private byte[] decrypted;
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
		decrypted = data.getDecrypted();
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
		decrypted = data.getDecrypted();
		Files.write(Paths.get(outputFileUrl), data.getDecrypted());
	}
	public void decryptWithoutSave(Scanner scanner)throws Exception {
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		System.out.println("type of decryptation (1-normal key,2-custom key):");
		int format = scanner.nextInt();
		if(format == 1) {
			data = new Encrypter().decrypt(data, new Alphabet(key));
		}else if(format == 2) {
			data = new Encrypter().decrypt(data, new Alphabet(customKey));
		}
		decrypted = data.getDecrypted();
	}
	public void useDecryptedOnLocalhost() throws Exception {
		ServerSocket server = new ServerSocket(80);
		OutputStream ou = server.accept().getOutputStream();
		int i = 0;
		for(byte b:decrypted) {
			System.out.print(b+",");
			if(i++ >9) {
				System.out.print("\n");
				i=0;
			}
		}
		
		ou.write(decrypted);
		Thread.sleep(1000);
		ou.close();
		server.close();
		System.out.println("foi");
	}
	public void showVars() {
		System.out.println("inputFileUrl:"+inputFileUrl);
		System.out.println("outputFileUrl:"+outputFileUrl);
		System.out.println("key:"+key);
		System.out.println("customKey:"+customKey);
		System.out.println("decrypted:"+decrypted);
	}
	public void help() {
		List<String> help = new ArrayList<>();
		help.add("comands:");
		help.add("setInputFileUrl >> set variable used to read");
		help.add("setOutputFileUrl >> set variable used to write");
		help.add("setKey >> set encrypter key used to decrypt a file");
		help.add("setCustomKey >> read a file that contains a custom key decrypt and store as variable, require input output files and key");
		help.add("encryptFile >> read a file and encrypt a new one");
		help.add("decryptFile >> read a file and decrypt a new one");
		help.add("encryptUsingCustomKey >> read a file and encrypt using custom key stored");
		help.add("decryptUsingCustomKey >> read a file and decrypt using custom key stored");
		help.add("showVars >> show variables and values");
		for(String s:help) {
			System.out.println(s);
		}
	}
	public void run() throws Exception{
		Scanner scanner;
		while(true) {
			System.out.print("10110101>");
			scanner = new Scanner(System.in);
			String op  = scanner.next();
			if(op.equals("setInputFileUrl")) {
				setInputFileUrl(scanner);
				showVars();
			}else if(op.equals("setOutputFileUrl")) {
				setOutputFileUrl(scanner);
				showVars();
			}else if(op.equals("setKey")) {
				setKey(scanner);
				showVars();
			}else if(op.equals("setCustomKey")) {
				setCustomKey();
				showVars();
			}else if(op.equals("encryptFile"))  {
				encryptFile();
			}else if(op.equals("decryptFile"))  {
				decryptFile();
			}else if(op.equals("encryptUsingCustomKey"))  {
				encryptUsingCustomKey();
			}else if(op.equals("decryptUsingCustomKey")) {
				decryptUsingCustomKey();
			}else if(op.equals("decryptWithoutSave")) {
				decryptWithoutSave(scanner);
				showVars();
			}else if(op.equals("showVars")) {
				showVars();
			}else if(op.equals("useDecryptedOnLocalhost")) {
				useDecryptedOnLocalhost();
			}else if(op.equals("help")) {
				help();
			}
		}
	}
	public static void main(String[] args) {
		try {
			new Console().run();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
