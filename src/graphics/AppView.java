package graphics;

import java.awt.event.ActionListener;
import java.util.List;

import map.Pair;

public interface AppView {

	public void displayPairs(List<Pair<String, String>> pairs);

	public Pair<String, String> getInput();

	public void show();

	public void setButtonListener(ActionListener listener);

	public void clearInput();

}
