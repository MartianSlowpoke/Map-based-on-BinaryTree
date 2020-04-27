package map;

import java.util.List;
import java.util.function.Consumer;

/*
 * Інтерфейс Dictionary який описує всі функції
 * 
 * Інтерфейс є параметризований двома параметрами - Key та Value
 */
public interface Dictionary<Key, Value> {

	/*
	 * size - кількість елементів
	 */
	public int size();

	/*
	 * empty - повертає значення true, якщо словник пустий
	 */
	public boolean isEmpty();

	/*
	 * include - додати елемент з наданим ключем у словник
	 */
	public void include(Key key, Value value);

	/*
	 * exclude - вилучити елемент з наданим ключем із словника
	 */
	public Value exclude(Key key);

	/*
	 * find - знайти значення, яке відповідає наданому ключ
	 */
	public Value find(Key key);

	/*
	 * for_each - змінювання елементів згідно з наданою процедурою
	 */
	public void forEach(Consumer<Node<Key, Value>> consumer);

	/*
	 * Повертає список пар ключ-значення даного словника
	 */
	public List<Pair<Key, Value>> getAll();

	/*
	 * порівняння, символі "=" , "!="
	 */
	public boolean isEquals(Dictionary<Key, Value> map);

	/*
	 * об’єднання словників, символ "+"
	 */
	public void addAll(Dictionary<Key, Value> map);

	/*
	 * оператор доступу по індексу, символ "[]".
	 * 
	 * Оскільки, JAVA не підтримує перезавантаження операторів , замість оператора
	 * використовується назва функції
	 * 
	 * Повертає значення за ключем
	 */
	public Value get(Key key);

	/*
	 * очищає словник
	 */
	public void clear();

	/*
	 * swap - обмін значеннями з іншим словником
	 */
	public void swap(Dictionary<Key, Value> map);

}
