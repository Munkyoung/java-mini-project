package miniGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import miniGame.game_2048.MainPanel_2048;
import miniGame.hamburger.Hamburger;
import miniGame.hamburger.MainPanel_Hamburger;
import miniGame.minesweeper.LevelChoice;
import miniGame.minesweeper.MainPanel_MineSweeper;
import miniGame.pinpon.MainPanel_pinpon;
import miniGame.snake.MainPanel_SnakeGame;
import miniGame.tetris.MainPanel_Tetris;

public class MainFrame extends JFrame implements MiniGame, ActionListener{
	Image image;
	Image changeImage;
	ImageIcon imageIcon;
	
	MainFrame(){
		super("미니게임 천국");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ROW+50, COL+50);
		
		add(mainPanel());
		
		setFocusable(true);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public JPanel mainPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		panel.add(northPanel(), BorderLayout.NORTH);
		panel.add(centerPanel(), BorderLayout.CENTER);
		panel.add(sidePanel(), BorderLayout.EAST);
		panel.add(sidePanel(), BorderLayout.WEST);
		return panel;
	}
	
	public JPanel northPanel() {
		JPanel panel = new JPanel();
		panel.add(setIconButton(null, PATH_MINGAME, ROW-50, 150));
		panel.setBackground(Color.white);
		return panel;
	}
	
	public JPanel centerPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3,2));
		panel.add(setIconButton(TITLE_MINESWEEPER, PATH_MINESWEEPER, 150, 150));
		panel.add(setIconButton(TITLE_HAMBURGER, PATH_HAMBURGER, 150, 150));
		panel.add(setIconButton(TITLE_TETRIS, PATH_TETRIS, 150, 150));
		panel.add(setIconButton(TITLE_SNAKE, PATH_SNAKE, 150, 150));
		panel.add(setIconButton(TITLE_2048, PATH_2048, 150, 150));
		panel.add(setIconButton(TITLE_PINPON, PATH_PINPON, 150, 150));
		
		panel.setBackground(Color.white);
		
		return panel;
	}
	
	public JPanel sidePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(100,800));
		
		return panel;
	}
	

	public JButton setIconButton(String title, String imagePath, int ImageWidth, int ImageHeight) {
		JButton button = new JButton(title);
		
		addButtonImage(button, imagePath, ImageWidth, ImageHeight);
		setButtonView(button);
		button.addActionListener(this);
		
		return button;
	}
	
	public void addButtonImage(JButton button, String imagePath, int ImageWidth, int ImageHeight) {
		image = new ImageIcon(MainFrame.class.getResource(imagePath)).getImage();
		changeImage = image.getScaledInstance(ImageWidth, ImageHeight, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(changeImage);
		button.setIcon(imageIcon);
	}
	
	public void setButtonView(JButton button) {
		button.setBorderPainted(false); // 버튼 테두리 없애기
		button.setFocusPainted(false); // 버튼 선택되었을때 테두리 안생기게
		button.setContentAreaFilled(false); // 버튼 내용 색 안채움
		button .setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		button .setVerticalTextPosition(JButton.BOTTOM); // 텍스트 아래로
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case TITLE_MINESWEEPER :
			new MainPanel_MineSweeper(this);
			break;
		
		case TITLE_HAMBURGER :
			new MainPanel_Hamburger(this);
			break;
		
		case TITLE_SNAKE :
			new MainPanel_SnakeGame(this);
			break;
			
		case TITLE_TETRIS : 
			new MainPanel_Tetris(this);
			break;
			
		case TITLE_2048 : 
			new MainPanel_2048(this);
			break;
			
		case TITLE_PINPON : 
			new MainPanel_pinpon(this);
			break;
		
		}
		
	}
	
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
