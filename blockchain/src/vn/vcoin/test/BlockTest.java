package vn.vcoin.test;

import com.google.gson.GsonBuilder;

import vn.vcoin.core.dto.BlockChainDTO;
import vn.vcoin.core.dto.BlockDTO;

public class BlockTest {

	public static void main(String[] args) {
		//only one block have previous hash is NULL
		BlockDTO genesisBlock = new BlockDTO(0, "Genesis Block", null);
		System.out.println("Hash for Genesis block  : " + genesisBlock.getHash());
		
		BlockDTO secondBlock = new BlockDTO(1, "Yo im the second block",genesisBlock.getHash());
		System.out.println("Hash for block 1 : " + secondBlock.getHash());
		
		BlockDTO thirdBlock = new BlockDTO(2, "Hey im the third block",secondBlock.getHash());
		System.out.println("Hash for block 2 : " + thirdBlock.getHash());
//		
		//mine new block
		BlockDTO mineBlock = thirdBlock.mineBlock();
		
	}
}
