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

public class TranslationFrame extends JFrame {
	final private JPanel cotrolPanelMain = new JPanel();
	final private JPanel cotrolPanelShow = new JPanel();
	final private JPanel cotrolPanelBackColor = new JPanel();
	final private JPanel cotrolPanelTranslate = new JPanel();
	final private ImagePanel imagePanel = new ImagePanel();
	final private ImagePanel imagePanel2 = new ImagePanel();

	JLabel lb = new JLabel("file path:");
	JLabel lbRed = new JLabel("BG (R)");
	JLabel lbGreen = new JLabel("BG (G)");
	JLabel lbBlue = new JLabel("BG (B)");
	JLabel lbDeltaX = new JLabel("x-delta");
	JLabel lbDeltaY = new JLabel("y-delta");
	JTextField tfFile = new JTextField(20);
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfDeltaX = new JTextField(5);
	JTextField tfDeltaY = new JTextField(5);
	JButton btnShow = new JButton("Show Original");
	JButton btnTranslate = new JButton("Translation");

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	TranslationFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Translation");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		tfFile.setText("file/shot.png");
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
		tfDeltaX.setText("0");
		tfDeltaY.setText("0");

		cotrolPanelBackColor.setLayout(new GridLayout(3, 2));
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);

		cotrolPanelTranslate.setLayout(new GridLayout(2, 2));
		cotrolPanelTranslate.add(lbDeltaX);
		cotrolPanelTranslate.add(tfDeltaX);
		cotrolPanelTranslate.add(lbDeltaY);
		cotrolPanelTranslate.add(tfDeltaY);

		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
		cotrolPanelShow.add(cotrolPanelBackColor);
		cotrolPanelShow.add(cotrolPanelTranslate);
		cotrolPanelShow.add(btnTranslate);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 500);
		getContentPane().add(cotrolPanelMain);

		imagePanel.setBounds(20, 130, 2000, 620);
		getContentPane().add(imagePanel);
		imagePanel2.setBounds(650, 130, 1500, 1500);
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

		btnTranslate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					int deltaX = Integer.parseInt(tfDeltaX.getText());
					int deltaY = Integer.parseInt(tfDeltaY.getText());
					if (deltaX < 0 || deltaY < 0) {
						JOptionPane.showMessageDialog(null, "deltaX and deltaY must be greater than or equal to zero.");
					} else {
						int r = Integer.parseInt(tfRed.getText());
						int g = Integer.parseInt(tfGreen.getText());
						int b = Integer.parseInt(tfBlue.getText());
						int bgColor = Util.getColor(r, g, b);
						BufferedImage newImg = Util.translation(data, deltaX, deltaY, bgColor);
						imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
