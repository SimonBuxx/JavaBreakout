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
	 * @param x  X-Koordinate des Balls
	 * @param y  Y-Koordinate des Balls
	 * @param dx X-Richtung des Balls (nicht normiert)
	 * @param dy Y-Richtung des Balls (nicht normiert)
	 */
	public Ball(double x, double y, double dx, double dy) {
		pos = new Position(x, y);
		direction = new Vector2D(dx, dy);
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
}
