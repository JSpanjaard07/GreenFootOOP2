import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

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
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
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
    
    public void layTrailOfEggs(int distance) {
        int nrStepsTaken = 0;               
        while ( nrStepsTaken < distance ) { 
            layEgg();
            move();
            nrStepsTaken++; 
        }
    }
    
    public void faceDirection(int direction) {
        while (getDirection() != direction) {
            if (direction < NORTH || direction > WEST) {
                break;
            }
            turnRight();
        }
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            move();
            int x = getX();
            int y = getY();
            String loc = x + "-" + y;
            System.out.println(loc);
        }
    }
    
    public void walkToWorldEdgeX40( ){
        int i = 40;
        while(i <= 0 ){
            if (borderAhead()) {
                turn180();
            }
            move();
            i--;
        }
    }
    
    public void gobacktostartandfaceback () {
        turn180();
        walkToWorldEdgePrintingCoordinates();
        turn180();
    }
    
    public void pickupgrain() {
        while( ! borderAhead() ){
            move();
            if (onGrain() ) {
                int x = getX();
                int y = getY();
                String loc = x + "-" + y;
                System.out.println(loc);
            }
        }
    }
    
    public void walkToWorldEdgeClimbingOverFences() {
        while( ! borderAhead() ){
            move();
            if ( fenceAhead() ){
                turnLeft();
                move();
                turnRight();
                move();
                move();
                turnRight();
                move();
                turnLeft();
                }
            if ( onNest() ) {
                if (!onEgg() ) {
                        layEgg();
                }
            }
        }
    }
    
    public void walkwhilefertingnests() {
        while (!borderAhead() ) {
            move();
            if (onNest() ) {
                if (!onEgg() ) {
                    layEgg();
                }
            }
        }    
    }
    
    public void stepbackwards() {
        turn180();
        move();
        turn180();
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }  
    
    public void jumpOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();        
    }
    
    public void turn180() {
        turnRight();
        turnRight();
    }
    
    public boolean grainAhead2() {
        move();
        if (grainAhead()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void gotoEgg() {
        while (!onEgg()) {
            move();
        }
    }
    
    public void walkAroundFencedArea() {
        if (fenceAhead()) {
            turnRight();
            move();
        }
    }
    
    public int countEggsInRow() {
        int x = 0;
        while (!borderAhead()) {
            if (onEgg()) {
                x++;
            }
            move();
        }
        if (onEgg()) {
                x++;
        }
        System.out.println(x);
        return x;
    }
    
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
    
    public void countEggsInWorld() {
        int amountOfEggs = 0;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            amountOfEggs = amountOfEggs + countEggsInRow();
        }
        System.out.println(amountOfEggs);
    }
    
    public void monumentOfEggs() {
            for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            int a = i;
            while (a >= 0) {
                layEgg();
                move();
                a--;
            }
        }
    }
    
    public void heftyMonument() {
        int amountOfEggs = 1;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            layTrailOfEggs(amountOfEggs);
            amountOfEggs*=2;
        }
    }
    
    public void eggPyramid() {
        int amountOfEggs = 1;
        int math = getWorld().getWidth();
        int loc = math / 2;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(loc, i);
            faceDirection(EAST);
            layTrailOfEggs(amountOfEggs);
            amountOfEggs+=2;
            loc--;
            if (loc == 0) {
                break;
            }
        }
    }
    
    public void randomMovement() {
        randomDirection();
        walkToWorldEdgeX40();
    }
    
    
}
