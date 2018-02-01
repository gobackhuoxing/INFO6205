package info6205.memory;

import java.util.Random;

public class Management {
	private int usedmemory;
	private int failed = 0;
	private int defragment = 0;
	private int test = 0;
	
	
	/**
	 * Assignment1 Q1 
	 * Assignment1 Q2
	 */
	// create initial blocks with size as power of 2 and sort them form lowest size to highest (Assignment1 Q1) & (Assignment1 Q2)
	public void getInitialBlock(){
		for (int i=0;i<=10;i++){
			Blocks newblock = new Blocks(i);
			Pool.poollist.add (newblock);
		}
		System.out.println("The initial block in pool");
		showAllBlock();
	}
	
	//method to get order for all blocks in the pool
	private void getOrders(){
		for(Blocks block: Pool.poollist){
			int order = Pool.poollist.indexOf(block);
			block.setOrder(order);
		}
	}
	
	//method to show the detail of the current memory pool
	private void showAllBlock(){
		System.out.println("All current blocks in memory pool:");
		getOrders();
		int poolsize = 0;
		for (int i = 0 ; i< Pool.poollist.size(); i++){
			int endpoint = poolsize+ Pool.poollist.get(i).getSize();
			System.out.println("Block " + Pool.poollist.get(i).getOrder() + " Index:" + Pool.poollist.get(i).getIndex() 
					+ " Size:" +Pool.poollist.get(i).getSize() +" Start Point:"+poolsize+" End Point:" +endpoint);
			poolsize += Pool.poollist.get(i).getSize();
		}
		usedmemory =2047-poolsize;
		System.out.println("The memory in pool is: "+ poolsize);
		System.out.println("The used memory is: " + usedmemory);
		System.out.println("-----------------------------------------------------------");
	}
	
	/**
	 * Assignment2 Q1
	 */
	//method to get memory with exact size 
	private boolean requestExactMemory(int index){
		int i = 0;
		boolean tobig = true;
		while(i<Pool.poollist.size()){
			
			//if there is suitable block in pool, take it and remove it from the pool (Assignment1 Q3)&(Assignment2 Q1)
			if(Pool.poollist.get(i).getIndex()==index){
				tobig = false;
				Usedmemory.usedlist.add(Pool.poollist.get(i));
				Pool.poollist.remove(i);
				break;
			}
			
			//if no suitable block, split the next bigger block into 2 sub-blocks and restart the previous step (Assignment2 Q1)
			if(Pool.poollist.get(i).getIndex()> index){
				tobig = false;
				int newindex = Pool.poollist.remove(i).getIndex()-1;
				Pool.poollist.add(i,new Blocks(newindex));
				Pool.poollist.add(i+1,new Blocks(newindex));
				i=0;
				continue;
			}
			i++;
		}
		return tobig;
	}
	
	/**
	 * Assignment1 Q3
	 * Assignment2 Q2
	 * Assignment2 Q3
	 */
	//method to request memory of different size randomly (Assignment1 Q3)
	private void requestMemory( ){
		test++;
		System.out.println("Test " + test);
		//get a size randomly (Assignment1 Q3)
		int index = (int)(Math.random()*11);
		System.out.println("Request memory: " + ((int)(Math.pow(2, index))));
		
		//if no large blocks to split, do defragment (Assignment2 Q2)
		if(requestExactMemory(index)){
			System.out.println("No large block in the pool, will defragment......");
			deFragment();
			System.out.println("Request memory: " + ((int)(Math.pow(2, index))) + " again");
			
			// after defragment,request the same size of memory again
			if(requestExactMemory(index)){
				
				//still no suitable block, that means no enough memory in pool, request failed, 
				//get the total number of failed requests (Assignment2 Q3)
				failed++;
//				System.err.println("Not enough memory!!");
//				System.err.println("Request failed. Total failed " + count + " times");
				System.out.println("Not enough memory!!");
				System.out.println("Request failed. Totally failed " + failed + " times");
			}
		}
		showAllBlock();
	}
		
	//method to return the used memory
	private void returnMemory(){
		test++;
		System.out.println("Test " + test);
		System.out.println("Returning memory");
		// only return if there is used memory
		if(Usedmemory.usedlist.size() !=0 ){
			Random ran = new Random();
			Blocks block =Usedmemory.usedlist.get( ran.nextInt( Usedmemory.usedlist.size() ) );
			Usedmemory.usedlist.remove(block);
			int index=block.getIndex();
			System.out.println("Return memory: " + ((int)(Math.pow(2, index))));
			boolean biggest = true;
			
			for (int i = 0 ; i< Pool.poollist.size(); i++){
				if(Pool.poollist.get(i).getIndex()>index){
					Pool.poollist.add(i,block);
					biggest = false;
					break;
				}
			}
			//if the new block is biggest, add it to the end of pool
			if (biggest){
				Pool.poollist.add(block);
			}
		}
		// do not return if no used memory
		else{
			System.out.println("No memory is used");
		}
		showAllBlock();
	}
	
	/**
	 * Assignment2 Q2
	 */
	//method to complete scan and defragment (Assignment2 Q2)
	private void deFragment(){
		int i =0;
		defragment++;
		while(i<Pool.poollist.size()-1){
			
			//if there are two same blocks
			if(Pool.poollist.get(i).getIndex() == Pool.poollist.get(i+1).getIndex()){
				//remove the two same blocks
				int index= Pool.poollist.remove(i).getIndex()+1;
				Pool.poollist.remove(i);
				
				//create a new block with double size and insert it into pool (Assignment2 Q2)
				boolean biggest = true;
				for (int j = 0 ; j< Pool.poollist.size(); j++){
					
					if(Pool.poollist.get(j).getIndex()>index){
						Pool.poollist.add(j,new Blocks(index));
						biggest = false;
						break;
					}
				}
				
				//if the new block is biggest, add it to the end of pool
				if (biggest){
					Pool.poollist.add(new Blocks(index));
				}
			}
			i++;
		}
		System.out.println("Defragment finished.");
		System.out.println("Totally Defragment "+ defragment+ " times");
		showAllBlock();
	}
	
	/**
	 * Assignment2 Q4
	 */
	// method to run the simulators n times (Assignment2 Q4)
	private void test(int n){
		getInitialBlock();
	
		int i=0;
		while(i<n){
			// request 10 times
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			requestMemory();
			//return 10 times
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			returnMemory();
			
			i++;
		}
		System.out.println("Test finished.");
		System.out.println("Totally Defragment "+ defragment+ " times");
		System.out.println("Totally failed request " + failed + " times");
	}
	
	public static void main(String[] args){
		Management man = new Management();
		man.test(50);
	}
}
