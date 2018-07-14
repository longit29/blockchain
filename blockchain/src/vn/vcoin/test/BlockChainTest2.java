package vn.vcoin.test;

import com.google.gson.GsonBuilder;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.BlockDTO;
import vn.vcoin.core.dto.WalletDTO;
import vn.vcoin.core.util.VcoinUtils;

public class BlockChainTest2 {

	public static void main(String[] args) {
		BlockChainDTO chain = new BlockChainDTO("Any Coin");

		BlockDTO orginalBlock = new BlockDTO(0, "Genesis block", null);
		// add our blocks to the blockchain manually
		chain.addBlock(orginalBlock);
		chain.addBlock(new BlockDTO("Yo im the block 2", chain.lastBlock().getHash()));
		
		//2. test blockchain is valid???
		//chain.addBlock(new BlockDTO("Hey im the block 3", orginalBlock.getHash()));
		
		//3. test mine
		//BlockDTO mineBlock = chain.lastBlock().mineBlock();
		//chain.addBlock(mineBlock);

		// add by using loop
		for (int i = 0; i < 5; i++) {
			// get the last hash of list
			String previousHash = chain.lastBlock().getHash();
			String data = "Block " + (chain.length() + 1);
			BlockDTO e = new BlockDTO(data, previousHash);

			// add to blockchain(list)
			chain.addBlock(e);
		}

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
		System.out.println(blockchainJson);
		System.out.println("blockchain size: " + chain.length());
		
		//4.test valid change
		chain.isChainValid();
		
		WalletDTO wRoot = new WalletDTO(chain);
		wRoot.setBalance(1000f);// have 1k coin
		
		
		// create 2 wallet
		WalletDTO wA = new WalletDTO(chain);
		WalletDTO wB = new WalletDTO(chain);
		System.out.println("Private key:>>>" + VcoinUtils.getStringFromKey(wA.getPrivateKey()));
		System.out.println("Public key:>>>" + VcoinUtils.getStringFromKey(wA.getPublicKey()));


		// =================================================test
		wRoot.getBalance();

		//wRoot.sendFunds(wB.getAddress(), 450);
		 String json = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
		System.out.println(json);
	}
}
