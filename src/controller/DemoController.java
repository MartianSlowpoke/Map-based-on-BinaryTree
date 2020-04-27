package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import graphics.AppView;
import map.Dictionary;
import map.Pair;
import map.TreeDictionary;

public class DemoController implements ActionListener {

	private AppView view;
	private Dictionary<String, String> model;
	private File storage;
	private JFileChooser chooser;

	public DemoController(AppView view, Dictionary<String, String> model) {
		this.view = view;
		this.model = model;
		storage = null;
		chooser = new JFileChooser();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			COMMAND command = COMMAND.valueOf(e.getActionCommand());
			switch (command) {
			case NEW:
				model = new TreeDictionary<>();
				view.displayPairs(model.getAll());
				storage = null;
				break;
			case OPEN:
				int option = chooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					Dictionary<String, String> model = (Dictionary<String, String>) open(chooser.getSelectedFile());
					this.model = model;
					view.displayPairs(model.getAll());
					storage = chooser.getSelectedFile();
				}
				break;
			case SAVE:
				if (storage != null) {
					save(model, storage);
					JOptionPane.showMessageDialog(null, "збережено до " + storage.getAbsolutePath());
				} else {
					if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						storage = chooser.getSelectedFile();
						save(model, storage);
						JOptionPane.showMessageDialog(null, "збережено до " + storage.getAbsolutePath());
					}
				}
				break;
			case SAVE_AS:
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					storage = chooser.getSelectedFile();
					save(model, storage);
					JOptionPane.showMessageDialog(null, "збережено до " + storage.getAbsolutePath());
				}
				break;
			case INCLUDE:
				Pair<String, String> pair = view.getInput();
				boolean isSuitable = isSuitable(pair);
				if (isSuitable) {
					model.include(pair.getKey(), pair.getValue());
					view.displayPairs(model.getAll());
				} else {
					JOptionPane.showMessageDialog(null, "вхідні параметри не вірні");
				}
				view.clearInput();
				break;
			case GET_ALL:
				if (model.isEmpty())
					JOptionPane.showMessageDialog(null, "пуста колекція(");
				else
					view.displayPairs(model.getAll());
				break;
			case FIND:
				if (model.isEmpty())
					JOptionPane.showMessageDialog(null, "пуста колекція(");
				else {
					String key = view.getInput().getKey();
					boolean isSuitableKey = isSuitableKey(key);
					if (isSuitableKey) {
						JOptionPane.showMessageDialog(null, "ключ - " + key + "\nзначення - " + model.get(key));
					} else {
						JOptionPane.showMessageDialog(null, "вхідний ключ невірний - " + key);
					}
				}
				view.clearInput();
				break;
			case EXCLUDE:
				if (model.isEmpty())
					JOptionPane.showMessageDialog(null, "пуста колекція(");
				else {
					String key = view.getInput().getKey();
					boolean isSuitableKey = isSuitableKey(key);
					if (isSuitableKey) {
						JOptionPane.showMessageDialog(null, "ключ - " + key + "\nзначення - " + model.exclude(key));
						view.displayPairs(model.getAll());
					} else {
						JOptionPane.showMessageDialog(null, "вхідний ключ невірний - " + key);
					}
				}
				view.clearInput();
				break;
			case ADD_ALL:
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File storage = chooser.getSelectedFile();
					this.model.addAll((Dictionary<String, String>) open(storage));
					view.displayPairs(this.model.getAll());
					JOptionPane.showMessageDialog(null, "додано");
				}
				break;
			case EQUALS:
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File storage = chooser.getSelectedFile();
					Dictionary<String, String> that = (Dictionary<String, String>) open(storage);
					boolean isEquals = model.isEquals(that);
					JOptionPane.showMessageDialog(null, "однакові - " + isEquals);
				}
				break;
			case SIZE:
				JOptionPane.showMessageDialog(null, "розмір - " + model.size());
				break;
			case EMPTY:
				JOptionPane.showMessageDialog(null, "пуста - " + model.isEmpty());
				break;
			case REVERSE:
				if (model.isEmpty())
					JOptionPane.showMessageDialog(null, "пуста колекція(");
				else {
					model.forEach(new ReverseConsumer());
					view.displayPairs(model.getAll());
					JOptionPane.showMessageDialog(null,
							"спрацювала функція for_each , яка змінила порядок літер в ключах та значеннях");
				}
				break;
			case SWAP:
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					Dictionary<String, String> map = (Dictionary<String, String>) open(file);
					model.swap(map);
					save(map, file);
					view.displayPairs(model.getAll());
					JOptionPane.showMessageDialog(null,
							"спрацювала функція swap зі словником з файлу " + file.getAbsolutePath());
				}
				break;
			default:
				break;
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}

	}

	private void save(Dictionary<String, String> model, File file) throws FileNotFoundException, IOException {
		try (ObjectOutputStream obj_stream = new ObjectOutputStream(new FileOutputStream(file))) {
			obj_stream.writeObject(model);
		}
	}

	private Object open(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		try (ObjectInputStream obj_stream = new ObjectInputStream(new FileInputStream(file))) {
			return obj_stream.readObject();
		}
	}

	private boolean isSuitableKey(String key) {
		if (key != null)
			if (!key.isBlank())
				return true;
		return false;
	}

	private boolean isSuitable(Pair<String, String> pair) {
		if (pair.getKey() != null) {
			if (!pair.getKey().isBlank()) {
				if (pair.getValue() != null) {
					if (!pair.getValue().isBlank()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
