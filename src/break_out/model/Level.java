package break_out.model;

import break_out.Constants;

/**
 * This object contains information about the running game
 * 
 * @author dmlux
 * @author I. Schumacher, modified by Simon Buchholz, Julia Sikorski
 */
public class Level extends Thread {

    /**
     * The game to which the level belongs 
     */
    private Game game;
	 
    /**
   	 * The number of the level
   	 */
    private int levelnr;
       
    /**
	 * The score of the level
	 */
    private int score;
     
    /**
     * Flag that shows if the ball was started
     */
    private boolean ballWasStarted = true;

    /**
     * Neues Objekt der Klasse Ball deklarieren
     */
    private Ball ball;
    
    /**
     * Neues Objekt der Klasse Paddle deklarieren
     */
    private Paddle paddle;
        
    /**
     * Der Konstruktor instanziiert einen neuen Level:
     * @param game Das zugehoerige Game-Objekt
     * @param levelnr Die Nummer des zu instanziierenden Levels
     * @param score Der bisher erreichte Scorewert
     */
    public Level(Game game, int levelnr, int score) {
    	this.game = game;
    	this.levelnr = levelnr;
    	this.score = score;
    	// ball instanziieren
    	this.ball = new Ball(Constants.SCREEN_WIDTH / 2 - Constants.BALL_DIAMETER / 2, 
    			Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER - Constants.PADDLE_HEIGHT, -5, 5);
    	// paddle instanziieren
    	this.paddle = new Paddle();
        loadLevelData(levelnr);
    }
    
    /**
     * Setzt ballWasStarted auf true, d.h. der Ball "startet", 
     * weil so die bedingten Anweisungen in der while-Schleife der run-Methode ausgefuehrt werden.
     */
    public void startBall() {
        ballWasStarted = true;
    }

    /**
     * Setzt ballWasStarted auf false, d.h. der Ball "pausiert", weil so die bedingten Anweisungen in der while-Schleife 
     * der run-Methode nicht ausgefuehrt werden.
     */
    public void stopBall() {
        ballWasStarted = false;
    }
    
    /**
     * Liefert den booleschen Wert der Variablen ballWasStarted
     * @return ballWasStarted True, wenn sich der Ball bewegt, sonst false
     */
    public boolean ballWasStarted() {
        return ballWasStarted;
    }

    /**
     * Liefert das Ballobjekt
     * @return ball
     */
    public Ball getBall() {
    	return ball;
    }
    
    /**
     * Liefert das Paddleobjekt
     * @return paddle
     */
    public Paddle getPaddle() {
    	return paddle;
    }

    /**
     * This method is the thread logic.
     */
    public void run() {
    		// update view, d. h. veranlasse das Neuzeichnen des Spielfeldes
    		game.notifyObservers();
    		
    		while (true) {
	            // if ballWasStarted is true (Spiel soll ablaufen, d.h. der Ball soll sich bewegen)
	            if (ballWasStarted) {
	                
	            	// hier das Abprallverhalten des Balls an den vier Waenden implementieren
	            	/**
	            	 * <= bzw. >= benutzt man, da bei dx bzw. dy > 1 der Ball mehrere Pixel im selben Schritt zuruecklegt.
	            	 * Daher kann es sein, dass der Ball den Bildschirmrand nicht pixelgenau trifft.
	            	 */
	            	/**
	            	 * Kollisionsabfrage
	            	 */
	            	ball.reactOnBorder();
	            	
	            	
	                // hier die neue BallPosition ermitteln
	            	/**
	            	 * Aktualisierung der Position des Balls
	            	 */
	            	ball.updatePosition();
	            	
	                               
	                // update view
	                game.notifyObservers();    
	                
	            }
	            // pause thread by a few millis
	            try {
	                Thread.sleep(4);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
    		}   
    }
    
    
    /**
    * Zugriff auf die der Levelnummer zugeordnete JSON-Datei
    * @param levelnr Die Nummer X fuer die LevelX.json Datei
    */
    private void loadLevelData(int levelnr) {
    		
    }
    
}
    


	
