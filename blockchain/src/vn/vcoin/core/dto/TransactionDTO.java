package vn.vcoin.core.dto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import vn.vcoin.core.util.TransactionType;
import vn.vcoin.core.util.VcoinConstant;
import vn.vcoin.core.util.VcoinUtils;

public class TransactionDTO {
	private String transactionId; // this is also the hash of the transaction.
	private TransactionType type;
	private PublicKey sender; // senders address/public key.
	private PublicKey reciepient; // Recipients address/public key.
	private float value;
	private byte[] signature; // this is to prevent anybody else from spending
								// funds in our wallet.

	public ArrayList<TXInputDataDTO> inputs = new ArrayList<TXInputDataDTO>();
	public ArrayList<TXOutputDataDTO> outputs = new ArrayList<TXOutputDataDTO>();

	private static int sequence = 0; // a rough count of how many transactions
										// have been generated.

	//

	public TransactionDTO(WalletDTO from, WalletDTO to, float value, ArrayList<TXInputDataDTO> inputs) {
		this.sender = from.getPublicKey();
		this.reciepient = to.getPublicKey();
		this.value = value;
		this.inputs = inputs;

		//
		this.transactionId = this.calulateHash();
	}

	public String getTransactionId() {
		return transactionId;
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions
					// having the same hash

		String data = VcoinUtils.getStringFromKey(sender) + VcoinUtils.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence;
		return VcoinUtils.applySha256(data);
	}

	// Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = VcoinUtils.getStringFromKey(sender) + VcoinUtils.getStringFromKey(reciepient)
				+ Float.toString(value);
		// System.out.println("signed data successfully!");
		signature = VcoinUtils.signECDSA(privateKey, data);
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = VcoinUtils.getStringFromKey(sender) + VcoinUtils.getStringFromKey(reciepient)
				+ Float.toString(value);
		System.out.println("verify signature successfully!");
		return VcoinUtils.verifyECDSASig(sender, data, signature);
	}

	// Returns true if new transaction could be created.
	public boolean processTransaction() {

		if (!verifiySignature()) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}

		// gather transaction inputs (Make sure they are unspent):
		for (TXInputDataDTO i : inputs) {
			TXOutputDataDTO uTXO = i.getUTXO();

		}

		// generate transaction outputs:
		float leftOver = getInputsValue() - value;
		// get value of inputs then the left over change:
		transactionId = calulateHash();
		// send value to recipient
		outputs.add(new TXOutputDataDTO(this.reciepient, value, transactionId));
		// send the left over 'change' back to sender
		outputs.add(new TXOutputDataDTO(this.sender, leftOver, transactionId));

		// add outputs to Unspent list
		for (TXOutputDataDTO o : outputs) {
		}

		// remove transaction inputs from UTXO lists as spent:
		for (TXInputDataDTO i : inputs) {
		}

		return true;
	}

	// returns sum of inputs(UTXOs) values
	public float getInputsValue() {
		float total = 0;
		for (TXInputDataDTO txi : inputs) {
			if (txi.getUTXO() == null)
				continue; // if Transaction can't be found skip it
			total += txi.getUTXO().getValue();
		}
		return total;
	}

	// returns sum of outputs:
	public float getOutputsValue() {
		float total = 0;
		for (TXOutputDataDTO txo : outputs) {
			total += txo.getValue();
		}
		return total;
	}

}
