package Blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionCollection implements Serializable {

	private final int SIZE = 10;
	public String merkleRoot;

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public List<String> getTranxLst() {
		return tranxLst;
	}

	public void setTranxLst(List<String> tranxLst) {
		this.tranxLst = tranxLst;
	}

	public List<String> tranxLst;
	
	public TransactionCollection() {
		tranxLst = new ArrayList<>(SIZE);
	}
	
	public void add(String tranx) {
		tranxLst.add(tranx);
	}
	
	public String getMerkleRoot() {
		return this.merkleRoot;
	}
	
	public List<String> getTransactionList() {
		return tranxLst;
	}

	@Override
	public String toString() {
		return "TransactionCollection [tranxLst=" + tranxLst + "]";
	}
	
}