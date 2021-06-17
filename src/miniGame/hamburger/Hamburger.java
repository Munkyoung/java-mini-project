package miniGame.hamburger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import miniGame.MiniGame;

interface HamburgerGameConstants {
	final int DOWNBREAD = 0;
	final int LETTUCE = 1;
	final int PATTY = 2;
	final int CHEESE = 3;
	final int UPBREAD = 4;
	final int FIRST_FOOD = 1;
	final int LAST_FOOD = 3;
	final int TOTAL_FOOD = 3;
	final int MAXIMUM_FOOD = 7; // 최대 속재료 (빵제외)
	final String DOWNBREAD_PATH = "image/아래빵.png";
	final String LETTUCE_PATH = "image/양상추.png";
	final String PATTY_PATH = "image/고기.png";
	final String CHEESE_PATH = "image/치즈.png";
	final String UPBREAD_PATH = "image/위빵.png";
	final String KEYPAD_PATH = "image/방향키.png";
	
	final Image GAMEOVER = new ImageIcon(Hamburger.class.getResource("image/gameover.png")).getImage();
	final Image TIMEOVER = new ImageIcon(Hamburger.class.getResource("image/timeover.png")).getImage();
	final Image CLEAR = new ImageIcon(Hamburger.class.getResource("image/clear.png")).getImage();
	final Image GAMECLEAR = new ImageIcon(Hamburger.class.getResource("image/gameclear.png")).getImage();
	final Image BACKGROUND = new ImageIcon(Hamburger.class.getResource("image/게임 배경.png")).getImage();
	
	// 재료 1개당 높이 : 70
	int ITEM_HEIGHT = 70;
	// 재료 1개당 너비 : 150
	int ITEM_WIDTH = 150;

	// 이미지 겹침 간격 : 13
	int ITEM_COLLIDE = 13;

}

class QuestionHamburgerPanel extends JPanel implements MiniGame, HamburgerGameConstants {

	private int[] hamburgerArray;

	public int[] getHamburgerArray() {
		return hamburgerArray;
	}

	public QuestionHamburgerPanel() {
		hamburgerArray();
		setPreferredSize(new Dimension(getPanelWidth(), getPanelHeight()));
	}

	public int getPanelHeight() {
		return ((hamburgerArray.length) * ITEM_COLLIDE + ITEM_HEIGHT);
	}

	public int getPanelWidth() {
		return ITEM_WIDTH + 50;
	}

	@Override
	public void paintComponent(Graphics g) {
		// 햄버거 문제 쌓기

//		hamburgerArray();

		g.setColor(new Color(123, 180, 201));
		g.fillRoundRect(0, 0, getPanelWidth() - 30, getPanelHeight() + 30, 30, 30);

		Image image;
		int questionX = 10;
		int questionY = getPanelHeight() - 70;

		for (int i = 0; i < hamburgerArray.length; ++i) {
			switch (hamburgerArray[i]) {
			case DOWNBREAD:
				image = new ImageIcon(Hamburger.class.getResource(DOWNBREAD_PATH)).getImage();
				g.drawImage(image, questionX, questionY, ITEM_WIDTH, ITEM_HEIGHT, this);
				break;
			case LETTUCE:
				image = new ImageIcon(Hamburger.class.getResource(LETTUCE_PATH)).getImage();
				g.drawImage(image, questionX, questionY, ITEM_WIDTH, ITEM_HEIGHT, this);
				break;
			case PATTY:
				image = new ImageIcon(Hamburger.class.getResource(PATTY_PATH)).getImage();
				g.drawImage(image, questionX, questionY, ITEM_WIDTH, ITEM_HEIGHT, this);
				break;
			case CHEESE:
				image = new ImageIcon(Hamburger.class.getResource(CHEESE_PATH)).getImage();
				g.drawImage(image, questionX, questionY, ITEM_WIDTH, ITEM_HEIGHT, this);
				break;
			case UPBREAD:
				image = new ImageIcon(Hamburger.class.getResource(UPBREAD_PATH)).getImage();
				g.drawImage(image, questionX, questionY, ITEM_WIDTH, ITEM_HEIGHT, this);
				break;
			}

			questionY -= ITEM_COLLIDE;

		}

		// repaint();
	}

