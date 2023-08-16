package Blockchain;

import java.io.Serializable;
import java.sql.Timestamp;


public class Block implements Serializable{
	
	public Header header;
	public TransactionCollection tranxLst;
	
	
	public Block(String previousHash) {
		this.header = new Header();
		header.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime() ) ;
		header.setPrevHash(previousHash) ;
		String info = String.join("+",
				Integer.toString( header.getIndex()),
				Long.toString(header.getTimestamp()),
				header.getPrevHash()
				);
		String blockHash = Hasher.sha256( info ) ;
		header.setCurrHash(blockHash) ;
	}
	
	public TransactionCollection getTransactions() {
		
		return this.tranxLst;
		
	}

	public void setTransactions(TransactionCollection tranxLst) {
		this.tranxLst = tranxLst;
	}
	
	public Header getHeader() {
		
		return this.header;
	}
	
	@Override
	public String toString() {
		return "Block [header=" + header + ", tranxLst=" + tranxLst + "]";
	}

	
	
	public class Header implements Serializable{
		
		public int index;
		public String currentHash;
		public String previousHash;
		public long timestamp;
		
		

		@Override
		public String toString() {
			return "Header [index=" + index + ", currentHash=" + currentHash + ", previousHash=" + previousHash
					+ ", timestamp=" + timestamp + "]";
		}

		public int getIndex() {
			return index;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public long getTimestamp() {
			return timestamp;
		}
		
		
		public void setCurrHash(String currentHash) {
			this.currentHash = currentHash;
		}
		public String getCurrHash() {
			return currentHash;
		}
		
		public void setPrevHash(String previousHash) {
			this.previousHash = previousHash;
		}
		public String getPrevHash() {
			return previousHash;
		}
	}
}


