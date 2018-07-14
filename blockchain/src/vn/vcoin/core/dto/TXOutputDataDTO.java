package vn.vcoin.core.dto;

import java.security.PublicKey;

import vn.vcoin.core.util.VcoinUtils;

public class TXOutputDataDTO {
	private String id;
	private PublicKey reciepient; // also known as the new owner of these coins.
	private float value; // the amount of coins they own
	private String parentTransactionId; // the id of the transaction this output
										// was created in

	// Constructor
	public TXOutputDataDTO(PublicKey reciepient, float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		//
		String data = VcoinUtils.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId;
		this.id = VcoinUtils.applySha256(data);
	}

	public String getId() {
		return id;
	}

	public PublicKey getReciepient() {
		return reciepient;
	}

	public float getValue() {
		return value;
	}

	public String getParentTransactionId() {
		return parentTransactionId;
	}

	// Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}

}
