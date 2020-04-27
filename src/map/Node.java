package map;

import java.io.Serializable;

public class Node<Key, Value> implements Serializable {

	private static final long serialVersionUID = -1755742757105886725L;
	private Key key;
	private Value value;
	protected Node<Key, Value> left_child;
	protected Node<Key, Value> right_child;

	public Node(Key key, Value value, Node<Key, Value> left_child, Node<Key, Value> right_child) {
		this.key = key;
		this.value = value;
		this.left_child = left_child;
		this.right_child = right_child;
	}

	public Node(Key key, Value value) {
		this(key, value, null, null);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
