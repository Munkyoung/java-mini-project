package miniGame.hamburger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import miniGame.MainFrame;
import miniGame.MiniGame;

public class MainPanel_Hamburger extends JPanel implements MiniGame,ActionListener{
	
	protected JFrame frame; 
	protected int totalClear;
	private ImageIcon imageIcon;
	private Image image;
	private Image changeImage;
	
	private JFrame parent;
	
	public MainPanel_Hamburger(JFrame parent){
		super();
		this.parent = parent;
		
		setPreferredSize(new Dimension(ROW,COL));
		setBackground(Color.white);
		setLayout(null);
		
		add(setStartButton());
		add(setRuleButton());
		
		
		// 프레임 부분 
		frame = new JFrame("햄버거 만들기");
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
		start.setBackground(new Color(255, 240, 122));
		start.setBounds(250, 150, 300, 200);
		start.setBorderPainted(false);
		start.addActionListener(this);
		
		return start;
	}
	
	private JButton setRuleButton() {
		JButton rule = new JButton(BUTTON_RULE);
		
		rule.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		rule.setBackground(new Color(255, 240, 122));
		rule.setBounds(250,400,300,200);
		rule.setBorderPainted(false);
		rule.addActionListener(this);
		
		return rule;
	}
	
	
	public void rulePanel() {
		JFrame frame = new JFrame("게임방법");
		
		JPanel panel = new JPanel()	{
			Image background = new ImageIcon(MainPanel_Hamburger.class.getResource("image/게임방법.png")).getImage();
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
					parent.requestFocus();
				}
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	public void addNewPanel(JComponent panel) {
		//새로운 패널 열기
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
	
	public void retry() {
		// 게임 다시 시작
	}
	
	public void editFrameTitle(String title) {
		frame.setTitle(title);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case BUTTON_START :
			addNewPanel(new Hamburger(this));
			break;
		
		case BUTTON_RULE :
			rulePanel();
			break;
		
		}
		
	}

	
	public static void main(String[] args) {
		new MainPanel_Hamburger(null);
	}
	
}