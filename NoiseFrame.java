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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

public class NoiseFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;

	ImagePanel imagePanel;
	ImagePanel imagePanel2;

	JButton btnShow = new JButton("Show Nonised Image");
	JButton btnMedianGray = new JButton("Remove Noise");
	JSlider slider;
	JLabel lb = new JLabel("file path:");
	JLabel lbLess = new JLabel("Less Noise");
	JLabel lbMore = new JLabel("More Noise");
	JTextField tfFile = new JTextField(20);

	int[][][] data;
	int[][] gray;
	int height;
	int width;
	BufferedImage img = null;
	int thresh = 100;

	NoiseFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("去除雜點");

		tfFile.setText("file/Munich_gray_noised.png");

		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelShow.add(lbLess);
		slider = new JSlider(0, 200, 100);
		thresh = slider.getValue();
		cotrolPanelShow.add(slider);
		cotrolPanelShow.add(lbMore);
		cotrolPanelShow.add(btnMedianGray);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 50, 620, 450);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(630, 50, 1230, 450);
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
					gray = Util.gray(data);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file path doesn't exist!");
					System.out.println("IO exception");
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), img);
			}

		});

		btnMedianGray.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int[] median = Util.getMedian(j, i, gray);
							if (Math.abs(gray[i][j] - median[0]) >= thresh) {
								int medianX = median[1];
								int medianY = median[2];
								int color = Util.getColor(data[medianY][medianX][0], data[medianY][medianX][1],
										data[medianY][medianX][2]);
								newImg.setRGB(j, i, color);
							} else {
								int color = Util.getColor(data[i][j][0], data[i][j][1], data[i][j][2]);
								newImg.setRGB(j, i, color);
							}
						}
					}
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});

		slider.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(slider.getMousePosition().x);
				slider.setValue(value);
			}
		});

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = slider.getValue();
				if (value != thresh) {
					thresh = value;
				}
			}

		});
	}
}// end of class
