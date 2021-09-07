package final_project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Menu extends JFrame{
	private final JPanel controlPanel = new JPanel();
	private final JMenuBar menuBar = new JMenuBar();

	public Menu() {
		prepareGUI();
	}

	private void prepareGUI() {
		controlPanel.setLayout(new FlowLayout());
		showMenu();
	}

	private void showMenu() {		
		JMenu basicMenu = new JMenu("基本(B)");
		basicMenu.setMnemonic(KeyEvent.VK_B);
		JMenu rotateMenu = new JMenu("旋轉(R)");
		rotateMenu.setMnemonic(KeyEvent.VK_R);
		JMenu colorMenu = new JMenu("色彩(C)");
		colorMenu.setMnemonic(KeyEvent.VK_C);
		JMenu filterMenu = new JMenu("濾波器(F)");
		filterMenu.setMnemonic(KeyEvent.VK_F);
		JMenu otherMenu = new JMenu("其他(O)");
		otherMenu.setMnemonic(KeyEvent.VK_O);
		
		JMenuItem translationMI = new JMenuItem("平移(T)");
		translationMI.setMnemonic(KeyEvent.VK_T);
		translationMI.setActionCommand("translation");
		
		JMenuItem scalingMI = new JMenuItem("縮放(S)");
		scalingMI.setMnemonic(KeyEvent.VK_S);
		scalingMI.setActionCommand("scaling");
		
		JMenuItem shearingMI = new JMenuItem("切移(H)");
		shearingMI.setMnemonic(KeyEvent.VK_H);
		shearingMI.setActionCommand("shearing");
		
		JMenuItem upDownMI = new JMenuItem("上下顛倒(U)");
		upDownMI.setMnemonic(KeyEvent.VK_U);
		upDownMI.setActionCommand("upDown");
		
		JMenuItem LeftRightMI = new JMenuItem("左右相反(L)");
		LeftRightMI.setMnemonic(KeyEvent.VK_L);
		LeftRightMI.setActionCommand("leftRight");
		
		JMenuItem inverseMI = new JMenuItem("反白(I)");
		inverseMI.setMnemonic(KeyEvent.VK_I);
		inverseMI.setActionCommand("inverse");
		
		JMenuItem grayMI = new JMenuItem("灰階(G)");
		grayMI.setMnemonic(KeyEvent.VK_G);
		grayMI.setActionCommand("gray");
		
		JMenuItem hslMI = new JMenuItem("HSL");
		hslMI.setMnemonic(KeyEvent.VK_L);
		hslMI.setActionCommand("HSL");
		
		JMenuItem left90MI = new JMenuItem("左旋90度(L)");
		left90MI.setMnemonic(KeyEvent.VK_L);
		left90MI.setActionCommand("left90");
		
		JMenuItem right90MI = new JMenuItem("右旋90度(R)");
		right90MI.setMnemonic(KeyEvent.VK_R);
		right90MI.setActionCommand("right90");
		
		JMenuItem rotate180MI = new JMenuItem("旋轉180度(O)");
		rotate180MI.setMnemonic(KeyEvent.VK_O);
		rotate180MI.setActionCommand("rotate180");
		
		JMenuItem rotateMI = new JMenuItem("任意旋轉(A)");
		rotateMI.setMnemonic(KeyEvent.VK_A);
		rotateMI.setActionCommand("rotate");
		
		JMenuItem removeNoiseMI = new JMenuItem("去除雜點(N)");
		removeNoiseMI.setMnemonic(KeyEvent.VK_N);
		removeNoiseMI.setActionCommand("removeNoise");
		
		JMenuItem blurMI = new JMenuItem("模糊(B)");
		blurMI.setMnemonic(KeyEvent.VK_B);
		blurMI.setActionCommand("blur");
		
		JMenuItem sharpMI = new JMenuItem("清晰(S)");
		sharpMI.setMnemonic(KeyEvent.VK_S);
		sharpMI.setActionCommand("sharp");
		
		JMenuItem stretchingMI = new JMenuItem("影像強化(S)");
		stretchingMI.setMnemonic(KeyEvent.VK_S);
		stretchingMI.setActionCommand("stretching");
		
		JMenuItem edgeDetectMI = new JMenuItem("邊緣偵測(E)");
		edgeDetectMI.setMnemonic(KeyEvent.VK_E);
		edgeDetectMI.setActionCommand("edgeDetect");
		
		JMenuItem binaryMI = new JMenuItem("Binary");
		binaryMI.setMnemonic(KeyEvent.VK_B);
		binaryMI.setActionCommand("binary");
		
		JMenuItem exitMI = new JMenuItem("Exit");
		exitMI.setMnemonic(KeyEvent.VK_X);
		exitMI.setActionCommand("EXIT");
		
		MenuItemListener menuItemListener = new MenuItemListener();
		translationMI.addActionListener(menuItemListener);
		scalingMI.addActionListener(menuItemListener);
		shearingMI.addActionListener(menuItemListener);
		upDownMI.addActionListener(menuItemListener);
		LeftRightMI.addActionListener(menuItemListener);
		inverseMI.addActionListener(menuItemListener);
		grayMI.addActionListener(menuItemListener);
		hslMI.addActionListener(menuItemListener);
		left90MI.addActionListener(menuItemListener);
		right90MI.addActionListener(menuItemListener);
		rotate180MI.addActionListener(menuItemListener);
		rotateMI.addActionListener(menuItemListener);
		removeNoiseMI.addActionListener(menuItemListener);
		blurMI.addActionListener(menuItemListener);
		sharpMI.addActionListener(menuItemListener);
		stretchingMI.addActionListener(menuItemListener);
		edgeDetectMI.addActionListener(menuItemListener);
		binaryMI.addActionListener(menuItemListener);
		exitMI.addActionListener(menuItemListener);
		
		basicMenu.add(translationMI);
		basicMenu.add(scalingMI);
		basicMenu.add(shearingMI);
		basicMenu.add(upDownMI);
		basicMenu.add(LeftRightMI);
		colorMenu.add(inverseMI);
		colorMenu.add(grayMI);
		colorMenu.add(hslMI);
		rotateMenu.add(left90MI);
		rotateMenu.add(right90MI);
		rotateMenu.add(rotate180MI);
		rotateMenu.add(rotateMI);
		filterMenu.add(removeNoiseMI);
		filterMenu.add(blurMI);
		filterMenu.add(sharpMI);
		otherMenu.add(stretchingMI);
		otherMenu.add(edgeDetectMI);
		otherMenu.add(binaryMI);
		otherMenu.addSeparator();
		otherMenu.add(exitMI);
		
		menuBar.add(basicMenu);
		menuBar.add(colorMenu);
		menuBar.add(rotateMenu);
		menuBar.add(filterMenu);
		menuBar.add(otherMenu);
		setJMenuBar(menuBar);
	}

	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("HSL"))
				new HSLFrame().setVisible(true);
			else if(e.getActionCommand().equals("translation"))
				new TranslationFrame().setVisible(true);
			else if(e.getActionCommand().equals("scaling"))
				new ScalingFrame().setVisible(true);
			else if(e.getActionCommand().equals("shearing"))
				new ShearingFrame().setVisible(true);
			else if(e.getActionCommand().equals("upDown"))
				new UpDownFrame().setVisible(true);
			else if(e.getActionCommand().equals("leftRight"))
				new LeftRightFrame().setVisible(true);
			else if(e.getActionCommand().equals("inverse"))
				new InverseFrame().setVisible(true);
			else if(e.getActionCommand().equals("gray"))
				new GrayFrame().setVisible(true);
			else if(e.getActionCommand().equals("left90"))
				new Left90Frame().setVisible(true);
			else if(e.getActionCommand().equals("right90"))
				new Right90Frame().setVisible(true);
			else if(e.getActionCommand().equals("rotate180"))
				new Rotate180Frame().setVisible(true);
			else if(e.getActionCommand().equals("rotate"))
				new RotationFrame().setVisible(true);
			else if(e.getActionCommand().equals("removeNoise"))
				new NoiseFrame().setVisible(true);
			else if(e.getActionCommand().equals("blur"))
				new BlurFrame().setVisible(true);
			else if(e.getActionCommand().equals("sharp"))
				new SharpFrame().setVisible(true);
			else if(e.getActionCommand().equals("stretching"))
				new StretchFrame().setVisible(true);
			else if(e.getActionCommand().equals("edgeDetect"))
				new EdgeFrame().setVisible(true);
			else if(e.getActionCommand().equals("binary"))
				new BinaryFrame().setVisible(true);
			else if (e.getActionCommand().equals("EXIT"))
				System.exit(0);
		}
	}
}