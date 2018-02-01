package BTMemory;

public class Block {

	private int size;
	private int index;
	private Block parent;
	private Block left;
	private Block right;
	private boolean avaiable;
	private boolean busy = false;
	private int position = 0;

	public Block() {

	}

	public Block(int index) {
		this.index = index;

	}

	public Block(int size, int index) {
		this.size = size;
		this.index = index;
	}

	public Block(int size, int index, Block parent, Block left, Block right, boolean avaiable) {
		super();
		this.size = size;
		this.index = index;
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.avaiable = avaiable;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Block getParent() {
		return parent;
	}

	public void setParent(Block parent) {
		this.parent = parent;
	}

	public Block getLeft() {
		return left;
	}

	public void setLeft(Block left) {
		this.left = left;
	}

	public Block getRight() {
		return right;
	}

	public void setRight(Block right) {
		this.right = right;
	}
	
	

	public boolean isAvaiable() {
		return avaiable;
	}

	public void setAvaiable(boolean avaiable) {
		this.avaiable = avaiable;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	
	

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Block [index=" + index + " left=" + left + ", right =" + right + "]";
	}

	

}
