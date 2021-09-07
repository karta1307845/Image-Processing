package final_project;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class DIPSystem {

	public static void main(String[] args) {
		JFrame frame = new Menu();
		frame.setTitle("Digital Image Processing System");
		frame.setSize(400, 400);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
