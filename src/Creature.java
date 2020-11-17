/**
 * @Author- Hilla Bartov
 *          Ofir Cohen
 **/
import java.util.ArrayList;
import java.util.Random;

public class Creature {

    private int i, j;
    private boolean isSick, isBlocked;
    private float infectionProb;
    private ArrayList<Creature> neighbors;


    public Creature(int row, int column, float p) {
        this.i = row;
        this.j = column;
        this.isSick = false;
        isBlocked = false;
        this.infectionProb = p;
        this.neighbors = new ArrayList<>(9);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }


    public boolean isSick() {
        return isSick;
    }

    public float getInfectionProb() {
        return infectionProb;
    }

    public ArrayList<Creature> getNeighbors() {
        return neighbors;
    }


    public boolean isBlocked() {
        return isBlocked;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }


    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setInfectionProb(float infectionProb) {
        this.infectionProb = infectionProb;
    }

    public void setNeighbors(ArrayList<Creature> n) {
        this.neighbors = n;
    }

    /**
     * Get creature new location by the flowing rules: 0-up left,1-up,2-up right,3-right,
     * 4-down right,5-down,6-down left 7-left,8-stay in place.
     *
     * @return the new available location.
     */
    public int newLocation() {
        if (this.isBlocked) {
            return 8;
        }
        Random rand = new Random();
        int location = rand.nextInt(8);
        //check that the new location is not already taken, or stay in place one
        while (location != 8 && !this.neighbors.isEmpty() && this.neighbors.get(location) != null) {
            location = rand.nextInt(8);
        }
        return location;
    }
}
