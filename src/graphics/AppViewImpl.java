package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.COMMAND;
import map.Pair;

public class AppViewImpl implements AppView {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu_file;
	private JMenuItem new_menu_item;
	private JMenuItem menu_item_saveAs;
	private JMenuItem menu_item_save;
	private JMenuItem menu_item_open;
	private JTextField key_field;
	private JTextField value_field;
	private JPanel input_panel;
	private JButton include_btn;
	private JButton get_btn;
	private JButton erase_btn;
	private JButton get_all_btn;
	private JPanel button_panel;
	private JButton add_all_btn;
	private JButton equals_btn;
	private JButton size_btn;
	private JButton empty_btn;
	private JButton swap_btn;
	private JButton to_up_case_btn;
	private JPanel main_panel;

	public AppViewImpl() {
		initFrame();
		initMenu();
		initMainPanel();
		initInputPanel();
		initButtonPanel();
		initButtonActionCommands();
		main_panel.repaint();
		main_panel.revalidate();
		button_panel.repaint();
		button_panel.revalidate();
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 555, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private void initMenu() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menu_file = new JMenu("файл");
		menuBar.add(menu_file);

		new_menu_item = new JMenuItem("новий");
		menu_file.add(new_menu_item);

		menu_item_open = new JMenuItem("відкрити");
		menu_file.add(menu_item_open);

		menu_item_save = new JMenuItem("зберегти");
		menu_file.add(menu_item_save);

		menu_item_saveAs = new JMenuItem("зберегти як");
		menu_file.add(menu_item_saveAs);
	}

	private void initMainPanel() {
		main_panel = new JPanel();
		main_panel.setBorder(new LineBorder(Color.GRAY, 1, true));
		main_panel.setBounds(203, 12, 340, 558);
		frame.getContentPane().add(main_panel);
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
	}

	private void render(Pair<String, String> pair) {
		JPanel pair_panel = new JPanel();
		pair_panel.setPreferredSize(new Dimension(344, 70));
		pair_panel.setMinimumSize(new Dimension(344, 70));
		pair_panel.setMaximumSize(new Dimension(343, 70));
		pair_panel.setBackground(SystemColor.control);
		main_panel.add(pair_panel);
		pair_panel.setLayout(null);

		JLabel key_label = new JLabel("ключ:");
		key_label.setBounds(12, 12, 70, 15);
		pair_panel.add(key_label);

		JLabel value_label = new JLabel("значення:");
		value_label.setBounds(12, 43, 70, 15);
		pair_panel.add(value_label);

		JLabel lblKeyEkyKey = new JLabel(pair.getKey());
		lblKeyEkyKey.setBounds(91, 12, 235, 15);
		pair_panel.add(lblKeyEkyKey);

		JLabel lblValueValueValue = new JLabel(pair.getValue());
		lblValueValueValue.setBounds(91, 43, 235, 15);
		pair_panel.add(lblValueValueValue);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(10, 5));
		verticalStrut.setMinimumSize(new Dimension(10, 5));
		verticalStrut.setMaximumSize(new Dimension(10, 5));
		main_panel.add(verticalStrut);

