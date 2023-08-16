package Blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import com.google.gson.GsonBuilder;

public class Blockchain {
	private static LinkedList<Block> db = new LinkedList<>();
	private static Blockchain _instance = null;
	public String chainFile;
	
	public Blockchain(String chainFile) {
		this.chainFile = chainFile;
	}
	
	public static Blockchain getInstance(String chainFile) {
		if(_instance == null) {
			_instance = new Blockchain(chainFile);
		}
		return _instance;
	}
	
	public void genesis() {
		Block genesis = new Block("0");
		db.add(genesis);
		persist();
	}
	
	public void nextBlock(Block newBlock) {
		db = get();
		db.add(newBlock);
		persist();
	}
	
	public LinkedList<Block> get(){
		try( FileInputStream fin = new FileInputStream(this.chainFile);
				ObjectInputStream in = new ObjectInputStream(fin);
			){
			return (LinkedList<Block>)in.readObject();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	private void persist() {
		try(FileOutputStream fout = new FileOutputStream(this.chainFile);
				ObjectOutputStream out = new ObjectOutputStream(fout);){
			out.writeObject(db);
			System.out.println("> Master file is updated!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void distribute() {
		String chain = new GsonBuilder().setPrettyPrinting().create().toJson(db);
		System.out.println(chain);
	}
	
	
	
}
