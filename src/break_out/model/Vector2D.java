package break_out.model;

import break_out.Constants;

/**
 * Klasse Vector2D stellt einen zweidimensionalen Vektor dar
 * @author Simon Buchholz, 661562
 * @author Julia Sikorski, 666402
 */
public class Vector2D {
	/**
	 * X-Komponente des Vektors
	 */
	private double dx;
	/**
	 * Y-Komponente des Vektors
	 */
	private double dy;
	
	/**
	 * Konstruktor zur Instanziierung des Vektors mit zwei Zahlen
	 * @param dx X-Komponente des Vektors
	 * @param dy Y-Komponente des Vektors
	 */
	public Vector2D(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Konstruktor zur Instanziierung des Vektors mit einer Ausgangsposition und einer Zielposition
	 * @param z Zielposition
	 * @param p Ausgangsposition
	 */
	public Vector2D(Position z, Position p) {
		this.dx = z.getX() - p.getX();
		this.dy = z.getY() - p.getY();
	}
	
	/**
	 * Normiert den Vektor und multipliziert ihn mit der Geschwindigkeit des Balls
	 */
	public void rescale() {
		double length = Math.sqrt(this.dx * this.dx + this.dy * this.dy);
		this.dx = (this.dx / length) * Constants.BALL_SPEED;
		this.dy = (this.dy / length) * Constants.BALL_SPEED;
	}
	
	/**
	 * Setter fuer dx
	 * @param dx X-Komponente des Vektors
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}
	
	/**
	 * Getter fuer dx
	 * @return dx X-Komponente des Vektors
	 */
	public double getDx() {
		return this.dx;
	}
	
	/**
	 * Setter fuer dy
	 * @param dy Y-Komponente des Vektors
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Getter fuer dy
	 * @return dy Y-Komponente des Vektors
	 */
	public double getDy() {
		return this.dy;
	}
}
