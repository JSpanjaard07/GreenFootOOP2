import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{
    /* ATTRIBUTE DECLARATIONS: */
    private int myNrOfStepsTaken;
    private int NrOfEggs;
           
    public MyDodo() {
        super( EAST );
        /* INITIALISATION OF ATTRIBUTES: */
        myNrOfStepsTaken = 0;
    }

    /* METHODS OF THE CLASS: */

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
            myNrOfStepsTaken++;
            ScoreBoard();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
    * Places all the Egg objects in the world in a list.
    * 
    * @return List of Egg objects in the world
    */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    
    
    /**
     * Places every step made in a list
     */
    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }
    

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEgss( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
    
    /**
     * Face a specific direction based on input, east, west, north or south
     */
    public void faceDirection(int direction) {
        while (getDirection() != direction) {
            if (direction < NORTH || direction > WEST) {
                break;
            }
            turnRight();
        }
    }
    
    
    /**
     * Uses facedirection and move to go towards a location without teleporting
     */
    public void gotoLocation(int coordX, int coordY) {
        while (getX() < coordX) {
            faceDirection(EAST);
            move();
        }
        while (getX() > coordX) {
            faceDirection(WEST);
            move();
        }
        while (getY() < coordY) {
            faceDirection(SOUTH);
            move();
        }
        while (getY() > coordY) {
            faceDirection(NORTH);
            move();
        }
    }
    
    /**
     * Function to return the closest egg
     */
    public Egg ReturnClosestEgg() {
        List<Egg> eggs = getWorld().getObjects(Egg.class); 
        Egg closestEgg = null;
        int closestDistance = Integer.MAX_VALUE;
 
        for (Egg egg : eggs) {
            int xDistance = Math.abs(egg.getX() - getX());
            int yDistance = Math.abs(egg.getY() - getY());
            int totalDistance = xDistance + yDistance;
            
            if (totalDistance < closestDistance) {
                closestEgg = egg;
                closestDistance = totalDistance;
            }
        } 
        return closestEgg;  
    }
    
    /**
     * Uses go to location to go towards the location of an egg
     */
    public void GoToEgg(Egg egg) {
        int eggX = egg.getX();
        int eggY = egg.getY();
        gotoLocation(eggX, eggY);
    }
    
    /**
     * The final race, attempts to get as many eggs within 40 steps
     */
    public void DodoRaceV1() {
        Egg egg = ReturnClosestEgg();
 
        while (egg != null) {
            
            GoToEgg(egg);
            if (Mauritius.MAXSTEPS <= myNrOfStepsTaken) {
                break;
            }
            NrOfEggs += egg.getValue();
            pickUpEgg();
            egg = ReturnClosestEgg();
        }
    }
    
    /**
     * Scoreboard is necessary for the scoreboard to show up and update while the dodo is racing
     */
    public void ScoreBoard() {
        Mauritius world = (Mauritius) getWorld();
        world.updateScore(Mauritius.MAXSTEPS - myNrOfStepsTaken, NrOfEggs);
    }

}
