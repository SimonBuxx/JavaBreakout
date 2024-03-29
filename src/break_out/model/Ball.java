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
	 * Feldkoordinaten aus dem letzten Durchlauf
	 */
	private int lastFieldX = 0;
	private int lastFieldY = 0;
	
	/**
	 * Konstruktor fuer den Ball
	 * Der Konstruktor plaziert den Ball in der Bildschirmmitte auf dem Paddle
	 */
	public Ball() {
		// X-Koordinate: Halber Bildschirm minus halber Ballumfang
		// Y-Koordinate: Bildschirmhoehe minus Ballumfang minus Paddlehoehe, Ball liegt unten auf dem Paddle
		pos = new Position(Constants.SCREEN_WIDTH / 2 - Constants.BALL_DIAMETER / 2,
				Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER - Constants.PADDLE_HEIGHT);
		direction = new Vector2D(-5, 5); // Startrichtung angeben
		direction.rescale(); // Startrichtung skalieren, so dass Laenge 1
	}
	
	/**
	 * Getter fuer die Position
	 * @return Position des Balls als Position-Objekt
	 */
	public Position getPosition() {
		return pos;
	}
	
	/**
	 * Getter fuer die Richtung
	 * @return Richtung des Balls als Vektor
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
	 * @return Ball ist mit dem Paddle kollidiert
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
	 * @param paddle Paddle-Objekt, an dem Kollision erfolgen soll
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
	 * Prueft, ob der Ball einen Stein beruehrt und implementiert Abprallverhalten vom Stein
	 * @param stones Steinarray mit Werten 0 - 3 je nach Leben des Steins
	 * @return Koordinate des Steins als Array, {-1, -1} falls keine Kollision
	 */
	public int[] hitsStone(int[][] stones) {
		/*
		 * Berechnen der X- und Y-Koordinaten des Balls: Je nach Bewegungsrichtung wird immer die Ecke des Balls als Referenzpunkt
		 * gewaehlt, in die sich der Ball bewegt. Z.B. Ball bewegt sich nach oben rechts: obere rechte Ecke des Balls wird
		 * als "Messpunkt" fuer die Grid-Koordinaten genommen.
		 */
		
		// X-Koordinate des Balls berechnen
		int gridX = 0;
		if (direction.getDx() > 0) { // Ball bewegt sich nach rechts
			gridX = (int) Math.floor((pos.getX() + Constants.BALL_DIAMETER) / (Constants.SCREEN_WIDTH / Constants.SQUARES_X));
		} else { // Ball bewegt sich nach links / keine X-Bewegung
			gridX = (int) (Math.ceil(pos.getX() / (Constants.SCREEN_WIDTH / Constants.SQUARES_X)) - 1);
		}
		if (gridX < 0) { // Sicherstellen, dass Werte zulaessig sind
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
		
		int[] stonePos = {-1, -1}; // Array fuer die Rueckgabe der Koordinaten
		
		if (stones[gridY][gridX] > 0) {  // Falls der Ball mit einem Stein kollidiert ist
			stonePos[0] = gridX;
			stonePos[1] = gridY; // Positionen fuer die Rueckgabe aendern
			
			/*
			 * Komplett ueberarbeitete Kollisionsabfrage: Letzte Koordinate, indem der Ball sich befindet
			 * wird gespeichert und mit der neuen Koordinate verglichen. Hat sich X oder Y geaendert,
			 * muss nach links/rechts bzw. oben/unten abgeprallt werden. (Richtung ist egal, da in beiden
			 * Faellen jeweils Dx bzw. Dy negiert wird)
			 */
			
			if (lastFieldX != gridX) { // Falls sich die X-Koordinate geaendert hat --> Kollision links oder rechts
				direction.setDx(-direction.getDx()); // X-Richtung umkehren
			} else if (lastFieldY != gridY) { // Falls sich die Y-Koordinate geaendert hat --> Kol. oben oder unten
				direction.setDy(-direction.getDy()); // Y-Richtung umkehren
			}
		}
		
		/*
		 * Koordinaten des Feldes abspeichern, in dem sich der Ball gerade befindet
		 */
		
		lastFieldX = gridX;
		lastFieldY = gridY;
		
		return stonePos; // Position des getroffenen Steins zurueckgeben
	}
	
	/**
	 * Kollisionsabfrage fuer den unteren Rand
	 * @return true, falls der Ball den Boden beruehrt oder sich darunter befindet, sonst falls
	 */
	public boolean hitsGround() {
		return (pos.getY() + Constants.BALL_DIAMETER >= Constants.SCREEN_HEIGHT);
	}
}
