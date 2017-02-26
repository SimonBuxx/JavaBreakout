package break_out.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import break_out.model.Game;
import break_out.view.Field;
import break_out.view.StartScreen;
import break_out.view.View;

/**
 * The controller takes care of the input events and reacts on those events by
 * manipulating the view and updates the model.
 * 
 * @author dmlux, modified by I. Schumacher, Simon Buchholz, Julia Sikorski
 * 
 */
public class Controller implements ActionListener, KeyListener {

    /**
     * The game as model that is connected to this controller
     */
    private Game game;

    /**
     * The view that is connected to this controller
     */
    private View view;

    
    /**
     * The constructor expects a view to construct itself.
     * 
     * @param view The view that is connected to this controller
     */
    public Controller(View view) {
        this.view = view;

        // Assigning the listeners
        assignActionListener();
        assignKeyListener();
    }

    /**
     * The controller gets all buttons out of the view with this method and adds
     * this controller as an action listener. Every time the user pushed a
     * button the action listener (this controller) gets an action event.
     */
    private void assignActionListener() {
        // Get the start screen to add this controller as action
        // listener to the buttons.
        view.getStartScreen().addActionListenerToStartButton(this);
        view.getStartScreen().addActionListenerToQuitButton(this);
    }
    
    /**
     * With this method the controller adds himself as a KeyListener.
     * Every time the user pushed a key the KeyListener (this controller) gets an KeyEvent.
     */
    private void assignKeyListener() {
        // Get the field to add this controller as KeyListener
        view.getField().addKeyListener(this);
    }

    /**
     * Every time the user pushed a button the action listener (this controller) 
     * gets an action event and starts this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Getting the start screen
        StartScreen startScreen = view.getStartScreen();
        
        // The 'start game' button was pressed
        if (startScreen.getStartButton().equals(e.getSource())) {
            // Getting the players name from the input
            String playersName = startScreen.getPlayersName();
            playersName = playersName.trim();
            if (playersName.length() < 1) {
                // If the players name is empty it is invalid
                startScreen.showError("Der Name ist ungültig");
            } else {    
            	// Neues Game-Objekt erzeugen und dem View-Objekt bekanntgeben
    	        game = new Game(this);
    	        view.setGame(game);
            }
        }

        // Der Spieler beendet das Spiel
        else if (startScreen.getQuitButton().equals(e.getSource())) {
            System.exit(0);
        }
    }
 
    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyPressed(KeyEvent e) {
    	// Falls die Leertaste gedrueckt wurde...
    	if (e.getKeyCode() == KeyEvent.VK_SPACE) {
    		// Pruefen, ob der Ball gestartet wurde
    		if (game.getLevel().ballWasStarted()) {
    			game.getLevel().stopBall(); // Ball stoppen
    		} else {
    			game.getLevel().startBall(); // Ball starten
    		}
    	}
    	// Falls die linke Pfeiltaste gedrueckt wurde...
    	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    		game.getLevel().getPaddle().setStatus(-1);
    	}
    	// Falls die rechte Pfeiltaste gedrueckt wurde...
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    		game.getLevel().getPaddle().setStatus(1);
    	}
    	
    	// Falls die Escape-Taste gedrueckt wurde...
    	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    		toStartScreen();
    		game.getLevel().setBeendet(true);
    	}
    }

    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyReleased(KeyEvent e) {
    	// Falls die linke Pfeiltaste losgelassen wurde...
    	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    		game.getLevel().getPaddle().setStatus(0); // Ball anhalten
    	}
    	// Falls die rechte Pfeiltaste losgelassen wurde...
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    		game.getLevel().getPaddle().setStatus(0); // Ball anhalten
    	}
    }

    
    /**
     * Mit dieser Methode erfolgt das Umschalten vom Spielfeld zum StartScreen
     */
    public void toStartScreen() {
    	view.showScreen(StartScreen.class.getName());
    	view.getStartScreen().requestFocusInWindow();
    }
    
    /**
     * Mit dieser Methode erfolgt das Umschalten zum Spielfeld
     */
    public void toPlayground() {
    	view.showScreen(Field.class.getName());
    	view.getField().requestFocusInWindow();
    }
   
}
