package break_out.model;

import break_out.Constants;

/**
 * Diese Klasse repraesentiert das Paddle
 * @author Simon Buchholz, 661562
 * @author Julia Sikorski, 666402
 */
public class Paddle {
	/**
	 * Position des Paddles
	 */
	private Position pos;
	
	/**
	 * Konstruktor fuer das Paddle
	 */
	public Paddle() {
		// Instanziieren des Positionsobjektes
		pos = new Position(Constants.SCREEN_WIDTH / 2 - Constants.PADDLE_WIDTH / 2, 
    			Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT - 1);
	}
	
	/**
	 * Getter fuer die Position des Paddles
	 * @return
	 */
	public Position getPosition() {
		return pos;
	}
	
}
