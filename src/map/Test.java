package map;

public class Test {

	public static void main(String[] args) {
		Dictionary<String, String> dictionary = new TreeDictionary<>();
		dictionary.include("skoriy", "is the best");
		dictionary.include("skoriy", "fucking freak");

		Dictionary<String, String> dictionary2 = new TreeDictionary<>();
		dictionary2.include("skoriy", "is the best");
		dictionary2.include("skoriy", "fucking freak");
		System.out.println(dictionary.isEquals(dictionary2));
	}

}
