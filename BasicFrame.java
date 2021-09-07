package final_project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicFrame extends JFrame{
	final protected JPanel cotrolPanelMain = new JPanel();
	final protected JPanel cotrolPanelShow = new JPanel();
	final protected ImagePanel imagePanel = new ImagePanel();
	final protected ImagePanel imagePanel2 = new ImagePanel();
	
	JLabel lb = new JLabel("file path:");
	JTextField tfFile = new JTextField(20);
	JButton btnShow = new JButton("Show Original");
	JButton btnFunction;

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	BasicFrame(){
		
	}
	
	BasicFrame(String function) {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle(function);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		tfFile.setText("file/shot.png");

		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		btnFunction = new JButton(function);
		cotrolPanelShow.add(btnFunction);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 500);
		getContentPane().add(cotrolPanelMain);

		imagePanel.setBounds(20, 130, 1500, 620);
		getContentPane().add(imagePanel);
		imagePanel2.setBounds(680, 130, 1500, 1500);
		getContentPane().add(imagePanel2);

		btnShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					img = ImageIO.read(new File(tfFile.getText()));
					height = img.getHeight();
					width = img.getWidth();
					data = new int[height][width][3];

					for (int y = 0; y < height; y++) {
						for (int x = 0; x < width; x++) {
							int rgb = img.getRGB(x, y);
							data[y][x][0] = Util.getR(rgb);
							data[y][x][1] = Util.getG(rgb);
							data[y][x][2] = Util.getB(rgb);
						}
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file path doesn't exist!");
					System.out.println("IO exception");
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), img);
			}

		});
	}
}
