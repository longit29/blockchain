package vn.vcoin.core.dto;

import java.util.ArrayList;

import vn.vcoin.core.util.VcoinConstant;

public class BlockChainDTO {
	private String name;
	private float noCoin = 1000000f;

	private ArrayList<BlockDTO> listBlocks = new ArrayList<BlockDTO>();

	// private BlockchainManager chainController;

	public BlockChainDTO(String name) {
		this.name = name;
	}

	// add new block to blockchain(list)
	public void addBlock(BlockDTO block) {
		this.listBlocks.add(block);
	}

	// length of blockchain
	public int length() {
		return this.listBlocks.size();
	}

	// get last elmenent blockchain
	public BlockDTO lastBlock() {
		return this.listBlocks.get(this.listBlocks.size() - 1);
	}

	public boolean isChainValid() {
		BlockDTO currentBlock;
		BlockDTO previousBlock;

		// loop through blockchain to check hashes:
		for (int i = 1; i < listBlocks.size(); i++) {
			currentBlock = listBlocks.get(i);
			previousBlock = listBlocks.get(i - 1);
			// compare index
			if (currentBlock.getIndex() != (previousBlock.getIndex() + 1)) {
				System.out.println("Current index not equals " + currentBlock.getIndex());
				return false;
			}
			// compare registered hash and calculated hash:
			if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equals");
				return false;
			}
			// compare previous hash and registered previous hash
			else if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
				System.out.println("Previous Hashes not equals INDEX>>> " + currentBlock.getIndex());
				return false;
			}
		}
		System.out.println("Blockchain is valid!");
		return true;
	}

	public String getName() {
		return name;
	}

	public float getNoCoin() {
		return noCoin;
	}

	public ArrayList<BlockDTO> getListBlocks() {
		return listBlocks;
	}

	@Override
	public String toString() {
		return "(" + this.name + ", " + this.noCoin + ")";
	}

	public void mineBlock() {
		BlockDTO currentBlock = lastBlock();
		// Create a string with difficulty * "0"
		String target = new String(new char[VcoinConstant.MINE_DIFFICULTY]).replace('\0', '0');
		// test
		target = "47b";
		String newHash = currentBlock.getHash();
		BlockDTO newBlock = null;
		System.out.println("Try mining a new block.........");
		System.out.println("Old hash>>>" + currentBlock.getHash());
		int countMine = 0;
		while (!newHash.startsWith(target)) {
			newBlock = new BlockDTO(currentBlock.getIndex() + 1, "Mine Block " + currentBlock.getIndex() + 1, currentBlock.getHash());
			// System.out.println(newHash.substring(0,
			// VcoinConstant.MINE_DIFFICULTY) + "===" +
			// target);
			countMine++;
			newHash = newBlock.getHash();
			// System.out.println("new hash>>>" + newHash);
		}
		System.out.println(countMine + "<<<Block Mined!!! : new hash>>>" + newHash);

		// point previous hash of new block to hash of the current block
		newBlock.setPreviousHash(currentBlock.getHash());
		newBlock.setCountMine(countMine);
		VcoinConstant.MINE_COUNT++;
		 
		//add new block was mined into chain
		this.addBlock(newBlock);
	}

}
