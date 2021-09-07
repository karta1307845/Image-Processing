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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.MetalSliderUI;

public class HSLFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelHue = new JPanel();
	JPanel cotrolPanelSat = new JPanel();
	JPanel cotrolPanelInt = new JPanel();
	JPanel cotrolPanelBin = new JPanel();
	JPanel cotrolPanelHSL = new JPanel();

	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnHue = new JButton("Hue  Only");
	JButton btnSat = new JButton("Saturation  Only");
	JButton btnLight = new JButton("Lightness  Only");
	JButton btnHSL = new JButton("HSL  ALL");

	JSlider sliderHue;
	JSlider sliderSat;
	JSlider sliderLight;

	JLabel lbFile = new JLabel("File Name");
	JLabel lbHueBeging = new JLabel("-180");
	JLabel lbHueEnd = new JLabel("180");
	JLabel lbSatBeging = new JLabel("-100(%)");
	JLabel lbSatEnd = new JLabel("100(%)");;
	JLabel lbIntBeging = new JLabel("-100(%)");;
	JLabel lbIntEnd = new JLabel("100(%)");;
	JLabel lbBinBeging = new JLabel("0");;
	JLabel lbBinEnd = new JLabel("255");;

	JTextField tfFile = new JTextField(20);
	JTextField tfHueValue = new JTextField(3);
	JTextField tfSatValue = new JTextField(3);
	JTextField tfLightValue = new JTextField(3);
	JTextField tfBinValue = new JTextField(3);

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	boolean flag = false;

	HSLFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("HSL");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		tfFile.setText("D:/file/munich.png");
		exec();
	}

	void exec() {
		btnShow = new JButton("Show");
		tfHueValue.setText("0");
		tfSatValue.setText("0");
		tfLightValue.setText("0");
		tfBinValue.setText("127");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		sliderHue = new JSlider(-180, 180, 0);
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lbFile);
		cotrolPanelShow.add(tfFile);
		cotrolPanelHue.add(lbHueBeging);
		cotrolPanelHue.add(sliderHue);
		cotrolPanelHue.add(lbHueEnd);
		cotrolPanelHue.add(tfHueValue);
		cotrolPanelHue.add(btnHue);
		sliderSat = new JSlider(-100, 100, 0);
		cotrolPanelSat.add(lbSatBeging);
		cotrolPanelSat.add(sliderSat);
		cotrolPanelSat.add(lbSatEnd);
		cotrolPanelSat.add(tfSatValue);
		cotrolPanelSat.add(btnSat);
		sliderLight = new JSlider(-100, 100, 0);
		cotrolPanelInt.add(lbIntBeging);
		cotrolPanelInt.add(sliderLight);
		cotrolPanelInt.add(lbIntEnd);
		cotrolPanelInt.add(tfLightValue);
		cotrolPanelInt.add(btnLight);
		cotrolPanelHSL.add(btnHSL);

		cotrolPanelBin.add(tfBinValue);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.add(cotrolPanelHue);
		cotrolPanelMain.add(cotrolPanelSat);
		cotrolPanelMain.add(cotrolPanelInt);
		cotrolPanelMain.add(cotrolPanelHSL);

		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 220, 1500, 1500);
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

					imagePanel.paintComponent(imagePanel.getGraphics(), img);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file path doesn't exist!");
					e.printStackTrace();
				}
			}

		});

		sliderHue.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(sliderHue.getMousePosition().x);
				sliderHue.setValue(value);
				tfHueValue.setText(value + "");
			}
		});

		sliderHue.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (!flag) {
					int value = sliderHue.getValue();
					tfHueValue.setText(value + "");
				}
			}

		});

		sliderSat.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(sliderSat.getMousePosition().x);
				sliderSat.setValue(value);
				tfSatValue.setText(value + "");
			}
		});

		sliderSat.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (!flag) {
					int value = sliderSat.getValue();
					tfSatValue.setText(value + "");
				}
			}

		});

		sliderLight.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(sliderLight.getMousePosition().x);
				sliderLight.setValue(value);
				tfLightValue.setText(value + "");
			}
		});

		sliderLight.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (!flag) {
					int value = sliderLight.getValue();
					tfLightValue.setText(value + "");
				}
			}

		});

		tfHueValue.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				flag = true;
				String value = tfHueValue.getText();
				if (isNumber(value)) {
					sliderHue.setValue(Integer.parseInt(value));
				}
				flag = false;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

		});

		tfSatValue.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				flag = true;
				String value = tfSatValue.getText();
				if (isNumber(value)) {
					sliderSat.setValue(Integer.parseInt(value));
				}
				flag = false;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

		});

		tfLightValue.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				flag = true;
				String value = tfLightValue.getText();
				if (isNumber(value)) {
					sliderLight.setValue(Integer.parseInt(value));
				}
				flag = false;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

		});

		btnHue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int hue = sliderHue.getValue();
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						double[] HSL = Util.convertRGBIntoHSL(data[i][j][0], data[i][j][1], data[i][j][2]);
						int h = (int) (HSL[0] + hue);
						int[] RGB = Util.convertHSLIntoRGB(h, HSL[1], HSL[2]);
						int color = Util.getColor(RGB[0], RGB[1], RGB[2]);
						newImg.setRGB(j, i, color);
					}
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
			}

		});

		btnSat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int sat = sliderSat.getValue();
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						double[] HSL = Util.convertRGBIntoHSL(data[i][j][0], data[i][j][1], data[i][j][2]);
						double s = HSL[1] + sat / 100.0;
						int[] RGB = Util.convertHSLIntoRGB((int) HSL[0], s, HSL[2]);
						int color = Util.getColor(RGB[0], RGB[1], RGB[2]);
						newImg.setRGB(j, i, color);
					}
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
			}

		});

		btnLight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int light = sliderLight.getValue();
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						double[] HSL = Util.convertRGBIntoHSL(data[i][j][0], data[i][j][1], data[i][j][2]);
						double l = HSL[2] + light / 100.0;
						int[] RGB = Util.convertHSLIntoRGB((int) HSL[0], HSL[1], l);
						int color = Util.getColor(RGB[0], RGB[1], RGB[2]);
						newImg.setRGB(j, i, color);
					}
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
			}

		});

		btnHSL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int hue = sliderHue.getValue();
				int sat = sliderSat.getValue();
				int light = sliderLight.getValue();
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						double[] HSL = Util.convertRGBIntoHSL(data[i][j][0], data[i][j][1], data[i][j][2]);
						int h = (int) (HSL[0] + hue);
						double s = HSL[1] + sat / 100.0;
						double l = HSL[2] + light / 100.0;
						int[] RGB = Util.convertHSLIntoRGB(h, s, l);
						int color = Util.getColor(RGB[0], RGB[1], RGB[2]);
						newImg.setRGB(j, i, color);
					}
				}
				imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
			}

		});
	}

	boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
