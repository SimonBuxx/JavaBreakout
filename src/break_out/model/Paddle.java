package break_out.model;

import break_out.Constants;

/**
 * Diese Klasse repraesentiert das Paddle
 * @author Simon Buchholz, 661562
 * @author Julia Sikorski, 666402
 */
public class Paddle {
	/**
	 * Position des Paddles// 4.5
	 */
	private Position pos;
	
	/**
	 * Status des Paddles
	 */
	private Integer status = 0;
	
	/**
	 * Konstruktor fuer das Paddle
	 */
	public Paddle() {
		// Instanziieren des Positionsobjektes
		pos = new Position(Constants.SCREEN_WIDTH / 2 - Constants.PADDLE_WIDTH / 2, 
    			Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT);
	}
	
	/**
	 * Getter fuer die Position des Paddles
	 * @return Position des Paddles als Position-Objekt
	 */
	public Position getPosition() {
		return pos;
	}
	
	/**
	 * Position des Paddles aktualisieren
	 */
	public void updatePosition() {
		// links
		if (status == -1) {
			if (pos.getX() >= Constants.DX_MOVEMENT) { // Falls das Paddle nicht ganz links ist
				pos.setX(pos.getX() - Constants.DX_MOVEMENT);
			} else {
				pos.setX(0);
			}
		}
		// rechts
		if (status == 1) {
			if (pos.getX() + Constants.PADDLE_WIDTH <= Constants.SCREEN_WIDTH - Constants.DX_MOVEMENT) {
				pos.setX(pos.getX() + Constants.DX_MOVEMENT);
			} else {
				pos.setX(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH);
			}
		}
	}
	
	/**
	 * Getter fuer den Status des Paddles
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * Setter fuer den Status des Paddles
	 * @param s Status
	 */
	public void setStatus(Integer s) {
		status = s;
	}
}
