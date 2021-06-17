package miniGame.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.events.MouseEvent;

import miniGame.MiniGame;

class MyButton extends JButton {
	int row, col;
	boolean isClicked;
	String imagePath = "";

	public MyButton(String name) {
		super(name);
	}

	public MyButton(int r, int c) {
		this(null, r, c);
	}

	public MyButton(String msg, int r, int c) {
		super(msg);
		row = r;
		col = c;
	}
}

public class MineSweeper extends JPanel implements MiniGame, MouseListener, MineSweeperContents {
	private MainPanel_MineSweeper parents;
	private MyButton[][] button;
	private int[][] mineArray;

	private final int GAME_ROW;
	private final int GAME_COL;
	private final int TOTALMINE;
	private final int BOOM = 9;
	
	private ImageIcon imageIcon;
	private Image image;
	private Image changeImage;
	private int flag;
	private MyButton flagButton;
	private int totalClick;
	private int totalFlag;
	private boolean gameOver;
	private boolean success;
	private Timer timer;
	protected MyButton timerButton;
	private boolean timerRun;
	private int time;

	MineSweeper(MainPanel_MineSweeper parents, int row, int col, int totalMine) {
		super();
		this.parents = parents;
		this.GAME_ROW = row;
		this.GAME_COL = col;
		this.TOTALMINE = totalMine;
		this.flag = totalMine;

		mineArray();
		add(mainPanel());

		// 정답 참조
		for (int i = 0; i < GAME_ROW; ++i) {
			System.out.println(Arrays.toString(mineArray[i]));
		}


		setTimer();

	}

	private void playgame() {
		
	}

	public JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(setMineSweeper(), BorderLayout.CENTER);
		panel.add(menuPanel(), BorderLayout.NORTH);
		
