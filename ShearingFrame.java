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

public class ShearingFrame extends JFrame {
	final private JPanel cotrolPanelMain = new JPanel();
	final private JPanel cotrolPanelShow = new JPanel();
	final private JPanel cotrolPanelBackColor = new JPanel();
	final private JPanel cotrolPanelTranslate = new JPanel();
	final private ImagePanel imagePanel = new ImagePanel();

	JLabel lb = new JLabel("file path:");
	JLabel lbRed = new JLabel("BG (R)");
	JLabel lbGreen = new JLabel("BG (G)");
	JLabel lbBlue = new JLabel("BG (B)");
	JLabel lbShearX = new JLabel("x-ratio");
	JLabel lbShearY = new JLabel("y-ratio ");
	JTextField tfFile = new JTextField(20);
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfShearX = new JTextField(5);
	JTextField tfShearY = new JTextField(5);
	JButton btnShow = new JButton("Show Original");
	JButton btnScaling = new JButton("Shearing");

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	ShearingFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Shearing");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		tfFile.setText("file/shot.png");
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
		tfShearX.setText("0.0");
		tfShearY.setText("0.0");

		cotrolPanelBackColor.setLayout(new GridLayout(3, 2));
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);

		cotrolPanelTranslate.setLayout(new GridLayout(2, 2));
		cotrolPanelTranslate.add(lbShearX);
		cotrolPanelTranslate.add(tfShearX);
		cotrolPanelTranslate.add(lbShearY);
		cotrolPanelTranslate.add(tfShearY);

		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelShow.add(cotrolPanelBackColor);
		cotrolPanelShow.add(cotrolPanelTranslate);
		cotrolPanelShow.add(btnScaling);
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

		btnScaling.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					float shearX = Float.parseFloat(tfShearX.getText());
					float shearY = Float.parseFloat(tfShearY.getText());
					if (shearX < 0 || shearY < 0) {
						JOptionPane.showMessageDialog(null, "shearX and shearY must be greater than or equal to zero.");
					} else {
						int r = Integer.parseInt(tfRed.getText());
						int g = Integer.parseInt(tfGreen.getText());
						int b = Integer.parseInt(tfBlue.getText());
						int bgColor = Util.getColor(r, g, b);
						BufferedImage newImg = Util.shear(data, shearX, shearY, bgColor);
						imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
