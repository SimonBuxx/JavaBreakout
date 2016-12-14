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
	 * Konstruktor zur Instanziierung des Vektors mit einem Positionsvektor und einem Richtungsvektor
	 * @param p Positionsvektor
	 * @param r Richtungsvektor
	 */
	public Vector2D(Vector2D p, Vector2D r) {
		this.dx = Math.abs(p.getDx() - r.getDx());
		this.dy = Math.abs(p.getDy() - r.getDy());
	}
	
	/**
	 * Normiert den Vektor und multipliziert ihn mit der Geschwindigkeit des Balls
	 */
	public void rescale() {
		this.dx = this.dx / this.dy;
		this.dx = this.dx * Constants.BALL_SPEED;
		this.dy = Constants.BALL_SPEED;
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
