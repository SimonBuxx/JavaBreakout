package break_out.model;

import java.util.List;
import java.util.ArrayList;

import break_out.controller.Controller;
import break_out.view.View;

/**
 * This object contains information about the game (the model in MVC)
 * 
 * @author dmlux, modified by I. Schumacher
 * 
 */
public class Game{

    /**
	 * A list of observer objects
	 */
	private List<View> observers = new ArrayList<View>();

	/**
     * The controller of the game
     */
    private Controller controller;
	
    /**
   	 * The current level
   	 */
    private Level level;
    
    /**
     * The first levelnumber
     */
    private int firstLevel = 1;
    
    /**
     * The last levelnumber
     */
    private int maxLevel = 2;  
       
    /**
	 * The total score of the game
	 */
    private int score = 0;
    
       
    /**
     * Der Konstruktor instanziiert ein neues Game-Objekt
     * @param controller Der zugeordnete Controller (siehe MVC)
     */
    public Game(Controller controller) {
        this.controller = controller;
        createLevel(firstLevel, 0);
    }

    
    /**
     * The methods of the observer pattern
     */
    public void addObserver(View observer) {
		observers.add(observer);
	}

	public void removeObserver(View observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (View observer : observers)
			observer.modelChanged(this);
	}

	
	/**
	 * Getter for the Controller
	 * @return controller The controller of this game
	 */
     public Controller getController() {
    	 return controller;
     }
     
     /**
      * Getter for the current Level
      * @return level The current level of the game
      */
     public Level getLevel() {
    	 return level;
     }
     
    /**
     * Getter for the total score
     * @return score The current score of the game
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Creates the first or next Level
     * @param levelnr The number of the new level
     * @param score The current score of the game
     */
    public void createLevel(int levelnr, int score) {
    	this.score = score;
    	if (levelnr <= maxLevel) {
    		// Levelobjekt erzeugen
    		level = new Level(this, levelnr, score);
    		// ruft die run-Methode des Level-Objektes auf
        	level.start();
            // Spielfeld anzeigen
            controller.toPlayground();
    	}
    	else {
    		// Umschalten auf Startscreen bei Spielende
    		controller.toStartScreen();
    		
    	}
    	
    }
    
}
    


	
