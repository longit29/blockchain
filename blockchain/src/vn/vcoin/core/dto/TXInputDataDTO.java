package vn.vcoin.core.dto;

public class TXInputDataDTO {
	private String transactionOutputId; // Reference to TransactionOutputs ->
										// transactionId
	private TXOutputDataDTO UTXO; // Contains the Unspent transaction output

	public TXInputDataDTO(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public String getTransactionOutputId() {
		return transactionOutputId;
	}

	public TXOutputDataDTO getUTXO() {
		return UTXO;
	}

	public void setTransactionOutputId(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public void setUTXO(TXOutputDataDTO uTXO) {
		UTXO = uTXO;
	}

}