	public void hamburgerArray() {
		// 햄버거 문제낼때 배열 - 재료는 랜덤으로 쌓이게
		int random = (int) (Math.random() * MAXIMUM_FOOD) + (TOTAL_FOOD+UPBREAD+DOWNBREAD);
		hamburgerArray = new int[random + 1];

		for (int i = FIRST_FOOD; i < random; ++i) {
			int rand = (int) (Math.random() * LAST_FOOD) + FIRST_FOOD;
			hamburgerArray[i] = rand;
		}

		hamburgerArray[0] = DOWNBREAD;
		hamburgerArray[random] = UPBREAD;

	}

}

// 메인 클래스
public class Hamburger extends JLayeredPane implements MiniGame, HamburgerGameConstants, KeyListener {

	private MainPanel_Hamburger parents;
	private int keyX = 200;
	private int keyY = 600;
	private int itemcolled = 40;
	private int downKeyCount;

	private Image image;
	private Image gameover;
	private Image keyImage;
	private Image timerImage;
	private boolean gameOver;
	private boolean timeOver;
	private boolean clear;
	private boolean gameClear;
	private int time;
	private Timer timer;
	private String timePath; 

	private int[] hamburgerArray; // 만들어지는 햄버거 재료 배열
	private TreeMap<String, Integer> map = new TreeMap<String, Integer>(); // 이미지경로 : 재료값
	private ArrayList<String> imageArray = new ArrayList<>(); // 이미지 경로 저장하는 배열

