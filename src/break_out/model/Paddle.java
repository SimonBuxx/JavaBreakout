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
	 * Status des Paddles
	 * Der Status gibt an, in welche Richtung das Paddle
	 * sich bewegt. Er ist 0, wenn das Paddle steht,
	 * -1, wenn es nach links bewegt wird
	 * und 1, wenn es nach rechts bewegt wird.
	 */

	private Integer status = 0;
	
	/**
	 * Konstruktor fuer das Paddle
	 */
	public Paddle() {
		// Instanziieren des Positionsobjektes
		// X-Koordinate: Halbe Bildschirmbreite minus halbe Paddlebreite, so dass Paddle mittig
		// Y-Koordinate: Bildschirmhoehe minus Paddlehoehe
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
		// Paddle soll nach links laufen
		if (status == -1) {
			if (pos.getX() >= Constants.DX_MOVEMENT) { // Falls das Paddle nicht ganz links ist
				pos.setX(pos.getX() - Constants.DX_MOVEMENT);
			} else {
				pos.setX(0);
			}
		}
		// Paddle soll nach rechts laufen
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
	 * @return Richtung, in die sich das Paddle bewegt
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * Setter fuer den Status des Paddles
	 * @param s Richtung, in die sich das Paddle bewegen soll
	 */
	public void setStatus(Integer s) {
		status = s;
	}
}
