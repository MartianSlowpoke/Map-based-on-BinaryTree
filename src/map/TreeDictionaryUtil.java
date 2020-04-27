package map;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TreeDictionaryUtil<Key extends Comparable<Key>, Value> implements Serializable {

	private static final long serialVersionUID = -894019379823290025L;
	private TreeDictionary<Key, Value> tree;

	public TreeDictionaryUtil(TreeDictionary<Key, Value> tree) {
		this.tree = tree;
	}

	public void in_order(Node<Key, Value> node, List<Pair<Key, Value>> result) {
		if (node != null) {
			in_order(node.left_child, result);
			Pair<Key, Value> pair = new Pair<>(node.getKey(), node.getValue());
			result.add(pair);
			in_order(node.right_child, result);
		}
	}

	public Node<Key, Value> getNode(Key key, Node<Key, Value> root) {
		if (root != null) {
			Node<Key, Value> current = root;
			while (true) {
				if (key.compareTo(current.getKey()) == 0) {
					return current;
				}
				current = getNextNode(current, key);
				if (current == null) {
					return null;
				}

			}
		}
		return null;
	}

	public Node<Key, Value> getParentFor(Node<Key, Value> root, Node<Key, Value> node) {
		if (node == root) {
			return root;
		}
		Node<Key, Value> current = root;
		Node<Key, Value> itsParent = root;
		boolean isFound = false;
		while (!isFound) {
			if (node == current) {
				isFound = true;
			}
			if (!isFound) {
				itsParent = current;
				current = getNextNode(current, node.getKey());
			}
		}
		return itsParent;
	}

	/*
	 * возвращает левый или правый узел текущего потомка взавимимости от ключа
	 */
	public Node<Key, Value> getNextNode(Node<Key, Value> current, Key key) {
		if (key.compareTo(current.getKey()) < 0)
			return current.left_child;
		return current.right_child;
	}

	public CHILD whichChild(Node<Key, Value> itsParent, Node<Key, Value> node) {
		if (itsParent.left_child == node)
			return CHILD.LEFT;
		return CHILD.RIGHT;
	}

	public Node<Key, Value> getSuccessorFor(Node<Key, Value> node) {
		return getMinimumNodeForSubTree(node);
	}

	public Node<Key, Value> getMinimumNodeForSubTree(Node<Key, Value> local_root) {
		Node<Key, Value> current = local_root;
		while (true) {
			if (current.left_child == null)
				break;
			current = current.left_child;
		}
		return current;
	}

	private DELETION_CASE getCase(Node<Key, Value> delNode) {
		if (delNode.left_child == null && delNode.right_child == null)
			return DELETION_CASE.NO_CHILDREN;
		if ((delNode.left_child == null && delNode.right_child != null)
				|| (delNode.left_child != null && delNode.right_child == null))
			return DELETION_CASE.ONE_CHILD;
		return DELETION_CASE.TWO_CHILDREN;
	}

	/*
	 * it is the hardest part.
	 * 
	 * let's do this shit!!!
	 */
	public Node<Key, Value> delete(Node<Key, Value> delNode) {
		DELETION_CASE del_case = getCase(delNode);
		Node<Key, Value> del_parent = getParentFor(tree.root, delNode);
		CHILD del_pos = whichChild(del_parent, delNode);
		switch (del_case) {
		case NO_CHILDREN:
			if (delNode == tree.root)
				tree.clear();
			else {
				switch (del_pos) {
				case LEFT:
					del_parent.left_child = null;
					break;
				case RIGHT:
					del_parent.right_child = null;
					break;
				}
			}
			break;
		case ONE_CHILD:
			if (delNode == tree.root) {
				CHILD child = hasChild(delNode);
				switch (child) {
				case LEFT:
					tree.root = delNode.left_child;
					break;
				case RIGHT:
					tree.root = delNode.right_child;
					break;
				}
			} else {
				CHILD child = hasChild(delNode);
				Node<Key, Value> delNodeChild = null;
				switch (child) {
				case LEFT:
					delNodeChild = delNode.left_child;
					break;
				case RIGHT:
					delNodeChild = delNode.right_child;
					break;
				}
				switch (del_pos) {
				case LEFT:
					del_parent.left_child = delNodeChild;
					break;
				case RIGHT:
					del_parent.right_child = delNodeChild;
					break;
				}
			}
			break;
		case TWO_CHILDREN:
			if (delNode == tree.root) {
				Node<Key, Value> successor = getSuccessorFor(delNode);
				if (successor != delNode.right_child) {
					Node<Key, Value> successor_parent = getParentFor(tree.root, successor);
					successor_parent.left_child = successor.right_child;
					successor.left_child = delNode.left_child;
					successor.right_child = delNode.right_child;
					tree.root = successor;
				} else {
					successor.left_child = delNode.left_child;
					tree.root = successor;
				}
			} else {
				Node<Key, Value> successor = getSuccessorFor(delNode);
				if (successor == delNode.right_child) {
					switch (del_pos) {
					case LEFT:
						successor.left_child = delNode.left_child;
						del_parent.right_child = successor;
						break;
					case RIGHT:
						successor.left_child = delNode.left_child;
						del_parent.left_child = successor;
						break;
					}
				} else {
					Node<Key, Value> successor_parent = getParentFor(tree.root, successor);
					successor_parent.left_child = successor.right_child;
					successor.left_child = delNode.left_child;
					successor.right_child = delNode.right_child;
					switch (del_pos) {
					case LEFT:
						del_parent.left_child = successor;
						break;
					case RIGHT:
						del_parent.right_child = successor;
						break;
					}
				}
			}
			break;
		}
		return null;
	}

	public List<Node<Key, Value>> getNodes(Node<Key, Value> root) {
		List<Pair<Key, Value>> pairs = new LinkedList<>();
		in_order(root, pairs);
		List<Node<Key, Value>> nodes = new LinkedList<>();
		for (Pair<Key, Value> pair : pairs) {
			Node<Key, Value> node = getNode(pair.getKey(), root);
			nodes.add(node);
		}
		return nodes;
	}

	private CHILD hasChild(Node<Key, Value> node) {
		if (node.left_child != null)
			return CHILD.LEFT;
		if (node.right_child != null)
			return CHILD.RIGHT;
		return null;
	}

}
