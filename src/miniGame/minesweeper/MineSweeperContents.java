package miniGame.minesweeper;

import java.awt.Color;
import java.awt.Font;

public interface MineSweeperContents {
	public final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	public final Color GAME_BUTTON_COLOR = new Color(219, 219, 219);
	public final Color BOOM_BACKGROUND_COLOR = Color.red;

	public final Color COLOR0 = BACKGROUND_COLOR;
	public final Color COLOR1 = new Color(39, 135, 65); // 초록
	public final Color COLOR2 = Color.blue; // 파랑
	public final Color COLOR3 = Color.red; // 빨강 
	public final Color COLOR4 = new Color(171, 154, 46); // 노랑
	public final Color COLOR5 = new Color(204, 104, 22); // 주황
	public final Color COLOR6 = new Color(17, 0, 171); // 남색
	public final Color COLOR7 = new Color(82, 21, 148); // 보라
	public final Color COLOR8 = new Color(232, 107, 222); // 분홍
	
	public final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
}
