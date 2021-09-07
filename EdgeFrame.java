package final_project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

public class EdgeFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelSlider = new JPanel();
	JPanel cotrolPanelEdgeEnh = new JPanel();
	JSlider sliderThresh;
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	JButton btnShow;
	JButton btnEdgeDet = new JButton("Edge Detect");
	JTextField tfThresh = new JTextField(5);
	JLabel labThreshLow = new JLabel("Thresh: Low");
	JLabel labThreshHigh = new JLabel("High");
	JLabel lb = new JLabel("file path:");
	JTextField tfFile = new JTextField(20);

	boolean tfFlag = false;
	int thresh = 1000;
	int[][][] data;
	int[][] gray;
	int height;
	int width;
	BufferedImage img = null;

	EdgeFrame() {
		setBounds(0, 0, 1500, 1500);
		setTitle("邊緣偵測");
		getContentPane().setLayout(null);
		tfFile.setText("file/shot.png");
		
		btnShow = new JButton("Show Original");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelMain.add(cotrolPanelShow);
		sliderThresh = new JSlider(0, 10000, 1000);
		cotrolPanelSlider.add(labThreshLow);
		cotrolPanelSlider.add(sliderThresh);
		cotrolPanelSlider.add(labThreshHigh);
		tfThresh.setText("1000");
		cotrolPanelSlider.add(tfThresh);
		cotrolPanelSlider.add(btnEdgeDet);
		cotrolPanelMain.add(cotrolPanelSlider);
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

		btnEdgeDet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (img != null) {
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int slope = Util.getSlope(j, i, gray);
							int color;
							if (slope >= thresh) {
								color = Util.getColor(255, 255, 255);
							} else {
								color = Util.getColor(0, 0, 0);
							}
							newImg.setRGB(j, i, color);
						}
					}
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});

		sliderThresh.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = this.valueForXPosition(sliderThresh.getMousePosition().x);
				sliderThresh.setValue(value);
			}
		});

		sliderThresh.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = sliderThresh.getValue();
				if (!tfFlag && value != thresh) {
					thresh = value;
					tfThresh.setText(Integer.toString(value));
				}
			}

		});

		tfThresh.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				tfFlag = true;
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				tfFlag = false;
			}

		});

		tfThresh.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (tfFlag) {
					String value = tfThresh.getText();
					if (Util.isNumber(value)) {
						sliderThresh.setValue(Integer.parseInt(value));
						thresh = Integer.parseInt(value);
					} else if (value.equals("")) {
						sliderThresh.setValue(0);
						thresh = 0;
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (tfFlag) {
					String value = tfThresh.getText();
					if (Util.isNumber(value)) {
						sliderThresh.setValue(Integer.parseInt(value));
						thresh = Integer.parseInt(value);
					} else if (value.equals("")) {
						sliderThresh.setValue(0);
						thresh = 0;
					}
				}
			}

		});
	}
}
