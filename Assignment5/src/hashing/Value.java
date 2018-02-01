package hashing;

public class Value {
	int key;
	String value;
	
	public Value() {
	}

	public Value(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public Value(String value) {
		super();
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
