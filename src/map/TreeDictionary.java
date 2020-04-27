package map;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections.CollectionUtils;

/*
 * реалізація словника на базі двійкового дерева пошуку
 */
public class TreeDictionary<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value>, Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * утилітний клас , який містить ряд необхідних функцій
	 */
	private TreeDictionaryUtil<Key, Value> utils;
	/*
	 * root - корінь дерева
	 */
	protected Node<Key, Value> root;

	/*
	 * пустий конструктор
	 */
	public TreeDictionary() {
		utils = new TreeDictionaryUtil<>(this);
	}

	/*
	 * конструктор копіювання в який передається інший словник
	 */
	public TreeDictionary(Dictionary<Key, Value> map) {
		this();
		List<Pair<Key, Value>> pairs = map.getAll();
		for (Iterator<Pair<Key, Value>> iterator = pairs.iterator(); iterator.hasNext();) {
			Pair<Key, Value> pair = iterator.next();
			this.include(pair.getKey(), pair.getValue());
		}
	}

	/*
	 * РЕАЛІЗАЦІЯ ФУНКЦІЙ ІНТЕРФЕЙСУ MAP
	 */

	/*
	 * повертає кількість елементів словника спочатку береться список пар , а потім
	 * їх розмір
	 */
	@Override
	public int size() {
		return this.getAll().size();
	}

	/*
	 * каже , чи словник пустий чи ні
	 */
	@Override
	public boolean isEmpty() {
		if (this.getAll().size() == 0)
			return true;
		return false;
	}

	/*
	 * додає ключ та значення в даний словник
	 */
	@Override
	public void include(Key key, Value value) {
		Node<Key, Value> old = utils.getNode(key, root);
		if (old != null) {
			exclude(key);
		}
		Node<Key, Value> node = new Node<>(key, value);
		if (root == null) {
			root = node;
		} else {
			Node<Key, Value> current = root;
			while (true) {
				if (key.compareTo(current.getKey()) < 0) {
					if (current.left_child == null) {
						current.left_child = node;
						return;
					}
					current = current.left_child;
				} else {
					if (current.right_child == null) {
						current.right_child = node;
						return;
					}
					current = current.right_child;
				}
			}
		}
	}

	/*
	 * видаляє значення за цим ключем та повертає його
	 */
	@Override
	public Value exclude(Key key) {
		Node<Key, Value> delNode = utils.getNode(key, root);
		if (delNode != null) {
			utils.delete(delNode);
			return delNode.getValue();
		}
		return null;
	}

	/*
	 * знайти значення, яке відповідає наданому ключу
	 */
	@Override
	public Value find(Key key) {
		Node<Key, Value> find = utils.getNode(key, root);
		if (find != null)
			return find.getValue();
		return null;
	}

	/*
	 * змінювання елементів згідно з наданою процедурою
	 */
	@Override
	public void forEach(Consumer<Node<Key, Value>> consumer) {
		List<Node<Key, Value>> nodes = utils.getNodes(root);
		for (Node<Key, Value> node : nodes) {
			consumer.accept(node);
		}
	}

	/*
	 * Повертає список пар ключ-значення даного словника
	 */
	@Override
	public List<Pair<Key, Value>> getAll() {
		List<Pair<Key, Value>> result = new LinkedList<>();
		if (root != null)
			utils.in_order(root, result);
		return result;/*
						* 
						*/
	}

	/*
	 * каже , чи два словника рівні чи ні
	 */
	@Override
	public boolean isEquals(Dictionary<Key, Value> map) {
		return CollectionUtils.isEqualCollection(map.getAll(), this.getAll());
	}

	/*
	 * конкатенує два словника
	 */
	@Override
	public void addAll(Dictionary<Key, Value> map) {
		List<Pair<Key, Value>> pairs = map.getAll();
		for (Iterator<Pair<Key, Value>> iterator = pairs.iterator(); iterator.hasNext();) {
			Pair<Key, Value> pair = iterator.next();
			this.include(pair.getKey(), pair.getValue());
		}
	}

	/*
	 * оператор доступу по індексу, символ "[] знаходить елемент за ключем
	 */
	@Override
	public Value get(Key key) {
		Node<Key, Value> node = utils.getNode(key, root);
		if (node != null)
			return node.getValue();
		return null;
	}

	/*
	 * очищає словник
	 */
	@Override
	public void clear() {
		root = null;
	}

	/*
	 * swap - обмін значеннями з іншим словником
	 */
	@Override
	public void swap(Dictionary<Key, Value> map) {
		Dictionary<Key, Value> copied = new TreeDictionary<>(this);
		this.clear();
		this.addAll(map);
		map.clear();
		map.addAll(copied);
	}

}
