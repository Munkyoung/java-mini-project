package miniGame.minesweeper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import miniGame.MiniGame;

public class MainPanel_MineSweeper extends JPanel implements MiniGame,ActionListener{
	
	private JFrame frame; 
	private ImageIcon imageIcon;
	private Image image;
	private Image changeImage;
	private final String EASY = "쉬움 (9X9)"; // 폭탄 10개
	private final String BASIC = "보통 (16X16)"; // 폭탄 40개
	private final String HARD = "어려움 (30X16)"; // 폭탄 100개
	
	private JFrame parent;
	
	public MainPanel_MineSweeper(JFrame parent){
		super();
		this.parent = parent;
		
		setPreferredSize(new Dimension(ROW,COL));
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		add(setStartButton());
		add(setRuleButton());
		
		
		// 프레임 부분 
		frame = new JFrame("지뢰찾기");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					frame.dispose();
					parent.requestFocus();
				}
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private void playgame() {
		// TODO Auto-generated method stub

	}
		
	
	private JButton setStartButton() {
		JButton start = new JButton(BUTTON_START);
		
		start.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		start.setBackground(new Color(135, 135, 135));
		start.setBounds(250, 150, 300, 200);
		start.setBorderPainted(true);
		start.addActionListener(this);
		
		return start;
	}
	
	private JButton setRuleButton() {
		JButton rule = new JButton(BUTTON_RULE);
		
		rule.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		rule.setBackground(new Color(135, 135, 135));
		rule.setBounds(250,400,300,200);
		rule.setBorderPainted(true);
		rule.addActionListener(this);
		
		return rule;
	}
	
	
	public void rulePanel() {
		JFrame frame = new JFrame("게임방법");
		
		JPanel panel = new JPanel()	{
			Image background=new ImageIcon(MainPanel_MineSweeper.class.getResource("image\\게임방법.png")).getImage();
			public void paint(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, 800, 750, null);//background를 그려줌		
		}};
		panel.setPreferredSize(new Dimension(800,750));
		
		frame.add(panel);
		frame.pack();
		
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					frame.dispose();
					
				}
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	public void addNewPanel(JPanel panel) {
		//새로운 패널 열기
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		frame.revalidate();
		frame.repaint();
	}
	
	public void retry() {
		// 게임 다시 시작
//		addNewPanel(new MineSweeper(this));
		switch(frame.getTitle()) {
		case EASY : // 9 X 9  // 폭탄 10개
			addNewPanel(new MineSweeper(this, 9, 9, 10));
			break;
		
		case BASIC : // 16 X 16 // 폭탄40개
			addNewPanel(new MineSweeper(this, 16, 16, 40));
			break;
		
		case HARD : // 30 X 16 // 폭탄 100개
			addNewPanel(new MineSweeper(this, 30, 16, 100));
			break;
		}
	}
	
	public void editFrameTitle(String title) {
		frame.setTitle(title);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case BUTTON_START :
			addNewPanel(new LevelChoice(this));
			break;
		
		case BUTTON_RULE :
			rulePanel();
			break;
		
		}
		
	}

	
	public static void main(String[] args) {
		new MainPanel_MineSweeper(null);
	}
	
}
