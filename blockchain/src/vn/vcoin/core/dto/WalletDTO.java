package vn.vcoin.core.dto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import vn.vcoin.core.bo.TransactionManager;
import vn.vcoin.core.bo.WalletManager;
import vn.vcoin.core.util.VcoinConstant;

public class WalletDTO {
	private PrivateKey privateKey;
	private PublicKey publicKey;

	private WalletInforamtionDTO infor;
	private BlockChainDTO blockChain;
	private float balance = 0f;
	private String address = UUID.randomUUID().toString();

	// only UTXOs owned by this wallet.
	// private HashMap<String, TransactionOutput> UTXOs = new HashMap<String,
	// TransactionOutput>();
	private HashMap<String, TXOutputDataDTO> listTXOutputs = new HashMap<String, TXOutputDataDTO>();

	public WalletDTO(BlockChainDTO blockchain) {
		this.blockChain = blockchain;
		this.balance = sumBalance();
		//
		generateKeyPair();

		//
		WalletManager.getInstance().addWallet(this);
	}

	public HashMap<String, TXOutputDataDTO> getListTXOutputs() {
		return listTXOutputs;
	}

	public void setListTXOutputs(HashMap<String, TXOutputDataDTO> listTXOutputs) {
		for (Map.Entry<String, TXOutputDataDTO> item : listTXOutputs.entrySet()) {
			TXOutputDataDTO txO = item.getValue();
			// if output belongs to me ( if // coins belong to me )
			if (txO.isMine(this.publicKey)) {
				// add it to our list of unspent transactions.
				this.listTXOutputs.put(txO.getId(), txO);
			}
		}
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public String getAddress() {
		return address;
	}

	// create 2 key
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); // 256 bytes provides an
												// acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// returns balance and stores the UTXO's owned by this wallet in this.UTXOs
	public float getBalance() {
		System.out.println("Your balance: " + this.balance);
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float sumBalance() {
		float total = 0;
		for (Map.Entry<String, TXOutputDataDTO> item : this.listTXOutputs.entrySet()) {
			TXOutputDataDTO txO = item.getValue();
			total += txO.getValue();
		}
		// System.out.println("Your balance: " + total);
		return total;
	}

	// Generates and returns a new transaction from this wallet.
	public boolean sendFunds(WalletDTO toWallet, float amount) {
		// gather balance and check funds.
		if (getBalance() <= amount) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return false;
		}
		if (amount <= VcoinConstant.MINIUM_TRANSACTION) {
			System.out.println("#Amount must be greater than MINIUM value. Transaction Discarded.");
			return false;
		}
		// create array list of inputs
		ArrayList<TXInputDataDTO> inputs = new ArrayList<TXInputDataDTO>();

		TXOutputDataDTO txOut = new TXOutputDataDTO(toWallet.getPublicKey(), amount, null);
		TXInputDataDTO txIn = new TXInputDataDTO(txOut.getId());
		inputs.add(txIn);
		//
		this.balance -= amount;
		// generate new transaction
		TransactionDTO newTransaction = new TransactionDTO(this, toWallet, amount, inputs);
		newTransaction.generateSignature(this.privateKey);
		if (newTransaction.processTransaction()) {
			TransactionManager.getInstance().addNewTransaction(this, toWallet, newTransaction);
			System.out.println("Transaction was generated. Please waiting for confirmation.....");
			return true;
		}

		System.out.println("Transaction was failed...");
		return false;

	}

	@Override
	public String toString() {
		return "(" + this.address + ", " + this.balance + ", " + this.infor + ")";
	}

}