	Hamburger(MainPanel_Hamburger parents) {
		super();
		this.parents = parents;
		setPreferredSize(new Dimension(ROW, COL));
		add(getQuestionPanel());
		
		parents.frame.addKeyListener(this);
		
		// 타이머 설정
		timePath = "image/15.png";
		time = 15;
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				--time;
				
				setTimerImage();
				repaint();
				
				if(time==0) {
					timer.stop();
					timeOver();
				}
				
			}
		});
		
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		
		g.drawImage(BACKGROUND, 0, 0, 800, 800, this);
		
		timerImage = new ImageIcon(Hamburger.class.getResource(timePath)).getImage();
		g.drawImage(timerImage, 350, 10, 100, 100, this);
		
		image = new ImageIcon(Hamburger.class.getResource(KEYPAD_PATH)).getImage();
		g.drawImage(image, 600, 25, 180, 180, this);
		
		makeHamburger(g); // 방향키누르면 생기는 이미지 배열로만들어 모두저장하며 출력하기 
		
		if(gameOver) {
			g.drawImage(GAMEOVER, 200, 200, 400, 400, this);
		}
		
		if(timeOver) {
			g.drawImage(TIMEOVER, 200, 200, 400, 400, this);
		}
		
		if(gameClear) {
			g.drawImage(GAMECLEAR, 200, 200, 400, 400, this);
		}
		
		
	}
	
	public void setTimerImage() {
		switch (time) {
		case 14:
			timePath = "image/14.png";
			break;
		case 13:
			timePath = "image/13.png";
			break;
		case 12:
			timePath = "image/12.png";
			break;
		case 11:
			timePath = "image/11.png";
			break;
		case 10:
			timePath = "image/10.png";
			break;
		case 9:
			timePath = "image/9.png";
			break;
		case 8:
			timePath = "image/8.png";
			break;
		case 7:
			timePath = "image/7.png";
			break;
		case 6:
			timePath = "image/6.png";
			break;
		case 5:
			timePath = "image/5.png";
			break;
		case 4:
			timePath = "image/4.png";
			break;
		case 3:
			timePath = "image/3.png";
			break;
		case 2:
			timePath = "image/2.png";
			break;
		case 1:
			timePath = "image/1.png";
			break;
		case 0:
			timePath = "image/0.png";
			break;
		}
	}
	
	public void timeOver() {
		timeOver = true;
		repaint();
		
		parents.frame.removeKeyListener(this);
		replayGameYesOrNo("TIME OVER!", "다시 하시겠습니까?");
	}
	public void makeHamburger(Graphics g) {
		
		if (imageArray.size() > 0) { // 방향키누르면 생기는 이미지 배열로만들어 모두저장하며 출력하기
			int keyCount = 1;
			
			for (int i = 0; i < imageArray.size(); i++) {
				keyImage = new ImageIcon(Hamburger.class.getResource(imageArray.get(i))).getImage();
				g.drawImage(keyImage, 200, keyY - (itemcolled * keyCount), 400, 200, this);
				++keyCount;
			}

		}
	}

	public JPanel getQuestionPanel() {
		QuestionHamburgerPanel questionPanel = new QuestionHamburgerPanel();
		questionPanel.setBounds(20, // 시작 x
				20, // 시작 y
				questionPanel.getPreferredSize().height + 100, // 사이즈 높이
				questionPanel.getPreferredSize().width + 100// 사이즈 너비
		);
		hamburgerArray = questionPanel.getHamburgerArray();

		return questionPanel;
	}
	
	
	public void checkMakeHamburger() {
		
		
		for (int i = 0; i < imageArray.size(); ++i) {
			if (hamburgerArray[i] == map.get(imageArray.get(i))) {
				// 만들어진 햄버거 값 이랑 내가 누른 값이 일치하는지
				continue;
			} else {
				gameOver();
				return;
			}
		}
		
	}
	
	public void replayGameYesOrNo(String title, String mainText) {
		// jop로 다시하기 yes, no 만들기
		int option = JOptionPane.showConfirmDialog(this, mainText, title, JOptionPane.YES_NO_OPTION); 
		if(option == JOptionPane.YES_OPTION) {
			parents.totalClear = 0;
			parents.addNewPanel(new Hamburger(parents));
			
		}
		
		return;
	}
	
	public void gameOver() {
		if(!timeOver) {
		gameOver = true;
		timer.stop();
		parents.totalClear = 0;
		parents.frame.removeKeyListener(this);
		repaint();
		
		replayGameYesOrNo("GAME OVER!", "다시 하시겠습니까?");
		}
		
	}
	
	public void clear() {
		clear = true;

		timer.stop();
		
		if(parents.totalClear == 4) {
			parents.frame.removeKeyListener(this);
			gameClear = true;
			repaint();
			
			
			replayGameYesOrNo("CLEAR!", "축하합니다 ! GAME CLEAR ! \n다시 하시겠습니까?");
			return;
		}
		
		if(clear) {
			++parents.totalClear;
			repaint();
			
			parents.frame.removeKeyListener(this);
			parents.addNewPanel(new Hamburger(parents));
			return;
		}
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(imageArray.size() == hamburgerArray.length &&
				e.getKeyCode()!=KeyEvent.VK_SPACE) {
			gameOver();
			return;
		}

		switch (e.getKeyCode()) {
		// 아래 - 빵 / 좌 - 양상추 / 우 - 치즈 / 위 - 고기
		
		case KeyEvent.VK_DOWN: // 아래키 처음누르면 아래빵 두번째는 위빵 세번째는 다시 아래빵
			++downKeyCount;
			if (downKeyCount == 1) {
				imageArray.add(DOWNBREAD_PATH); // 이미지 경로만 어레이리스트에 추가추가
				map.put(DOWNBREAD_PATH, DOWNBREAD);
			}

			if (downKeyCount == 2) {
				imageArray.add(UPBREAD_PATH);
				map.put(UPBREAD_PATH, UPBREAD);
				downKeyCount = 0;
			}
			repaint();
			break;
		case KeyEvent.VK_UP:
			imageArray.add(PATTY_PATH);
			map.put(PATTY_PATH, PATTY);
			repaint();
			break;
		case KeyEvent.VK_LEFT:
			imageArray.add(LETTUCE_PATH);
			map.put(LETTUCE_PATH, LETTUCE);
			repaint();
			break;
		case KeyEvent.VK_RIGHT:
			imageArray.add(CHEESE_PATH);
			map.put(CHEESE_PATH, CHEESE);
			repaint();
			break;
		case KeyEvent.VK_SPACE :
			// 쌓아놓은 햄버거 갯수랑 마지막햄버거까지 일치하는지
			if(hamburgerArray.length == imageArray.size()
					&& hamburgerArray[hamburgerArray.length-1] 
							== map.get(imageArray.get(hamburgerArray.length-1))) {
				clear();
				return;
			} else if (hamburgerArray.length != imageArray.size()) {
				gameOver();
			}
			break;
		}
		
		checkMakeHamburger();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