		pair_panel.repaint();
		pair_panel.revalidate();
	}

	private void initInputPanel() {
		input_panel = new JPanel();
		input_panel.setBorder(new LineBorder(Color.GRAY, 1, true));
		input_panel.setBounds(12, 268, 179, 302);
		frame.getContentPane().add(input_panel);
		input_panel.setLayout(null);

		JLabel lblKey = new JLabel("ключ (стрічка)");
		lblKey.setBounds(12, 12, 135, 15);
		input_panel.add(lblKey);

		key_field = new JTextField();
		key_field.setBounds(12, 39, 155, 25);
		input_panel.add(key_field);
		key_field.setColumns(10);

		JLabel lblValue = new JLabel("значення (стрічка)");
		lblValue.setBounds(12, 76, 155, 15);
		input_panel.add(lblValue);

		value_field = new JTextField();
		value_field.setBounds(12, 103, 155, 25);
		input_panel.add(value_field);
		value_field.setColumns(10);

		include_btn = new JButton("include");
		include_btn.setBounds(30, 140, 117, 25);
		input_panel.add(include_btn);

		get_btn = new JButton("get");
		get_btn.setBounds(30, 177, 117, 25);
		input_panel.add(get_btn);

		get_all_btn = new JButton("get all");
		get_all_btn.setBounds(30, 214, 117, 25);
		input_panel.add(get_all_btn);

		erase_btn = new JButton("exclude");
		erase_btn.setBounds(30, 251, 117, 25);
		input_panel.add(erase_btn);
	}

	private void initButtonPanel() {
		button_panel = new JPanel();
		button_panel.setBorder(new LineBorder(Color.GRAY, 1, true));
		button_panel.setBounds(12, 12, 179, 244);
		frame.getContentPane().add(button_panel);
		button_panel.setLayout(null);

		add_all_btn = new JButton("add all (+)");
		add_all_btn.setBounds(32, 12, 117, 25);
		button_panel.add(add_all_btn);

		equals_btn = new JButton("equals (=)");
		equals_btn.setBounds(32, 49, 117, 25);
		button_panel.add(equals_btn);

		size_btn = new JButton("size");
		size_btn.setBounds(32, 86, 117, 25);
		button_panel.add(size_btn);

		empty_btn = new JButton("empty");
		empty_btn.setBounds(32, 123, 117, 25);
		button_panel.add(empty_btn);

		swap_btn = new JButton("swap");
		swap_btn.setBounds(32, 160, 117, 25);
		button_panel.add(swap_btn);

		to_up_case_btn = new JButton("reverse");
		to_up_case_btn.setBounds(32, 197, 117, 25);
		button_panel.add(to_up_case_btn);
	}

	private void initButtonActionCommands() {
		new_menu_item.setActionCommand(COMMAND.NEW.name());
		menu_item_saveAs.setActionCommand(COMMAND.SAVE_AS.name());
		menu_item_save.setActionCommand(COMMAND.SAVE.name());
		menu_item_open.setActionCommand(COMMAND.OPEN.name());
		include_btn.setActionCommand(COMMAND.INCLUDE.name());
		get_btn.setActionCommand(COMMAND.FIND.name());
		erase_btn.setActionCommand(COMMAND.EXCLUDE.name());
		get_all_btn.setActionCommand(COMMAND.GET_ALL.name());
		add_all_btn.setActionCommand(COMMAND.ADD_ALL.name());
		equals_btn.setActionCommand(COMMAND.EQUALS.name());
		size_btn.setActionCommand(COMMAND.SIZE.name());
		empty_btn.setActionCommand(COMMAND.EMPTY.name());
		swap_btn.setActionCommand(COMMAND.SWAP.name());
		to_up_case_btn.setActionCommand(COMMAND.REVERSE.name());
	}

	@Override
	public void displayPairs(List<Pair<String, String>> pairs) {
		main_panel.removeAll();
		for (Pair<String, String> pair : pairs) {
			render(pair);
		}
		main_panel.repaint();
		main_panel.revalidate();
	}

	@Override
	public Pair<String, String> getInput() {
		return new Pair<String, String>(key_field.getText(), value_field.getText());
	}

	@Override
	public void show() {
		frame.setVisible(true);
	}

	@Override
	public void setButtonListener(ActionListener listener) {
		new_menu_item.addActionListener(listener);
		menu_item_saveAs.addActionListener(listener);
		menu_item_save.addActionListener(listener);
		menu_item_open.addActionListener(listener);
		include_btn.addActionListener(listener);
		get_btn.addActionListener(listener);
		get_all_btn.addActionListener(listener);
		erase_btn.addActionListener(listener);
		add_all_btn.addActionListener(listener);
		equals_btn.addActionListener(listener);
		size_btn.addActionListener(listener);
		empty_btn.addActionListener(listener);
		swap_btn.addActionListener(listener);
		to_up_case_btn.addActionListener(listener);
	}

	@Override
	public void clearInput() {
		key_field.setText("");
		value_field.setText("");
	}

}
