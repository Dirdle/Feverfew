/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * 
 * This class will do a lot of the heavy lifting, I expect
 */
import glyphs.Pin;
import glyphs.Scaffold;
import glyphs.Sprite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JPanel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class Board extends JPanel implements Runnable, Testing, ActionListener {
    // right
    ClassLoader cl;
    
    private Thread animator;
    private final static int DELAY = 30;
    
    // This enum tracks the game's internal state, allowing control over
    // what is happening
    private State state;
    
    // The player character, currently singular
    private PlayerCharacter player;
    
    // keep track of objects to be painted
    // iteration over this array will paint each object without needing many 
    // lines of painting code
    private ArrayList<Pin> screenPins;
    private int pinCount;
    
    //graphics context
    private Graphics2D g2d;    
    
    // Combat object. When this exists, a battle is taking place.
    private Combat currentFight;
    
    private boolean gameActive;
    private boolean userReady;
    
    // Extractors for XML docs
    private ActionExtractor actionGetter;
    private EnemyExtractor enemyGetter;
    
    public KeyAdapter myAdapter;
    public ActionListener myListener;
    
// <editor-fold defaultstate="collapsed" desc=" Specific components ">
    //Default all these to Null
    private JButton combatStartButton = null;

// </editor-fold>
    public Board(ClassLoader loader){
               
        // Setting some JPanel constants relevant to the desired setup
        this.setBackground(BACKGROUND);
        this.setDoubleBuffered(true);
        this.setPreferredSize(DIM);
        this.setFocusable(true);        
        
        cl = loader;
        
        //ImageIcon ii = new ImageIcon(cl.getResource("blank.png"));
        //block = ii.getImage();
        //Deprecated code, kept for reference for importing images
        
        this.gameStart();
    }
    
    private void gameStart(){
        // Initialises the game
        myAdapter = new MyKeyAdapter();
        this.addKeyListener((KeyListener) myAdapter);
//        myListener = new MyActionAdapter();
        
        LOGGER.log("Extracting XML...");
        
        try {            
            actionGetter = this.getActionGetter();
            enemyGetter  = this.getEnemyGetter();            
        } 
        catch (JAXBException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.log("XML extraction failed.");
            ex.printStackTrace();
            System.exit(1);
        }        
        
        
        LOGGER.log("Initialising player character");
        player = new PlayerCharacter(actionGetter.getListOfActions());
        
        // Set the state and log the change
        state = State.STARTINGCOMBAT;
        LOGGER.stateSet(state);
        
        gameActive = true;
        userReady = false;
        
        screenPins = new ArrayList(MAXSCREENOBJECTS);
        pinCount = 0;
    }
    
    
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    
    
    /**
     * <p>
     * Painter method for Feverfew. Cycles over objects in the screenPins list.
     * </p>
     * @param g Graphics object that the painter will use
     * 
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g2d = (Graphics2D)g;
        
        
        for (int n = 0; n < pinCount; n++ ){
            // Iterate over objects to paint
            
            
            Pin pin = screenPins.get(n);
            
            if (pin.isDying()){
                screenPins.remove(pin);                
                pinCount--;
            }            
            
            if (pin.isVisible()){
                
                
                // Strictly speaking it is possible to avoid instanceof calls 
                // here by 'merely' adding a whole lot of otherwise-useless
                // code to Sprite, Scaffold and maybe Pin too. 
                if (pin instanceof Sprite){
                    // TODO sprite painting code
                }
                
                else if (pin instanceof Scaffold){
                    
                    Scaffold paintable = (Scaffold) pin;
                    
                    // Create a translation to apply to the shape
                    AffineTransform trans = AffineTransform.
                            getTranslateInstance(paintable.getX(),
                                                 paintable.getY());
                    
                    if (paintable.isFilled()){
                        // Actions to take if the shape is filled
                        g2d.setPaint(paintable.getFillColor());
                        g2d.fill(trans.createTransformedShape(
                                paintable.getShape()));
                    }
                    // Possible to be both stroked and filled, or neither
                    if (paintable.isStroked()){
                        // Actions to take if the shape is stroked
                        g2d.setStroke(paintable.getPen());
                        g2d.setColor(paintable.getStrokeColor());
                        g2d.draw(trans.createTransformedShape(
                                paintable.getShape()));
                    }
                }
            }
        }
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public void cycle(){
        if (gameActive == true){
            // TODO: create code to 
            /**
             * 1) Display a button until the user clicks it. 
             * 1a) Later add another button for exiting
             * 2) Create a new Combat when the user clicks the button
             * 3) Then cycle over the combat until it's done
             * 4) goto 1
             */
            
            switch (state){
                case STARTINGCOMBAT:
                    // Code to create a button
                    combatStartButton = new JButton("Let's go!");
                    // The command that the button sends when used
                    combatStartButton.setActionCommand("startCombat");
                    // The switchboard that listens for said command
                    combatStartButton.addActionListener(this);
                    // Place appropriately, or, try to
                    combatStartButton.setLocation(COMBATSTARTBUTTONLOCATION);
                    
                    // Add the button to the board
                    this.add(combatStartButton);
                    // And then switch to waiting for input
                    this.state = State.WAITINGINPUT;
                    LOGGER.stateSet(state);
                    break;
                
                case CREATINGCOMBAT:                    
                    ArrayList<PlayerCharacter> players = new ArrayList<>(0);
                    players.add(player);
                    // For now we're gonna just use 1 random enemy. TODO improve
                    this.currentFight 
                            = new Combat(selectOpponents(enemyGetter, 1),
                                        players);
                    
                    // Forget about the button
                    this.remove(combatStartButton);                    
                    combatStartButton = null;
                    
                    this.state = State.RUNNINGCOMBAT;
                    LOGGER.stateSet(state);  
                    break;
                    
                case RUNNINGCOMBAT:
                    this.currentFight.update();
                    break;
                case WAITINGINPUT:
                    if (userReady) {
                        Board.this.state = State.CREATINGCOMBAT;
                        LOGGER.stateSet(state);
                    }
                    break;
            }

            
        }
    }
    
    @Override
    public void run(){
        
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        
        while (true){
            // TODO possible termination condition
            
            cycle();
            repaint();
            
            // This code keeps the timing working
            timeDiff = System.currentTimeMillis() - beforeTime;
            
            sleep = DELAY - timeDiff;

            if (sleep < 0){
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.log("Interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
    
    private void addScreenObject(Pin p){
        //TODO add code to determine where in the array, exactly , the new
        // pin should be (i.e. what the image is in front of/behind, since 
        // recently-drawn elements overwrite less recently-drawn ones).
        try {
            screenPins.add(pinCount, p);
        }
        catch (IndexOutOfBoundsException e){
            LOGGER.log("Index out of bounds");
            System.exit(0);
        }
        pinCount += 1;      
    }
    
    private void addScreenObject(Pin[] pins){
        for(Pin item : pins){
            this.addScreenObject(item);
        }
    }
    
// <editor-fold defaultstate="collapsed" desc=" Extractor getters for XML ">
    public ActionExtractor getActionGetter() throws JAXBException {
        // Creates a new ActionExtractor object that contains all the Actions
        // in the XML actions file.
        ActionExtractor actionExtractor;
        JAXBContext context = JAXBContext.newInstance(ActionExtractor.class);
        Unmarshaller unm = context.createUnmarshaller();        
        
        LOGGER.log("Extracting actions from " + ACTIONLOCATION);
        actionExtractor = (ActionExtractor) unm.unmarshal(
                cl.getResourceAsStream(ACTIONLOCATION));
        LOGGER.log("Found "
                + actionExtractor.getListOfActions().size()
                + " actions.");
        
        return actionExtractor;
    }
    
    public EnemyExtractor getEnemyGetter() throws JAXBException {
        // Creates a new EnemyExtractor object that contains all the Enemies
        // in the XML file
        // Is it unwise to load so much stuff at once? Probably.
        EnemyExtractor enemyExtractor;
        JAXBContext context = JAXBContext.newInstance(EnemyExtractor.class);        
        Unmarshaller unm = context.createUnmarshaller();        
        
        
        LOGGER.log("Extracting enemies from " + ENEMIESLOCATION);
        enemyExtractor = (EnemyExtractor) unm.unmarshal(
                cl.getResourceAsStream(ENEMIESLOCATION));
        LOGGER.log("Found "
                + enemyExtractor.getListOfEnemies().size()
                + " enemies.");
        
        return enemyExtractor;
    }

// </editor-fold>
    
    public Shape stringToShape(String str) {
        // Attempts at code to convert a string to a shape
        // TODO add varargs for other arguments that allow passing of font, size
        Font f = getFont().deriveFont(Font.BOLD, 25);
        GlyphVector v = f.createGlyphVector(
                getFontMetrics(f).getFontRenderContext(), str);
        Shape shape = v.getOutline();
        setPreferredSize(new Dimension(30,30));
        return shape;
    }    
    
    public PlayerCharacter testPlayer(){
        //A testing method for creating a player character for the alpha stages
        // of development
        return null;
    }
    
    private ArrayList<Enemy> selectOpponents(EnemyExtractor fetcher, int n){
        // This method might later become part of the 'area' class, so
        // each area can provide its own enemies
        ArrayList<Enemy> shuffledEnemies = fetcher.getListOfEnemies();
        Collections.shuffle(shuffledEnemies);
        ArrayList<Enemy> output 
                = new ArrayList<>(shuffledEnemies.subList(0, n));
        return output;
    }

    @Override
    public void actionPerformed(ActionEvent acEve) {
            if ("startCombat".equals(acEve.getActionCommand())){
                LOGGER.log("Button pressed");
                userReady = true;                
            }
    }
    
    /**
     * Inner class: combat. Better to begin this chain inside the board,
     * than mess around with passing board to round as an argument (as it did
     * previously)
     */    
    private class Combat {
        // Combat should probably be a subclass of Board, since it's going to be 
        // running other stuff essentially.
        // Or maybe it should just be a method? Augh
        // So what should happen?
        /**
         * 1) Combat begins. Other class(es) decide what enemies should be 
         * fought, and which party member(s) have been selected to fight them.     
         * 2) Play proceeds in Rounds. The Active Agent in a round selects an 
         * action from their pool; they have freedom to change target at will 
         * (but action-target should be 'locked in' at choice)      
         * 3) The combat ends when either the enemies are all defeated or the 
         *      player character(s) are.
         */
        

        ArrayList<Enemy> enemyList;
        ArrayList<PlayerCharacter> playerList;
        Round currentRound;
        
        public Combat(ArrayList<Enemy> enemies, 
                ArrayList<PlayerCharacter> players){
            LOGGER.log("Starting a combat.");
            this.playerList = players;
            this.enemyList  = enemies;
        }
               
        public void update(){
            if (this.currentRound == null) {
                // No round exists yet
                // TODO add a pause between rounds
                LOGGER.log("Null Round detected, creating new Round");
                // or something else the actbar object can self-alter
                
                //Creating the actbar here has, for now, an advantage:
                // the else-if is only reached if the actbar ended
                currentRound = new Round(ACTIONCOUNT, player);              
                currentRound.setBoard(Board.this);
                
                // Add all the pins in the round to the board

                addScreenObject(currentRound.pins.toArray(new Pin[0]));                
                LOGGER.log("Adding " 
                        + currentRound.pins.toArray(new Pin[0]).length 
                        + " screen objects; total screen objects: " 
                        + pinCount);                
            }            
            else if(currentRound.active) {
                // round is running
                currentRound.update();
            }            
            else{
                // round is done, time to delete it
                currentRound = null;
            }            
        }
        
        public void keyPressed(KeyEvent press){
            // Code to interpret key presses in context of a combat
            int key = press.getKeyCode();
            
            switch (key) {
                case KeyEvent.VK_SPACE:
                    // Space is used to stop an actbar
                    if (currentRound.active){
                        LOGGER.log("Stop command: stopping round.");
                        currentRound.stop();
                    }
                    else {
                        LOGGER.log("Stop command: round already inactive");
                    }
            }
        }
        
    }
    
    /**
     * Inner class: switchboard for input events from the keyboard
     */
    private class MyKeyAdapter extends KeyAdapter {
        
        @Override
        public void keyReleased(KeyEvent release){
            // TODO add anything that happens on keyrelease rather than keypress
        }
        
        @Override
        public void keyPressed (KeyEvent press){
            
            /**
             * Key bindings at present: 
             * Space: stop bar
             * 
             * TODO create bindings.txt and code to read it
             */
            
            
            switch (state){
                case RUNNINGCOMBAT:
                    currentFight.keyPressed(press);
                    break;
                default:
                    LOGGER.log("Key does nothing until game has a state.");
                    break;
            }
        }

    }

}
