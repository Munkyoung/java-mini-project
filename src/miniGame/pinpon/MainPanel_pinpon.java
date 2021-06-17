package miniGame.pinpon;

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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import miniGame.MiniGame;

public class MainPanel_pinpon extends JPanel implements MiniGame,ActionListener{
	
	private JFrame frame; 
	private ImageIcon imageIcon;
	private Image image;
	private Image changeImage;
	private JFrame parent;
	
	public MainPanel_pinpon(JFrame parent){
		super();
		this.parent = parent;
		
		setPreferredSize(new Dimension(ROW,COL));
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		add(setStartButton());
		add(setRuleButton());
		
		
		// 프레임 부분 
		frame = new JFrame("니편 내편");
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
			Image background=new ImageIcon(MainPanel_pinpon.class.getResource("image\\게임방법.png")).getImage();
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
	
	
	public void addNewPanel(pinpon pinpon) {
		//새로운 패널 열기
		try {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(pinpon);
			frame.revalidate();
			frame.repaint();
		} catch (IllegalArgumentException e) {
			System.out.println("시작!");
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
			try {
				frame.dispose();
				addNewPanel(new pinpon());
			} catch (IllegalArgumentException e2) {
				System.out.println("시작!");
			}
			
			break;
		
		case BUTTON_RULE :
			rulePanel();
			break;
		
		}
		
	}

	
	public static void main(String[] args) {
		new MainPanel_pinpon(null);
	}
	
}
