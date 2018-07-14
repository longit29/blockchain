package vn.vcoin.core.bo;

import java.util.ArrayList;
import java.util.HashMap;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.TransactionDTO;
import vn.vcoin.core.dto.WalletDTO;

public class TransactionManager {

	private static TransactionManager instance;

	private HashMap<String, ArrayList<TransactionDTO>> listTXs = new HashMap<String, ArrayList<TransactionDTO>>();
	// private HashMap<String, ArrayList<TransactionDTO>> listWithdrawTX = new
	// HashMap<String, ArrayList<TransactionDTO>>();

	private TransactionManager() {

	}

	// singleton
	public static TransactionManager getInstance() {
		if (instance == null)
			instance = new TransactionManager();
		return instance;

	}

	public ArrayList<TransactionDTO> getListTXsByAddress(String address) {
		return this.listTXs.get(address);
	}

	public void addNewTransaction(WalletDTO from, WalletDTO to, TransactionDTO newTrans) {
		//this.listTXs.get(address).add(newTrans);
		
		//how to confirm a transaction
	}

}
