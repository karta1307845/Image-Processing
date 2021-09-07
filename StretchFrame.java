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


public class StretchFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;

	ImagePanel imagePanel;
	ImagePanel imagePanel2;

	JButton btnShow = new JButton("Show Original");
	JButton btnStretch1 = new JButton("Stretch 1 (min-max)");
	JButton btnStretch2 = new JButton("Stretch 2 (histogram)");
	JLabel lb = new JLabel("file path:");
	JTextField tfFile = new JTextField(20);

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	int[] histogram = new int[256];

	StretchFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("影像強化");
		tfFile.setText("file/Munich_gray_dark.png");

		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelShow.add(btnStretch1);
		cotrolPanelShow.add(btnStretch2);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 720, 620);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(650, 220, 1500, 1500);
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
							histogram[data[y][x][0]]++;
						}
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file path doesn't exist!");
					System.out.println("IO exception");
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), img);
			}

		});

		btnStretch1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					int max = data[0][0][0];
					int min = data[0][0][0];

					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							if (data[i][j][0] > max) {
								max = data[i][j][0];
							}
							if (data[i][j][0] < min) {
								min = data[i][j][0];
							}
						}
					}
					
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int value = 255 * (data[i][j][0] - min) / (max - min);
							int color = Util.getColor(value, value, value);
							newImg.setRGB(j, i, color);
						}
					}
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});

		btnStretch2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					// 計算原始離散分佈圖的機率密度函數值
					double[] pdf = new double[256];
					for (int i = 0; i < 256; i++) {
						pdf[i] = (double) histogram[i] / (height * width);
					}
					// 計算轉換後的機率密度函數值
					double[] cdf = new double[256];
					double sum = 0;
					for (int i = 0; i < 256; i++) {
						sum += pdf[i];
						cdf[i] = sum;
					}
					// 計算原始非0的機率密度函數值對應的位置
					int[] mapping = new int[256];
					for (int i = 0; i < 256; i++) {
						mapping[i] = (int) Math.round(cdf[i] * 255);
					}

					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int value = mapping[data[i][j][0]];
							int color = Util.getColor(value, value, value);
							newImg.setRGB(j, i, color);
						}
					}
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
