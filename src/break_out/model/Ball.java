package break_out.model;

import break_out.Constants;

/**
 * Diese Klasse repraesentiert den Ball
 * @author Simon Buchholz, 661562
 * @author Julia Sikorski, 666402
 */
public class Ball {
	/**
	 * Position des Balls
	 */
	private Position pos;
	
	/**
	 * Richtung des Balls
	 */
	private Vector2D direction;
	
	/**
	 * Konstruktor fuer den Ball
	 */
	public Ball() {
		pos = new Position(Constants.SCREEN_WIDTH / 2 - Constants.BALL_DIAMETER / 2, 
				Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER - Constants.PADDLE_HEIGHT);
		direction = new Vector2D(-5, 5);
		direction.rescale();
	}
	
	/**
	 * Getter fuer die Position
	 * @return pos
	 */
	public Position getPosition() {
		return pos;
	}
	
	/**
	 * Getter fuer die Richtung
	 * @return direction
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * Aktualisiert die Position des Balls
	 */
	public void updatePosition() {
		pos.setX(pos.getX() + direction.getDx()); // x-Position um dx veraendern
    	pos.setY(pos.getY() + direction.getDy()); // y-Position um dy veraendern
	}
	
	/**
	 * Prueft, ob der Ball mit einer Wand kollidiert ist
	 */
	public void reactOnBorder() {
		if (pos.getX() <= 0) { // Linke Wand ueberpruefen
    		direction.setDx(-direction.getDx());
    	} else if (pos.getX() >= Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) { // Rechte Wand ueberpruefen
    		direction.setDx(-direction.getDx());
    	} else if (pos.getY() >= Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER) { // Untere Wand ueberpruefen
    		direction.setDy(-direction.getDy());
    	} else if (pos.getY() <= 0) { // Obere Wand ueberpruefen
    		direction.setDy(-direction.getDy());
    	}
	}
	
	/**
	 * Prueft, ob der Ball mit dem Paddle kollidiert ist
	 * @param p Paddle-Objekt
	 * @return Ball ist kollidiert
	 */
	public boolean hitsPaddle(Paddle p) {
		return (this.pos.getX() + Constants.BALL_DIAMETER >= p.getPosition().getX() // Ball ist rechts von der linken Seite des Paddles
				// Ball ist links von der rechten Seite des Paddles
				&& this.pos.getX() <= p.getPosition().getX() + Constants.PADDLE_WIDTH
				// Unterseite des Balls ist unterhalb der oberen Kante des Paddles
				&& this.pos.getY() + Constants.BALL_DIAMETER >= Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT);
	} 
	
	/**
	 * Implementiert das Abprallverhalten des Balls bei Kollision mit dem Paddle
	 * @param paddle
	 */
	public void reflectOnPaddle(Paddle paddle) {
		// Tiefergelegten Mittelpunkt als Positionsobjekt erzeugen
		Position mittelpunkt = new Position(paddle.getPosition().getX() + Constants.PADDLE_WIDTH/2, Constants.SCREEN_HEIGHT + 30);
		// Falls der Ball mit dem Paddle kollidiert...
		if (hitsPaddle(paddle)) {
			// Neuer Richtungsvektor
			Vector2D neu = new Vector2D(new Position(this.pos.getX() + Constants.BALL_DIAMETER / 2, this.pos.getY() + Constants.BALL_DIAMETER / 2), mittelpunkt);
			neu.rescale(); // Vektor skalieren
			this.direction = neu; // Neue Richtung als Richtung des Balls verwenden
		}
	}
	
	/**
	 * Prueft, ob der Ball einen Stein beruehrt
	 * @param stones Steinarray
	 * @return Ball ist kollidiert
	 */
	public int[] hitsStone(int[][] stones) {
		// X-Koordinate des Balls berechnen
		int gridX = 0;
		if (direction.getDx() > 0) {
			gridX = (int) Math.floor((pos.getX() + Constants.BALL_DIAMETER) / (Constants.SCREEN_WIDTH / Constants.SQUARES_X));
		} else {
			gridX = (int) (Math.ceil(pos.getX() / (Constants.SCREEN_WIDTH / Constants.SQUARES_X)) - 1);
		}
		if (gridX < 0) {
			gridX = 0;
		} else if (gridX > Constants.SQUARES_X - 1) {
			gridX = Constants.SQUARES_X - 1;
		}
		
		// Y-Koordinate berechnen
		int gridY = 0;
		if (direction.getDy() > 0) {
			gridY = (int) Math.floor((pos.getY() + Constants.BALL_DIAMETER) / (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y));
		} else {
			gridY = (int) (Math.ceil(pos.getY() / (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y)) - 1);
		}
		if (gridY < 0) {
			gridY = 0;
		} else if (gridY > Constants.SQUARES_Y - 1) {
			gridY = Constants.SQUARES_Y - 1;
		}
		
		int[] stonePos = {-1, -1};
		
		// Vom Stein abprallen
		if (stones[gridY][gridX] > 0) { // Falls der Ball einen Stein beruehrt
			stonePos[0] = gridX;
			stonePos[1] = gridY; // Positionen fuer die Rueckgabe aendern
			// Falls der Ball von unten auf den Stein trifft
			if (pos.getY() >= gridY * (Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)) {
				direction.setDy(-direction.getDy()); // Y-Richtung umkehren
			}
			
			// Falls der Ball von oben auf den Stein trifft
			if (pos.getY() + Constants.BALL_DIAMETER < gridY * (Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)) {
				direction.setDy(-direction.getDy()); // Y-Richtung umkehren
			}
			
			// Falls der Ball von links auf den Stein trifft
			if(pos.getX() + Constants.BALL_DIAMETER < gridX * (Constants.SCREEN_WIDTH/Constants.SQUARES_X)) {
				direction.setDx(-direction.getDx()); // X-Richtung umkehren
			}
			
			// Falls der Ball von rechts  auf den Stein trifft
			if(pos.getX() >= gridX * (Constants.SCREEN_WIDTH/Constants.SQUARES_X)) {
				direction.setDx(-direction.getDx()); // X-Richtung umkehren
			}
		}
		return stonePos;
	}
}
