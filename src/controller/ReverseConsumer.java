package controller;

import java.util.function.Consumer;

import map.Node;

public class ReverseConsumer implements Consumer<Node<String, String>> {

	@Override
	public void accept(Node<String, String> t) {
		t.setKey(reverse(t.getKey()));
		t.setValue(reverse(t.getValue()));
	}

	private String reverse(String input) {
		StringBuffer buffer = new StringBuffer(input);
		return new String(buffer.reverse());
	}

}
