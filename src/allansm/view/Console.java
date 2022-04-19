package allansm.view;

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

import allansm.entity.Alphabet;
import allansm.entity.Data;
import allansm.entity.Encrypter;
import allansm.persistence.DataDao;

public class Console {
	private String inputFileUrl;
	private String outputFileUrl;
	private String customKey;
	private byte[] decrypted;
	private int key;
	private Encrypter.TYPE type = Encrypter.TYPE.FASTRANDOM;
	
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
	public void setEncrypterType(Scanner scanner) throws Exception{
		System.out.println("1:"+Encrypter.TYPE.RANDOM.name()+" >> encrypt with random characters based on key");
		System.out.println("2:"+Encrypter.TYPE.LINEAR.name()+" >> encrypt with same characters based on key");
		System.out.println("3:"+Encrypter.TYPE.FASTRANDOM.name()+" >> encrypt with random characters based on key using threads");
		System.out.println("4:"+Encrypter.TYPE.FASTLINEAR.name()+" >> encrypt with same characters based on key using threads");
		System.out.print("type nº:");
		int type = scanner.nextInt();
		if(type == 1) {
			this.type = Encrypter.TYPE.RANDOM;
		}else if(type == 2) {
			this.type = Encrypter.TYPE.LINEAR;
		}else if(type == 3) {
			this.type = Encrypter.TYPE.FASTRANDOM;
		}else if(type == 4) {
			this.type = Encrypter.TYPE.FASTLINEAR;
		}
	}
	public void setCustomKey() throws Exception {
		System.out.print("custom key:");
		this.customKey = new Scanner(System.in).next();//new String(new Encrypter().decrypt(data, new Alphabet(key)).getDecrypted());
	}
	public void encryptFile()throws Exception {
		Data data = new Data();
		data.setDecrypted(Files.readAllBytes(Paths.get(inputFileUrl)));
		DataDao dataDao = new DataDao();
		dataDao.write(data, outputFileUrl,type);
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
		dataDao.write(data, outputFileUrl,type,new Alphabet(customKey));
		System.out.println("\nok");
	}
	public void decryptUsingCustomKey() throws Exception{
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		data = new Encrypter().decrypt(data, new Alphabet(customKey));
		Files.write(Paths.get(outputFileUrl), data.getDecrypted());
	}
	public void decryptWithoutSave(Scanner scanner)throws Exception {
		byte[] b = Files.readAllBytes(Paths.get(inputFileUrl));
		Data data = new Data();
		data.setEncrypted(new String(b));
		System.out.print("type of decryptation (1-normal key,2-custom key):");
		int format = scanner.nextInt();
		if(format == 1) {
			data = new Encrypter().decrypt(data, new Alphabet(key));
		}else if(format == 2) {
			data = new Encrypter().decrypt(data, new Alphabet(customKey));
		}
		decrypted = data.getDecrypted();
	}
	public void decryptDecrypt(Scanner scanner) {
		byte[] b = decrypted;
		Data data = new Data();
		data.setEncrypted(new String(b));
		System.out.print("type of decryptation (1-normal key,2-custom key):");
		int format = scanner.nextInt();
		if(format == 1) {
			data = new Encrypter().decrypt(data, new Alphabet(key));
		}else if(format == 2) {
			data = new Encrypter().decrypt(data, new Alphabet(customKey));
		}
		decrypted = data.getDecrypted();
	}
	public void showDecrypted() {
		System.out.println(new String(decrypted));
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
		System.out.println("encrypterType:"+type.name());
	}
	public void list(Scanner scanner) {
		int i =0;
		System.out.print("type the directory:");
		String path = scanner.next();
		try{
			File f = new File(path);
			while(true) {
				if(!f.isDirectory()) {
					System.out.println("type only directory here.");
					break;
				}
				path = (f.getPath().endsWith("\\"))?f.getPath()+f.list()[i++]:f.getPath()+"\\"+f.list()[i++];
				System.out.println(path);
			}
		}catch(Exception e) {}
	}
	public void help() {
		List<String> help = new ArrayList<>();
		help.add("comands:");
		help.add("setInputFileUrl >> set variable used to read");
		help.add("setOutputFileUrl >> set variable used to write");
		help.add("setKey >> set encrypter key used to decrypt a file");
		help.add("setCustomKey >> read a file that contains a custom key decrypt and store as variable, require input output files and key");
		help.add("setEncrypterType >> select the encryptation mode");
		help.add("encryptFile >> read a file and encrypt a new one");
		help.add("decryptFile >> read a file and decrypt a new one");
		help.add("encryptUsingCustomKey >> read a file and encrypt using custom key stored");
		help.add("decryptUsingCustomKey >> read a file and decrypt using custom key stored");
		help.add("decryptWithoutSave >>  read a file and decrypt and store as variable");
		help.add("decryptDecrypt >> decrypt value stored on variable decrypt");
		help.add("showDecrypted >> show value inside decrypt");
		help.add("useDecryptedOnLocalhost >> use data stored on variable decrypt on http://localhost");
		help.add("showVars >> show variables and values");
		help.add("list >> list a directory");
		help.add("bye >> exit");
		for(String s:help) {
			System.out.println(s);
		}
	}
	public void run() throws Exception{
		Scanner scanner;
		try {
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
			}else if(op.equals("setEncrypterType")) {
				setEncrypterType(scanner);
				showVars();
			}else if(op.equals("encryptFile"))  {
				encryptFile();
			}else if(op.equals("decryptFile"))  {
				decryptFile();
			}else if(op.equals("encryptUsingCustomKey"))  {
				encryptUsingCustomKey();
			}else if(op.equals("decryptUsingCustomKey")) {
				decryptUsingCustomKey();
			}else if(op.equals("decryptDecrypt")) {
				decryptDecrypt(scanner);
			}else if(op.equals("showDecrypted")) {
				showDecrypted();
			}else if(op.equals("decryptWithoutSave")) {
				decryptWithoutSave(scanner);
				showVars();
			}else if(op.equals("useDecryptedOnLocalhost")) {
				useDecryptedOnLocalhost();
			}else if(op.equals("showVars")) {
				showVars();
			}else if(op.equals("list")) {
				list(scanner);
			}else if(op.equals("help")) {
				help();
			}else if(op.equals("bye")) {
				System.exit(0);
			}else {
				System.out.println("type help to get commands.");
			}
		}catch(Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		run();
	}
	public static void main(String[] args) {
		try {
			new Console().run();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