		panel.setPreferredSize(new Dimension(ROW, COL));
		return panel;
	}

	public JPanel menuPanel() {
		JPanel panel = new JPanel();

		MyButton replay = new MyButton("다시 시작");
		replay.setBackground(Color.LIGHT_GRAY);
		replay.setFont(FONT);
		replay.addMouseListener(this);

		timerButton = new MyButton("0초");
		timerButton.setBackground(Color.LIGHT_GRAY);
		timerButton.setPreferredSize(new Dimension(100, 35));
		timerButton.setFont(FONT);
		
		flagButton = new MyButton("" + flag);
		image = new ImageIcon(MineSweeper.class.getResource("image/깃발.png")).getImage();
		changeImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(changeImage);
		flagButton.setIcon(imageIcon);
		
		flagButton.setBackground(Color.LIGHT_GRAY);
		flagButton.setPreferredSize(new Dimension(100, 35));
		flagButton.setFont(FONT);
		
		MyButton back = new MyButton("이전으로");
		back.setBackground(Color.LIGHT_GRAY);
		back.setFont(FONT);
		back.addMouseListener(this);
		
		panel.add(flagButton);
		panel.add(timerButton);
		panel.add(replay);
		panel.add(back);

		return panel;
	}
	

	public JPanel setMineSweeper() {
		JPanel panel = new JPanel();

		panel.setBackground(BACKGROUND_COLOR);

		panel.setLayout(new GridLayout(GAME_ROW, GAME_COL));
		panel.setPreferredSize(new Dimension(700,700));
		setMyButton(panel);

		return panel;
	}

	public void setMyButton(JPanel panel) {
		button = new MyButton[GAME_ROW][GAME_COL];
		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				button[i][j] = new MyButton(i, j);
				button[i][j].setBackground(BACKGROUND_COLOR);
				button[i][j].setFont(BUTTON_FONT);
				button[i][j].setBorderPainted(true);
				button[i][j].addMouseListener(this);
				panel.add(button[i][j]);
			}
		}
	}

	public void mineArray() {
		mineArray = new int[GAME_ROW][GAME_COL];

		for (int i = 0; i < TOTALMINE; ++i) {
			int a = (int) (Math.random() * GAME_ROW);
			int b = (int) (Math.random() * GAME_COL);

			if (mineArray[a][b] == BOOM) {
				--i;
				continue;
			}
			mineArray[a][b] = BOOM;
		}

		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (mineArray[i][j] == BOOM)
					setArrayExceptMine(mineArray, i, j);
			}

		}

	}

	public void setArrayExceptMine(int[][] arrays, int row, int col) {
		// 폭탄 제외하고 숫자 배정
		int startR = row - 1 < 0 ? 0 : row - 1;
		int endR = row + 1 == GAME_ROW ? GAME_ROW - 1 : row + 1;
		int startC = col - 1 < 0 ? 0 : col - 1;
		int endC = col + 1 == GAME_COL ? GAME_COL - 1 : col + 1;

		for (int a = startR; a <= endR; ++a) {
			for (int b = startC; b <= endC; ++b) {
				if (arrays[a][b] == BOOM) {
					continue;
				}
				++arrays[a][b];
			}
		}
	}

	public void clickToZero(MyButton[][] myButton, int row, int col) {
		// 눌렀는데 0 번일때 주변 뜨게하기
//		myButton[row][col].setText(String.valueOf(mineArray[row][col]));

		if ("0".equals(myButton[row][col].getText())) {

			int startR = row - 1 < 0 ? 0 : row - 1;
			int endR = row + 1 == GAME_ROW ? GAME_ROW - 1 : row + 1;
			int startC = col - 1 < 0 ? 0 : col - 1;
			int endC = col + 1 == GAME_COL ? GAME_COL - 1 : col + 1;

			myButton[row][col].isClicked = true;

			for (int i = startR; i <= endR; ++i) {
				for (int j = startC; j <= endC; ++j) {
					clickTheButtonColor(button, i, j, false);

					if (!myButton[i][j].isClicked) {
						clickToZero(myButton, i, j);
					}
				}

			}
		}
	}

	public void clickTheButtonColor(MyButton[][] myButton, int row, int col) {
		clickTheButtonColor(myButton, row, col, true);
	}

	public void clickTheButtonColor(MyButton[][] myButton, int row, int col, boolean isGameover) {
		// 숫자별 색상 설정
		myButton[row][col].setText(String.valueOf(mineArray[row][col]));
		myButton[row][col].setIcon(null);

		switch (myButton[row][col].getText()) {
		case "0":
			myButton[row][col].setForeground(GAME_BUTTON_COLOR);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "1":
			myButton[row][col].setForeground(COLOR1);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "2":
			myButton[row][col].setForeground(COLOR2);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "3":
			myButton[row][col].setForeground(COLOR3);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "4":
			myButton[row][col].setForeground(COLOR4);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "5":
			myButton[row][col].setForeground(COLOR5);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "6":
			myButton[row][col].setForeground(COLOR6);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "7":
			myButton[row][col].setForeground(COLOR7);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "8":
			myButton[row][col].setForeground(COLOR8);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		}
		if (isGameover) {
			if (mineArray[row][col] == BOOM) {
				myButton[row][col].imagePath = "image\\폭탄.png";
				addButtonImage(button[row][col]);
				myButton[row][col].setText(null);
				myButton[row][col].setBackground(BOOM_BACKGROUND_COLOR);
			}
		}
	}

	public void addButtonImage(MyButton myButton) {
		// 버튼에 이미지 붙이기
		if (myButton.imagePath.isEmpty())
			return;
		image = new ImageIcon(MineSweeper.class.getResource(myButton.imagePath)).getImage();
		switch(GAME_ROW) {
		case 9 :
			changeImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			break;
		case 16 :
			changeImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			break;
		case 30 :
			changeImage = image.getScaledInstance(30, 20, Image.SCALE_SMOOTH);
			break;
		}
		
		imageIcon = new ImageIcon(changeImage);
		myButton.setIcon(imageIcon);

	}

	public void clickTheBOOM(MyButton[][] myButton, int row, int col) {
		// 폭탄 눌렀을 때

		if (myButton[row][col].getText() == null) {
			for (int i = 0; i < GAME_ROW; ++i) {
				for (int j = 0; j < GAME_COL; ++j) {
					clickTheButtonColor(button, i, j);
					myButton[i][j].removeMouseListener(this);
				}
			}
			gameOver = true;
			success = false;
		}
	}

	public void findAllMine(MyButton[][] myButton) {
		// 지뢰를 모두 찾았을때 게임 끝 클릭 다 하고 + 깃발까지 꽂아야함

		// 클릭한 갯수 총 합 = 폭탄이아닌것 버튼 총 합
		totalClick = 0;

		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (myButton[i][j].getText() != null && !myButton[i][j].getText().isBlank()) {
					++totalClick;
				}
			}
		}

		// 오른쪽마우스로 깃발표시 총합 = 폭탄 총합
		totalFlag = 0;
		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (mineArray[i][j] == BOOM && myButton[i][j].imagePath.equals("image\\깃발.png")) {
					++totalFlag;
				}
			}
		}

		if (totalClick == GAME_ROW * GAME_COL - TOTALMINE && totalFlag == TOTALMINE) {
			for (int i = 0; i < GAME_ROW; ++i) {
				for (int j = 0; j < GAME_COL; ++j) {
					myButton[i][j].removeMouseListener(this);
				}
			}
			gameOver = true;
			success = true;
		}

	}

	public void GameOver() {
		if (gameOver) {
			if (success) {
				timer.stop();
				JOptionPane.showMessageDialog(getRootPane(), "축하합니다! 폭탄을 모두 찾으셨습니다!\n총 소요 시간 : " + time + "초");
				return;
			}
			
			timer.stop();
			JOptionPane.showMessageDialog(getRootPane(), "---GAME OVER---\n폭탄을 클릭하셨습니다.");
		}
	}

	public void setTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				if (timerRun) {
//					return;
//				}
				timerRun = true;
				++time;
				timerButton.setText(String.valueOf(time) + "초");

			}
		});

	}

	public void setCountFlag(MyButton[][] myButton) {
		//플래그 갯수 세기
		flag = TOTALMINE;
		for (int i = 0; i < GAME_ROW; ++i) {
			for(int j = 0; j < GAME_COL; ++j)
				
			try {	
				if(myButton[i][j].getText().isEmpty()
						&& myButton[i][j].imagePath.equals("image\\깃발.png")) {
					--flag;
				} 
			}catch(NullPointerException e) {
				return;
			}
				
		}
		flagButton.setText("" + flag);
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		MyButton click = (MyButton) e.getSource();
//		System.out.println(clickButton.row + "행" + clickButton.col + "열");
		int row = click.row;
		int col = click.col;
		click.isClicked = true;

		if (e.isMetaDown()) { // 오른쪽마우스 클릭할때
			switch (click.imagePath) {
			case "image\\깃발.png": // 오른쪽마우스 두번클릭
				click.imagePath = "image\\물음표.png";
				break;
			case "image\\물음표.png": // 오른쪽마우스 세번클릭
				click.imagePath = "image\\공백.png";
				break;
			default: // 그 외 모든 오른쪽마우스 클릭시
				click.imagePath = "image\\깃발.png";
				break;
			}
			// switch문으로 정해진 이미지파일을 버튼에 추가
			addButtonImage(click);
			findAllMine(button);
			GameOver();
			setCountFlag(button);
			return;
		}

		// 여기서부터는 왼쪽마우스 클릭 설정

		switch (click.getText()) {
		case "다시 시작":
			parents.retry();
			return;
			
		case "이전으로":
			parents.addNewPanel(new LevelChoice(parents));
			return;
		}

		timer.start();

		click.setText(String.valueOf(mineArray[row][col]));

		clickTheButtonColor(button, row, col);

		clickToZero(button, row, col);

		findAllMine(button);

		clickTheBOOM(button, row, col);

		GameOver();
		
		setCountFlag(button);
		
		click.removeMouseListener(this);

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	}

}
