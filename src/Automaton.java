/**
 * @Author- Hilla Bartov
 *          Ofir Cohen
 **/

import java.awt.List;
import java.util.*;

public class Automaton {
    private Creature[][] automaton;
    private ArrayList<Creature> creatures;
    private int sickCounter;


    public Automaton() {
        this.automaton = new Creature[200][200];
        this.creatures = new ArrayList<>();
        this.sickCounter = 1;

    }

    public Creature[][] getAutomaton() {
        return this.automaton;
    }

    public void setAutomaton(Creature[][] automaton) {
        this.automaton = automaton;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }

    public void setCell(int row, int column, Creature c) {
        this.automaton[row][column] = c;
    }

    public void addCreature(Creature c) {
        this.creatures.add(c);
    }

    public boolean isCellEmpty(int row, int column) {
        if (this.automaton[row][column] == null) {
            return true;
        }
        return false;
    }


    public void incSickCounter() {
        this.sickCounter++;
    }

    public int getSickCounter() {
        return sickCounter;
    }

    /**
     * Setting each creature neighbors.
     */
    public void setNeighbors() {

        for (Creature creature : this.creatures) {
            ArrayList<Creature> n = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                n.add(j, null);
            }
            //check if neighbor 0-7 has been checked already
            boolean[] isChecked = new boolean[8];
            for (int j = 0; j < 8; j++) {
                isChecked[j] = false;
            }
            //counting neighbors
            int i = 0;
            //start looking from the warp-around neighbors
            //Top row
            if (creature.getI() == 0) {
                //top left corner
                if (creature.getJ() == 0) {
                    if (!this.isCellEmpty(199, 199)) {
                        // neighbor = new Creature(199, 199, p);
                        n.set(0, this.automaton[199][199]);
                        i++;
                    }
                    isChecked[0] = true;
                    if (!this.isCellEmpty(199, 0)) {
                        // neighbor = new Creature(199, 0, p);
                        n.set(1, this.automaton[199][0]);
                        i++;
                    }
                    isChecked[1] = true;
                    if (!this.isCellEmpty(199, 1)) {
                        //  neighbor = new Creature(199, 1, p);
                        n.set(2, this.automaton[199][1]);
                        i++;
                    }
                    isChecked[2] = true;
                    if (!this.isCellEmpty(1, 199)) {
                        //neighbor = new Creature(1, 199, p);
                        n.set(6, this.automaton[1][199]);
                        i++;
                    }
                    isChecked[6] = true;
                    if (!this.isCellEmpty(0, 199)) {
                        n.set(7, this.automaton[0][199]);
                        i++;
                    }
                    isChecked[7] = true;
                    //Top right corner
                } else if (creature.getJ() == 199) {
                    if (!this.isCellEmpty(199, 198)) {
                        n.set(0, this.automaton[199][199]);
                        i++;
                    }
                    isChecked[0] = true;
                    if (!this.isCellEmpty(199, 199)) {
                        n.set(1, this.automaton[199][199]);
                        i++;
                    }
                    isChecked[1] = true;
                    if (!this.isCellEmpty(199, 0)) {
                        n.set(2, this.automaton[199][0]);
                        i++;
                    }
                    isChecked[2] = true;
                    if (!this.isCellEmpty(0, 0)) {
                        n.set(3, this.automaton[0][0]);
                        i++;
                    }
                    isChecked[3] = true;
                    if (!this.isCellEmpty(1, 0)) {
                        n.set(4, this.automaton[1][0]);
                        i++;
                    }
                    isChecked[4] = true;
                    //top row inner
                } else {
                    if (!this.isCellEmpty(199, creature.getJ() - 1)) {
                        n.set(0, this.automaton[199][creature.getJ() - 1]);
                        i++;
                    }
                    isChecked[0] = true;
                    if (!this.isCellEmpty(199, creature.getJ())) {
                        n.set(1, this.automaton[199][creature.getJ()]);
                        i++;
                    }
                    isChecked[1] = true;
                    if (!this.isCellEmpty(199, creature.getJ() + 1)) {
                        n.set(2, this.automaton[199][creature.getJ() + 1]);
                        i++;
                    }
                    isChecked[2] = true;
                }
                //right Col
            } else if (creature.getJ() == 199) {
                //Buttom right corner
                if (creature.getI() == 199) {
                    if (!this.isCellEmpty(198, 0)) {
                        n.set(2, this.automaton[198][0]);
                        i++;
                    }
                    isChecked[2] = true;
                    if (!this.isCellEmpty(199, 0)) {
                        n.set(3, this.automaton[199][0]);
                        i++;
                    }
                    isChecked[3] = true;
                    if (!this.isCellEmpty(0, 0)) {
                        n.set(4, this.automaton[0][0]);
                        i++;
                    }
                    isChecked[4] = true;
                    if (!this.isCellEmpty(0, 199)) {
                        n.set(5, this.automaton[0][199]);
                        i++;
                    }
                    isChecked[5] = true;
                    if (!this.isCellEmpty(0, 198)) {
                        n.set(6, this.automaton[0][198]);
                        i++;
                    }
                    isChecked[6] = true;

                    //right col inner
                } else {
                    if (!this.isCellEmpty(creature.getI() - 1, 0)) {
                        n.set(2, this.automaton[creature.getI() - 1][0]);
                        i++;
                    }
                    isChecked[2] = true;
                    if (!this.isCellEmpty(creature.getI(), 0)) {
                        n.set(3, this.automaton[creature.getI()][0]);
                        i++;
                    }
                    isChecked[3] = true;
                    if (!this.isCellEmpty(creature.getI() + 1, 0)) {
                        n.set(4, this.automaton[creature.getI() + 1][0]);
                        i++;
                    }
                    isChecked[4] = true;
                }
                //Buttom Row
            } else if (creature.getI() == 199) {
                //Buttom left corner
                if (creature.getJ() == 0) {
                    if (!this.isCellEmpty(198, 198)) {
                        n.set(0, this.automaton[198][198]);
                        i++;
                    }
                    isChecked[0] = true;
                    if (!this.isCellEmpty(0, 199)) {
                        n.set(4, this.automaton[0][199]);
                        i++;
                    }
                    isChecked[4] = true;
                    if (!this.isCellEmpty(0, 0)) {
                        n.set(5, this.automaton[0][0]);
                        i++;
                    }
                    isChecked[5] = true;
                    if (!this.isCellEmpty(0, 199)) {
                        n.set(6, this.automaton[0][199]);
                        i++;
                    }
                    isChecked[6] = true;
                    if (!this.isCellEmpty(199, 199)) {
                        n.set(7, this.automaton[199][199]);
                        i++;
                    }
                    isChecked[7] = true;

                    //Buttom row inner
                } else {
                    if (!this.isCellEmpty(0, creature.getJ() + 1)) {
                        n.set(4, this.automaton[0][creature.getJ() + 1]);
                        i++;
                    }
                    isChecked[4] = true;
                    if (!this.isCellEmpty(0, creature.getJ())) {
                        n.set(5, this.automaton[0][creature.getJ()]);
                        i++;
                    }
                    isChecked[5] = true;
                    if (!this.isCellEmpty(0, creature.getJ() - 1)) {
                        n.set(6, this.automaton[0][creature.getJ() - 1]);
                        i++;
                    }
                    isChecked[6] = true;
                }
                //Left Col
            } else if (creature.getJ() == 0) {
                if (!this.isCellEmpty(creature.getI() - 1, 199)) {
                    n.set(0, this.automaton[creature.getI() - 1][199]);
                    i++;
                }
                isChecked[0] = true;
                if (!this.isCellEmpty(creature.getI() + 1, 199)) {
                    n.set(6, this.automaton[creature.getI() + 1][199]);
                    i++;
                }
                isChecked[6] = true;
                if (!this.isCellEmpty(creature.getI(), 199)) {
                    n.set(7, this.automaton[creature.getI()][199]);
                    i++;
                }
                isChecked[7] = true;
            }
            //inner matrix cells, and the neighbors of the margins that have'nt been checked
            if (!isChecked[0] && !this.isCellEmpty(creature.getI() - 1, creature.getJ() - 1)) {
                n.set(0, this.automaton[creature.getI() - 1][creature.getJ() - 1]);
                i++;
            }
            if (!isChecked[1] && !this.isCellEmpty(creature.getI() - 1, creature.getJ())) {
                n.set(1, this.automaton[creature.getI() - 1][creature.getJ()]);
                i++;
            }
            if (!isChecked[2] && !this.isCellEmpty(creature.getI() - 1, creature.getJ() + 1)) {
                n.set(2, this.automaton[creature.getI() - 1][creature.getJ() + 1]);
                i++;
            }
            if (!isChecked[3] && !this.isCellEmpty(creature.getI(), creature.getJ() + 1)) {
                n.set(3, this.automaton[creature.getI()][creature.getJ() + 1]);
                i++;
            }
            if (!isChecked[4] && !this.isCellEmpty(creature.getI() + 1, creature.getJ() + 1)) {
                n.set(4, this.automaton[creature.getI() + 1][creature.getJ() + 1]);
                i++;
            }
            if (!isChecked[5] && !this.isCellEmpty(creature.getI() + 1, creature.getJ())) {
                n.set(5, this.automaton[creature.getI() + 1][creature.getJ()]);
                i++;
            }
            if (!isChecked[6] && !this.isCellEmpty(creature.getI() + 1, creature.getJ() - 1)) {
                n.set(6, this.automaton[creature.getI() + 1][creature.getJ() - 1]);
                i++;
            }
            if (!isChecked[7] && !this.isCellEmpty(creature.getI(), creature.getJ() - 1)) {
                n.set(7, this.automaton[creature.getI()][creature.getJ() - 1]);
                i++;
            }
            //set the neighbors for each creature
            creature.setNeighbors(n);
            if (i == 8) {
                creature.setBlocked(true);//creature has the max neighbors so it's blocked
            }
        }
    }
}

