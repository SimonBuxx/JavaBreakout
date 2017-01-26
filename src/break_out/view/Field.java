package break_out.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import break_out.Constants;
import net.miginfocom.swing.MigLayout;

/**
 * The field represents the board of the game. All components are on the board
 * 
 * @author dmlux, modified by iSchumacher, Simon Buchholz, Julia Sikorski
 * 
 */
public class Field extends JPanel {

	/**
	 * Automatic generated serial version UID
	 */
	private static final long serialVersionUID = 2434478741721823327L;

	/**
	 * The connected view object
	 */
	private View view;

	/**
	 * The background color
	 */
	private Color background;

	/**
	 * The constructor needs a view
	 * 
	 * @param view The view of this board
	 */
	public Field(View view) {
		super();

		this.view = view;
		this.background = new Color(177, 92, 107);

		setFocusable(true);

		// Load settings
		initialize();
	}

	/**
	 * Initializes the settings for the board
	 */
	private void initialize() {
		// creates a layout
		setLayout(new MigLayout("", "0[grow, fill]0", "0[grow, fill]0"));
	}

	/**
	 * Change the background color
	 * @param color The new color
	 */
	public void changeBackground(Color color) {
		background = color;
		repaint();
	}
	
	/**
	 * Die Methode wird zum Zeichnen / Neuzeichnen des Spielfeldes aufgerufen, dazu werden z. B. Hintergrundfarbe, Ballfarbe usw.
	 * angegeben und die einzelnen Methoden zum Zeichnen wie drawBall(g2) aufgerufen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double w = Constants.SCREEN_WIDTH;
		double h = Constants.SCREEN_HEIGHT;

		// Die Abmessungen des Spielfeldes bestimmen
		setPreferredSize(new Dimension((int) w, (int) h));
		setMaximumSize(new Dimension((int) w, (int) h));
		setMinimumSize(new Dimension((int) w, (int) h));

		// Die Schaerfe der Zeichnung festlegen
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Die Hintergrundfarbe setzen
		g2.setColor(background);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		// Die Ballfarbe setzen
		g2.setColor(new Color(200, 200, 200));
		
		// Den Ball zeichnen
		drawBall(g2);
		
		// Das Paddle zeichnen
		drawPaddle(g2);
		
		// Das Netz zeichnen
		drawGrid(g2);
		
		// Die Steine zeichnen
		drawStones(g2);
		
		// Den Punktestand zeichnen
		drawScore(g2);
	}


	/**
	 * Zeichnet den Ball, greift dabei ueber das ihm bekannte view-Objekt auf das zugehoerige Game-Objekt und 
	 * darueber auf das Level-Objekt zu, um dortige Methoden zu nutzen
	 * @param g2: Graphics2D-Objekt
	 */
	private void drawBall(Graphics2D g2) {
		g2.fillOval((int) view.getGame().getLevel().getBall().getPosition().getX(),
				(int) view.getGame().getLevel().getBall().getPosition().getY(),
				(int) (Constants.BALL_DIAMETER),
				(int) (Constants.BALL_DIAMETER));
	}
	
	/**
	 * Zeichnet das Paddle, greift dabei ueber das ihm bekannte view-Objekt auf das zugehoerige Game-Objekt und 
	 * darueber auf das Level-Objekt zu, um dortige Methoden zu nutzen
	 * @param g2: Graphics2D-Objekt
	 */
	private void drawPaddle(Graphics2D g2) {
		g2.fillRoundRect((int) view.getGame().getLevel().getPaddle().getPosition().getX(),
				(int) view.getGame().getLevel().getPaddle().getPosition().getY(),
				(int) (Constants.PADDLE_WIDTH),
				(int) (Constants.PADDLE_HEIGHT), 2, 2);
	}

	/**
	 * Zeichnet das Netz
	 * @param g2: Graphics2D-Objekt
	 */
	private void drawGrid(Graphics2D g2) {
		// Vertikale Linien
		for (int i = 0; i < Constants.SCREEN_WIDTH/Constants.SQUARES_X; i++) {
			g2.drawLine(i * (int) Constants.SCREEN_WIDTH/Constants.SQUARES_X, 0, i * (int) Constants.SCREEN_WIDTH/Constants.SQUARES_X, (int) Constants.SCREEN_HEIGHT);
		}
		// Horizontale Linien
		for (int j = 0; j < Constants.SCREEN_HEIGHT/Constants.SQUARES_Y; j++) {
			g2.drawLine(0, j * (int) Constants.SCREEN_HEIGHT/Constants.SQUARES_Y, (int) Constants.SCREEN_WIDTH, j * (int) Constants.SCREEN_HEIGHT/Constants.SQUARES_Y);
		}
	}
	
	/**
	 * Zeichnet die Steine
	 * @param g2: Graphics2D-Objekt
	 */
	private void drawStones(Graphics2D g2) {
		for (int i = 0; i < Constants.SQUARES_X; i++) {
			for (int j = 0; j < Constants.SQUARES_Y; j++) {
				if (view.getGame().getLevel().getStones()[j][i] > 0) { // Falls der Stein gezeichnet werden soll
					g2.setColor(new Color(200 - (view.getGame().getLevel().getStones()[j][i] - 1) * 30, 200 - 
							(view.getGame().getLevel().getStones()[j][i] - 1) * 30, 200 - (view.getGame().getLevel().getStones()[j][i] - 1) * 30));
					g2.fillRect(i * (int) Constants.SCREEN_WIDTH/Constants.SQUARES_X + 2, j * (int) Constants.SCREEN_HEIGHT/Constants.SQUARES_Y + 2, 
						(int) Constants.SCREEN_WIDTH/Constants.SQUARES_X - 3, (int) Constants.SCREEN_HEIGHT/Constants.SQUARES_Y - 3);
				}
			}
		}
	}
	
	/**
	 * Zeichnet den Score
	 * @param g2: Graphics2D-Objekt
	 */
	private void drawScore(Graphics2D g2) {
		g2.drawString("Score: " + view.getGame().getLevel().getScore(), 10, 20);
	}
}
