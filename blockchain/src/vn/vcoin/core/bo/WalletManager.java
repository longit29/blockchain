package vn.vcoin.core.bo;

import java.util.HashMap;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.WalletDTO;

public class WalletManager {

	private static WalletManager instance;
	private HashMap<String, WalletDTO> listWallets = new HashMap<String, WalletDTO>();
	private BlockChainDTO blockchain;

	private WalletManager() {

	}

	public void addWallet(WalletDTO newWallet) {
		this.listWallets.put(newWallet.getAddress(), newWallet);
	}

	public WalletDTO getWalletByAddress(String address) {
		return this.listWallets.get(address);
	}

	public HashMap<String, WalletDTO> getListWallets() {
		return listWallets;
	}

	// singleton
	public static WalletManager getInstance() {
		if (instance == null)
			instance = new WalletManager();
		return instance;

	}

}
