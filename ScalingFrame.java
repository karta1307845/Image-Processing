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

public class ScalingFrame extends JFrame {
	final private JPanel cotrolPanelMain = new JPanel();
	final private JPanel cotrolPanelShow = new JPanel();
	final private JPanel cotrolPanelTranslate = new JPanel();
	final private ImagePanel imagePanel = new ImagePanel();

	JLabel lb = new JLabel("file path:");
	JLabel lbAmpX = new JLabel("x-ratio");
	JLabel lbAmpY = new JLabel("y-ratio");
	JTextField tfFile = new JTextField(20);
	JTextField tfAmpX = new JTextField(5);
	JTextField tfAmpY = new JTextField(5);
	JButton btnShow = new JButton("Show Original");
	JButton btnScaling = new JButton("Scaling");

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	ScalingFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Scaling");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		tfFile.setText("file/shot.png");
		tfAmpX.setText("1.0");
		tfAmpY.setText("1.0");

		cotrolPanelTranslate.setLayout(new GridLayout(2, 2));
		cotrolPanelTranslate.add(lbAmpX);
		cotrolPanelTranslate.add(tfAmpX);
		cotrolPanelTranslate.add(lbAmpY);
		cotrolPanelTranslate.add(tfAmpY);

		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lb);
		cotrolPanelShow.add(tfFile);
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
					float ampX = Float.parseFloat(tfAmpX.getText());
					float ampY = Float.parseFloat(tfAmpY.getText());
					if (ampX <= 0 || ampY <= 0) {
						JOptionPane.showMessageDialog(null, "ampX and ampY must be greater than zero.");
					} else {
						BufferedImage newImg = Util.scale(data, ampX, ampY);
						imagePanel.paintComponent(imagePanel.getGraphics(), newImg);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
