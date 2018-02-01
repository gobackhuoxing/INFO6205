package info6205.memory;

//class to hold a block with the size as power of 2  (Assignment1 Q1) 
public class Blocks {
	//index of the block
	int index;
	//position in the pool
	int order;
	
	//Constructor
	public  Blocks(int index){
		this.index=index;
	}

	public int getIndex() {
		return index;
	}

	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

	public int getSize(){;
		int size = (int) Math.pow(2, index);
		return size;
	}
}
