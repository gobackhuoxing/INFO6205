package BTMemory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryPool{

	private int number;
	private Timer timer1;
	private Timer timer2;
	
	public Block headBlock;
	public boolean ifFind = false; // check find the targeted block, if find,								// ifFind will become true, then following
									// finding will doesn't work
	public boolean returnFind = false;
	public int requesttime=0;
	public int returntime=0;
	private int totalSize;
	public int failNum = 0;

	
	private static FileWriter fw;
	
	int requestcount=0;
	int returncount=0;

	

	public void initialPool(int index) throws IOException {
		headBlock = new Block((int) Math.pow(2, index), index, null, null, null, true);
		totalSize = (int) Math.pow(2, index);
		setNextBlock(headBlock);
		System.out.println("Memory is created");
		fw.write("Memory Pool is creating:\n");
		printAll();
	}

	// create block binary tree from head to bottom
	public void setNextBlock(Block block) {
		if (block.getIndex() > 0) {

			if (block.getLeft() == null) {

				Block leftBlock = new Block((int) Math.pow(2, block.getIndex() - 1), block.getIndex() - 1, block, null,
						null, true);
				block.setLeft(leftBlock);
				if (leftBlock.getIndex() > 0) {
					setNextBlock(block);
				}

			}

			if (block.getRight() == null) {

				Block rightBlock = new Block((int) Math.pow(2, block.getIndex() - 1), block.getIndex() - 1, block, null,
						null, true);
				block.setRight(rightBlock);
				if (rightBlock.getIndex() > 0) {
					setNextBlock(block);
				}
			}

			if ((block.getLeft() != null) && (block.getRight() != null)) {

				setNextBlock(block.getLeft());
				setNextBlock(block.getRight());
			}
		}

	}

	public boolean requestMemory(Block block, int i,int num) throws IOException {
		
		requestcount++;
		System.out.println("Request "  +requestcount+   "      Request Index= " + i);
		fw.write("Request "  +requestcount+   "      Request Index= " + i);
		fw.write("\n");
		number = 0;
		ifFind = false;
		boolean ifPickIp = true;
		// split request 3 to 2 and 2
		System.out.println(findNumber(headBlock, 3));
		if (findNumber(headBlock, i) >= 1) {
			ifPickIp = requestCertainMemory(block, i, num);
			printAll();
		} else {
			if (findNumber(headBlock, i - 1) >= 2) {

				// request 2 for two times

				requestCertainMemory(block, i - 1, num);

				ifFind = false;
				requestCertainMemory(block, i - 1, (0 - num));
				printAll();
			}
		}

		return ifPickIp;
	}
	
	public int findNumber(Block block, int index) {

		if (block.getIndex() > 0) {
			if (block.getIndex() == index && block.isAvaiable() == true && block.getLeft().isAvaiable() == true
					&& block.getRight().isAvaiable() == true) {
				number++;
			} else {
				findNumber(block.getLeft(), index);
				findNumber(block.getRight(), index);
			}
		}
		return number;
	}


	
	public boolean requestCertainMemory(Block block, int i,int num) throws IOException {
		if (totalSize >= (int) Math.pow(2, i)) {
			
			if (block.getIndex() == i) {
				
				if (block.isAvaiable() && (block.getSize() >= (int) Math.pow(2, i))) {
					
					if (ifFind == false) {
						block.setAvaiable(false);
						ifFind = true;
						setBusy1(block); // set following block as unavaiable
						checkAvaiable(block); // set the parent of
						// unavaiable
						// block as false
						block.setSize(0); // set block size 0
						requestParentSize(block, (int) Math.pow(2, i)); // set
																		// all
																		// parent
																		// size
						requestSonSize(block);
						totalSize = totalSize - (int) Math.pow(2, i);
						block.setPosition(num);
						block.setBusy(true);
						
						return true;
					}
					
				}
			} else {
				requesttime++;
				requestCertainMemory(block.getLeft(), i,num);
				requestCertainMemory(block.getRight(), i,num);

			}

			return true;

		} else { // no memory
			System.out.println("Not enough memory");
			fw.write("Request failed: Not enough memory\n\n");
			failNum++;
			return false;
		}

	}

	public void setBusy1(Block block) {
		if (block.getIndex() > 0) {
			block.setAvaiable(false);
			setBusy1(block.getLeft());
			setBusy1(block.getRight());
		}

		if (block.getIndex() == 0) {
			block.setAvaiable(false);
		}

	}

	public void checkAvaiable(Block block) {
		if (block.getIndex() < headBlock.getIndex()) {
			if (block.getParent().getLeft().isAvaiable() == false
					&& block.getParent().getRight().isAvaiable() == false) {
				block.getParent().setAvaiable(false);
				checkAvaiable(block.getParent());
			}
		}
	}

	public void requestParentSize(Block block, int size) {
		if (block.getIndex() < headBlock.getIndex()) {
			block.getParent().setSize(block.getParent().getSize() - size);
			block.getParent().setBusy(true);
			requestParentSize(block.getParent(), size);
		}

	}

	public void returnParentSize(Block block, int size) {
		if (block.getIndex() < headBlock.getIndex()) {
			block.getParent().setSize(block.getParent().getSize() + size);
			changeParentBusy(block);
			returnParentSize(block.getParent(), size);
		}

	}

	public void changeParentBusy(Block block){
		if(!(block.getParent()==null)){
			if(!block.getParent().getLeft().isBusy() && !block.getParent().getLeft().isBusy()){
				block.getParent().setBusy(false);
				changeParentBusy(block.getParent());
			}
		}
	}
	
	public void requestSonSize(Block block) {
		if (block.getIndex() > 0) {
			block.setSize(0);
			block.setBusy(true);
			requestSonSize(block.getLeft());
			requestSonSize(block.getRight());
		}
		
		if(block.getIndex() == 0){
			block.setBusy(true);
		}
	}

	public void returnSonSize(Block block) {
		if (block.getIndex() > 0) {
			block.setSize((int) Math.pow(2, block.getIndex()));
			block.setBusy(false);
			returnSonSize(block.getLeft());
			returnSonSize(block.getRight());
		}
		
		if(block.getIndex() == 0){
			block.setBusy(false);
		}
	}

	public void backMemory(Block block, int i, boolean ifExecute , int num) throws IOException {
		if (ifExecute) {
			returncount++;
			System.out.println("Return "  +returncount+   "      Return Index= " + i);
			fw.write("Return "  +returncount+   "      Return Index= " + i);
			fw.write("\n");
			returnFind = false;
			backCertainMemory(block, i,num);
			if(returnFind == false){
				backCertainMemory(block, i-1, num);
				returnFind = false;
				backCertainMemory(block, i-1, (0-num));
			}
			printAll();
		} else {
			System.out.println("Return failed time "+returncount+   "     Return index =" + i);
			fw.write("Return "  +returncount+   "      Return Index= " + i);
			fw.write("\n");
		}

	}

	public void backCertainMemory(Block block, int index, int num) {
		returntime++;
		if (block.getIndex() == index) {
			if (block.isAvaiable() == false) {
				if (returnFind == false && block.getPosition() == num) {

					block.setAvaiable(true);
					block.setSize((int) Math.pow(2, index));
					freeBusy(block);
					returnCheckAvaiable(block);
					returnSonSize(block);
					returnParentSize(block, (int) Math.pow(2, index));
					totalSize = totalSize + (int) Math.pow(2, index);
					returnFind = true;
					block.setPosition(0);
				}

			}

		} else {
			backCertainMemory(block.getRight(), index,num);
			backCertainMemory(block.getLeft(), index,num);
		}

	}

	public void freeBusy(Block block) {
		if (block.getIndex() > 0) {
			block.setAvaiable(true);
			freeBusy(block.getRight());
			freeBusy(block.getLeft());
		}
		if (block.getIndex() == 0) {
			block.setAvaiable(true);

		}
	}

	public void returnCheckAvaiable(Block block) {
		if (block.getIndex() < headBlock.getIndex()) {
			if ((block.getParent().getLeft().isAvaiable() && block.getParent().getRight().isAvaiable()) == false) {
				block.getParent().setAvaiable(true);
				returnCheckAvaiable(block.getParent());
			}
		}

	}

	// print a row of blocks for certain index
	public void printForIndex(Block block, int i) throws IOException {
		int height = headBlock.getIndex();
		if (block == null)
			return;
		if (i == height) {
			if (!block.isBusy()){
				System.out.print("O");
				fw.write("O");
			}
			if (block.isBusy()){
				System.out.print("X");
				fw.write("X");
			}

			int space = (int) Math.pow(2, block.getIndex() + 1) - 1;
			System.out.print(String.format("%" + space + "s", " "));
			fw.write(String.format("%" + space + "s", " "));
		} else if (i < height) {
			printForIndex(block.getLeft(), i + 1);
			printForIndex(block.getRight(), i + 1);
		}
	}

	// print all blocks
	public void printAll() throws IOException {
		int height = headBlock.getIndex();
		
		System.out.println("Used: X    Available: O ");
		System.out.println("All current blocks in memory pool:");
		
		
		fw.write("Used: X    Available: O \n");
		fw.write("All current blocks in memory pool:\n");
		
		for (int i = height; i >= 0; i--) {
			int space = (int) Math.pow(2, i);
			if (i < 10){
				System.out.print("Index 0" + (i) + ":");
				fw.write("Index 0" + (i) + ":");
			}
			if (i >= 10){
				System.out.print("Index " + (i) + ":");
				fw.write("Index " + (i) + ":");
			}
			System.out.print(String.format("%" + space + "s", " "));
			fw.write(String.format("%" + space + "s", " "));
			printForIndex(headBlock, i);
			System.out.println(" ");
			fw.write("\n");
		}
		System.out.println("Total Size: " + totalSize + "    Fail Count: " + failNum);
		System.out.println(" ");
		System.out.println("***********************************************************************************************");
		System.out.println(" ");
		fw.write("Total Size: " + totalSize + "    Fail Count: " + failNum);
		fw.write("\n");
		fw.write("\n");
		fw.write("***********************************************************************************************");
		fw.write("\n");
		fw.write("\n");
	}

	public void testForQ1(int index) throws IOException{
		fw = new FileWriter("Assignment3 Report.txt");
		initialPool(index);
		fw.close();
	}
	
	public void testForQ2(int index) throws IOException {
		fw = new FileWriter("Assignment3 Report.txt");
		fw.write("Memory Pool is creating:\n");

		initialPool(index);

		timer1 = new Timer();
		timer2 = new Timer();
		timer1.schedule(new timer1Task(), 0, 2000);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer2.schedule(new timer2Task(), 0, 2000);

		// fw.close();
	}

	class timer2Task extends TimerTask {
		int count = 1;

		boolean pick1;

		@Override
		public void run() {
			if (count < 5) {
				int i = (int) (Math.random() * 6);
				try {
					System.out.println("timer2");
					pick1 = requestMemory(headBlock, i,count);
					int seconds = (int) (Math.random() * 1000);
					Thread.sleep(seconds);
					backMemory(headBlock, i, pick1,count);
					count++;
				} catch (IOException e) {
				
					e.printStackTrace();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}

			} else {
				timer2.cancel();
				System.out.println("Project End");	
			}
		}
	}

	class timer1Task extends TimerTask {
		int count = 1;

		boolean pick1;

		@Override
		public void run() {
			if (count < 5) {
				int i = (int) (Math.random() * 6);
				try {
					System.out.println("timer1");
					pick1 = requestMemory(headBlock, i,count);
					int seconds = (int) (Math.random() * 10000);
					Thread.sleep(seconds);
					backMemory(headBlock, i, pick1,count);
					count++;
				} catch (IOException e) {
				
					e.printStackTrace();
				} catch (InterruptedException e) {
			
					e.printStackTrace();
				}

			} else {
				timer1.cancel();
				System.out.println("Project End");
				try {
					fw.close();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
			}
		}
	}
	
	public void testForQ3() throws IOException{
		fw = new FileWriter("Assignment3 Report.txt");
		FileWriter csv = new FileWriter("complexity.csv");
		for(int i=0; i<=10; i++){
			initialPool(i);
			requestCertainMemory(headBlock, 0,1);
			csv.write(i+","+(int) (Math.pow(2, i+1)-1)+","+requesttime+"\n");
			requesttime=0;
		}
		csv.close();
	}
	
	public void testForB1(int index) throws IOException{
		fw = new FileWriter("Assignment3_Summary_Q4.txt");
		fw.write("Memory Pool is creating:\n");
		initialPool(4);

		requestMemory(headBlock, 2,1);
		requestMemory(headBlock, 2,2);
		requestMemory(headBlock, 2,3);

		backMemory(headBlock, 2, true,1);

		requestMemory(headBlock, 3, 4);
		backMemory(headBlock, 3, true, 4);
		backMemory(headBlock, 2, true, 3);
		backMemory(headBlock, 2, true, 2);
		fw.close();
	}
	
	public static void main(String[] args) throws IOException {
		MemoryPool memoryPool = new MemoryPool();
		
		//Test answer to Q1
//		memoryPool.testForQ1(5);
		
		//Test answer to Q2
//		memoryPool.testForQ2(5);
	
		//Test answer to Q3
//		memoryPool.testForQ3();
		
		//Test answer to B1
		memoryPool.testForB1(5);

	}

	

}
