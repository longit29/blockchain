package vn.vcoin.test;

import com.google.gson.GsonBuilder;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.BlockDTO;

public class BlockChainTest {

	public static void main(String[] args) {
		BlockChainDTO chain = new BlockChainDTO("V Coin");

		// only one block have previous hash is NULL
		BlockDTO genesisBlock = new BlockDTO(0, "Genesis Block", "null");
		System.out.println("Hash for Genesis block  : " + genesisBlock.getHash());
		chain.addBlock(genesisBlock);

		BlockDTO secondBlock = new BlockDTO(1, "Yo im the second block", genesisBlock.getHash());
		System.out.println("Hash for block 1 : " + secondBlock.getHash());
		chain.addBlock(secondBlock);

		BlockDTO thirdBlock = new BlockDTO(2, "Hey im the third block", secondBlock.getHash());
		System.out.println("Hash for block 2 : " + thirdBlock.getHash());
		chain.addBlock(thirdBlock);
		//
		// mine new block
		chain.mineBlock();

		// add by using loop
		for (int i = 0; i < 5; i++) {
			// get the last hash of list
			String previousHash = chain.lastBlock().getHash();
			String data = "Block " + (chain.length());
			BlockDTO e = new BlockDTO(chain.length(), data, previousHash);

			// add to blockchain(list)
			chain.addBlock(e);
		}

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
		System.out.println(blockchainJson);
		System.out.println("blockchain size: " + chain.length());

		// 4.test valid change
		chain.isChainValid();
	}
}
