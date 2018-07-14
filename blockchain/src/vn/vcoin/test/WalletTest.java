package vn.vcoin.test;

import java.security.Security;

import com.google.gson.GsonBuilder;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.BlockDTO;
import vn.vcoin.core.dto.WalletDTO;
import vn.vcoin.core.util.VcoinUtils;

public class WalletTest {

	public static void main(String[] args) {
		//set security provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		
		BlockChainDTO chain = new BlockChainDTO("V Coin");

		//only one block have previous hash is NULL
		BlockDTO genesisBlock = new BlockDTO(0, "Genesis Block", null);
		System.out.println("Hash for Genesis block  : " + genesisBlock.getHash());
		
		BlockDTO secondBlock = new BlockDTO(1, "Yo im the second block",genesisBlock.getHash());
		System.out.println("Hash for block 1 : " + secondBlock.getHash());
		
		BlockDTO thirdBlock = new BlockDTO(2, "Hey im the third block",genesisBlock.getHash());
		System.out.println("Hash for block 2 : " + thirdBlock.getHash());
//		
		//mine new block
		BlockDTO mineBlock = thirdBlock.mineBlock();

		// add by using loop
		for (int i = 0; i < 5; i++) {
			// get the last hash of list
			String previousHash = chain.lastBlock().getHash();
			String data = "Block " + (chain.length() + 1);
			BlockDTO e = new BlockDTO(chain.length() + 1, data, previousHash);

			// add to blockchain(list)
			chain.addBlock(e);
		}

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
		System.out.println(blockchainJson);
		System.out.println("blockchain size: " + chain.length());

		// 4.test valid change
		chain.isChainValid();
		/// ---------------------------------------
		WalletDTO wRoot = new WalletDTO(chain);
		wRoot.setBalance(1000f);// have 1k coin

		// create 2 wallet
		WalletDTO wA = new WalletDTO(chain);
		WalletDTO wB = new WalletDTO(chain);
		System.out.println("Private key:>>>" + VcoinUtils.getStringFromKey(wA.getPrivateKey()));
		System.out.println("Public key:>>>" + VcoinUtils.getStringFromKey(wA.getPublicKey()));

		// =================================================test
		wRoot.getBalance();

		wRoot.sendFunds(wB, 450f);
		
	}
}
