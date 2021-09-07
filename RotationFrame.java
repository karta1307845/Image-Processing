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

public class RotationFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelBackColor = new JPanel();;
	JPanel cotrolPanelRotate = new JPanel();
	ImagePanel imagePanel = new ImagePanel();
	JButton btnShow = new JButton("show");
	JButton btnRotate = new JButton("Rotate");
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfTheta = new JTextField(5);
	JTextField tfFile = new JTextField(20);
	JLabel lb = new JLabel("file path:");
	JLabel lbRed = new JLabel("Red (R)");
	JLabel lbGreen = new JLabel("Green (G)");
	JLabel lbBlue = new JLabel("Blue (B)");
	JLabel lbTheta = new JLabel("Angle");

	BufferedImage img;
	int[][][] data;
	int height;
	int width;

	RotationFrame() {
		setBounds(0, 0, 1500, 1500);
		setTitle("任意旋轉");
		getContentPane().setLayout(null);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
		tfTheta.setText("0");
		tfFile.setText("file/shot.png");

		cotrolPanelBackColor.setLayout(new GridLayout(3, 2));
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);

		cotrolPanelRotate.setLayout(new GridLayout(2, 2));
		cotrolPanelRotate.add(lbTheta);
		cotrolPanelRotate.add(tfTheta);
		cotrolPanelRotate.add(btnRotate);

		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelShow.add(cotrolPanelBackColor);
		cotrolPanelShow.add(cotrolPanelRotate);
		cotrolPanelShow.add(btnRotate);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 500);
		getContentPane().add(cotrolPanelMain);

		imagePanel.setBounds(20, 130, 2000, 620);
		getContentPane().add(imagePanel);

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

		btnRotate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (img != null) {
					int angle = Integer.parseInt(tfTheta.getText());
					int r = Integer.parseInt(tfRed.getText());
					int g = Integer.parseInt(tfGreen.getText());
					int b = Integer.parseInt(tfBlue.getText());
					int bgColor = Util.getColor(r, g, b);
					BufferedImage newImg = Util.rotate(data, angle, bgColor);
					imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
