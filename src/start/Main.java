package start;

import java.awt.EventQueue;

import controller.DemoController;
import graphics.AppView;
import graphics.AppViewImpl;
import map.Dictionary;
import map.TreeDictionary;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				AppView view = new AppViewImpl();
				Dictionary<String, String> model = new TreeDictionary<>();
				DemoController controller = new DemoController(view, model);
				view.setButtonListener(controller);
				view.show();
			}

		});
	}

}
