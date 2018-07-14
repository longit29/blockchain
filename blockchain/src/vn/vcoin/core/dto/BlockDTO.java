package vn.vcoin.core.dto;

import java.util.Date;

import vn.vcoin.core.util.VcoinConstant;
import vn.vcoin.core.util.VcoinUtils;

public class BlockDTO {
	private String hash;
	private String previousHash;
	private String data; // our data will be a simple message.
	private long timeStamp; // as number of milliseconds since 1/1/1970.
	private int countMine = 0;
	private int index;

	// Block Constructor.
	public BlockDTO(int index, String data, String previousHash) {
		this.index = index;
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		// new hash
		this.hash = calculateHash();
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getData() {
		return data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setCountMine(int countMine) {
		this.countMine = countMine;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	// calculate new hash based on the previous hash
	public String calculateHash() {
		String calculatedhash = VcoinUtils
				.applySha256(this.index + this.previousHash + Long.toString(this.timeStamp) + this.data);
		return calculatedhash;
	}

	@Override
	public String toString() {
		return "(" + this.hash + ", " + this.data + ")";
	}

}
