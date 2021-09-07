package final_project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.MetalSliderUI;

public class BinaryFrame extends JFrame {
	final int DEFAULT_THRESH = 128;
	JPanel cotrolPanel;
	HistogramPanel histogramPanel;
	ImagePanel imagePanel;
	ImagePanel imagePanelBin;
	JButton btnShow;
	String filename = "file/test.png";
	int thresh = DEFAULT_THRESH;;
	JSlider sliderBin;
	JLabel lbFilename;
	JTextField tfFilename;
	JTextField tfBinValue;
	int[][][] data;
	int[][] gray;
	int[] histogram;
	int height;
	int width;
	BufferedImage img;
	boolean textfieldFlag = false;

	BinaryFrame() {
		lbFilename = new JLabel("File name (png):");
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Binary");
		btnShow = new JButton("Show");
		btnShow.setBounds(10, 8, 80, 25);
		lbFilename.setBounds(110, 8, 100, 25);
		tfFilename = new JTextField();
		tfFilename.setText(filename);
		tfFilename.setBounds(205, 8, 200, 25);
		tfBinValue = new JTextField();
		tfBinValue.setText(thresh + "");
		tfBinValue.setBounds(280, 200, 40, 25);
		int xOffset1 = 7;
		sliderBin = new JSlider(0, 255, DEFAULT_THRESH);
		sliderBin.setBounds(0, 200, 270, 25);
		histogramPanel = new HistogramPanel();
		histogramPanel.setBounds(0 + xOffset1, 40, 256, 150);
		histogramPanel.setBackground(new Color(255, 255, 0));

		cotrolPanel = new JPanel();
		cotrolPanel.setBounds(0, 0, 1500, 500);
		cotrolPanel.setLayout(null);
		cotrolPanel.add(btnShow);
		cotrolPanel.add(lbFilename);
		cotrolPanel.add(tfFilename);
		cotrolPanel.add(sliderBin);
		cotrolPanel.add(histogramPanel);
		cotrolPanel.add(tfBinValue);

		imagePanel = new ImagePanel();
		imagePanel.setBounds(10, 240, 700, 700);
		imagePanelBin = new ImagePanel();
		imagePanelBin.setBounds(650, 240, 700, 700);

		add(imagePanel);
		add(cotrolPanel);
		add(imagePanelBin);

		btnShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					img = ImageIO.read(new File(tfFilename.getText()));
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

					imagePanel.paintComponent(imagePanel.getGraphics(), img);
					gray = Util.gray(data);
					sliderBin.setValue(DEFAULT_THRESH);
					histogramPanel.paintComponent(histogramPanel.getGraphics(), gray, DEFAULT_THRESH);
					BufferedImage newImg = binary();
					imagePanelBin.paintComponent(imagePanelBin.getGraphics(), newImg);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file path doesn't exist!");
					e.printStackTrace();
				}
			}

		});

		sliderBin.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(sliderBin.getMousePosition().x);
				sliderBin.setValue(value);
			}
		});

		sliderBin.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = sliderBin.getValue();
				if (!textfieldFlag && value != thresh) {
					tfBinValue.setText(value + "");
					thresh = value;
					updateImg();
				}
			}

		});

		tfBinValue.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) { // 限制輸入數字
				int keyChar = e.getKeyChar();
				if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
					e.consume();
				}
			}

		});

		tfBinValue.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				textfieldFlag = true;
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				textfieldFlag = false;
			}

		});
		tfBinValue.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (textfieldFlag) {
					String value = tfBinValue.getText();
					if (isNumber(value)) {
						sliderBin.setValue(Integer.parseInt(value));
						thresh = Integer.parseInt(value);
					} else if (value.equals("")) {
						sliderBin.setValue(0);
						thresh = 0;
					}
					updateImg();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (textfieldFlag) {
					String value = tfBinValue.getText();
					if (isNumber(value)) {
						sliderBin.setValue(Integer.parseInt(value));
						thresh = Integer.parseInt(value);
					} else if (value.equals("")) {
						sliderBin.setValue(0);
						thresh = 0;
					}
					updateImg();
				}
			}

		});
	}

	BufferedImage binary() {
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color;
				if (gray[i][j] >= thresh) {
					color = Util.getColor(255, 255, 255);
				} else {
					color = Util.getColor(0, 0, 0);
				}
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	void updateImg() { // 更新影像
		histogramPanel.paintComponent(histogramPanel.getGraphics(), gray, thresh);
		BufferedImage newImg = binary();
		imagePanelBin.paintComponent(imagePanelBin.getGraphics(), newImg);
	}

	boolean isNumber(String str) { // 檢查是否為數字
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
