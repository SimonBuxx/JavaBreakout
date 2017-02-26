package break_out.model;

import break_out.Constants;
import break_out.controller.JSONReader;

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
     * Anzahl Steine im Level
     */
    private int stoneCount = 0;
    
    /**
     * Flag that shows if the ball was started
     */
    private boolean ballWasStarted = false;

    /**
     * Neues Objekt der Klasse Ball deklarieren
     */
    private Ball ball;
    
    /**
     * Neues Objekt der Klasse Paddle deklarieren
     */
    private Paddle paddle;
    
    /**
     * Neues Array mit den Steinpositionen deklarieren
     */
    private int[][] stones = new int[Constants.SQUARES_Y][Constants.SQUARES_X];
        
    /**
     * Wird auf true gesetzt um das Level zu beenden
     */
    private boolean beendet = false;
    
    /**
     * Anzahl Leben fuer das Level
     */
    private int leben = 0;
    
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
    	this.ball = new Ball();
    	// paddle instanziieren
    	this.paddle = new Paddle();
        loadLevelData(levelnr);
        
        // Anzahl der Steine berechnen
    	for (int i = 0; i < stones.length; i++) {
        	for (int j = 0; j < stones[i].length; j++) {
        		if (stones[i][j] > 0) {
        			stoneCount++;
        		}
        	}
        }
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
     * @return ballWasStarted: true, wenn sich der Ball bewegt, sonst false
     */
    public boolean ballWasStarted() {
        return ballWasStarted;
    }

    /**
     * Liefert das Ballobjekt
     * @return Ball-Objekt
     */
    public Ball getBall() {
    	return ball;
    }
    
    /**
     * Liefert das Paddleobjekt
     * @return Paddle-Objekt
     */
    public Paddle getPaddle() {
    	return paddle;
    }

    /**
     * Liefert das Array mit den Steinpositionen
     * @return Steinarray als 2D-Array
     */
    public int[][] getStones() {
    	return stones;
    }
    
    /**
     * Gibt den aktuelle Punktestand zurueck
     * @return aktueller Punktestand als InteSystem.out.println(stoneCount);ger
     */
    public int getScore() {
    	return score;
    }
    
    /**
     * Gibt die verbleibenden Leben zurueck
     * @return verbleibende Leben als Integer
     */
    public int getLives() {
    	return leben;
    }
    
    public void setBeendet(boolean b) {
    	beendet = b;
    }
    
    /**
     * This method is the thread logic.
     */
    public void run() {
    		// update view, d. h. veranlasse das Neuzeichnen des Spielfeldes
    		game.notifyObservers();
    		
    		while (!beendet) {
	            // if ballWasStarted is true (Spiel soll ablaufen, d.h. der Ball soll sich bewegen)
	            if (ballWasStarted) {
	                
	            	if (ball.hitsGround()) {
	            		leben--;
	            		if (leben >= 1) {
	            			stopBall(); // Ball stoppen
	            			ball = new Ball();
	            			paddle = new Paddle();
	            		} else {
	            			setBeendet(true);
	            			game.getController().toStartScreen();
	            		}
	            	}
	            	
	            	// Kollisionsabfragen
	            	ball.reactOnBorder();
	            	ball.reflectOnPaddle(paddle);
	            	
	            	// Aktualisierung der Positionen
	            	ball.updatePosition();
	            	paddle.updatePosition();
	            	
	            	updateStonesAndScore();
	                               
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
    	JSONReader json = new JSONReader("res/Level" + levelnr + ".json");
    	this.levelnr = levelnr;
    	this.leben = json.getLifeCounter();
    	stones = json.getStones2DArray();
    }
    
    /**
     * Aktualisiert die Steine und den Score
     */
    public void updateStonesAndScore() {
    	int[] stonePos = ball.hitsStone(stones);
    	if (stonePos[0] >= 0 && stonePos[1] >= 0) {
    		score++;
    		stones[stonePos[1]][stonePos[0]]--;
    		if (stones[stonePos[1]][stonePos[0]] <= 0) {
    			stones[stonePos[1]][stonePos[0]] = 0;
    			stoneCount--;
    			// Pruefen, ob das Level beendet wurde
    			if (levelCleared()) {
    				setBeendet(true);
    				game.createLevel(levelnr + 1, score);
    			}
    		}
    	}
    }
    
    /**
     * Prueft, ob alle Steine entfernt wurden
     * @return true, falls alle Steine weg, sonst false
     */
    public boolean levelCleared() {
    	
    	return (stoneCount <= 0);
    }
}
    


	
