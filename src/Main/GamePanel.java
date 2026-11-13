package Main;
import java.awt.*;
import javax.swing.JPanel;
import inputs.*;
import static Main.Game.GAME_WIDTH;
import static Main.Game.GAME_HEIGHT;


public class GamePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	
	public GamePanel(Game game) {
		
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyBoardInputs(this));
		addMouseListener(new MouseInputs(this));
		addMouseMotionListener(new MouseInputs(this));
		setFocusable(true);
		
        
	}	
	

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	
	public Game getGame() {
		return game;
	}

	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.render(g);
		
		}


		
	}

