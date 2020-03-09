package entity;

public class Data {
	private byte[] decrypted;
	private String encrypted;
	private int key;
	public byte[] getDecrypted() {
		return decrypted;
	}
	public void setDecrypted(byte[] decrypted) {
		this.decrypted = decrypted;
	}
	public String getEncrypted() {
		return encrypted;
	}
	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	
}
