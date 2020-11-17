/**
 * @Author- Hilla Bartov
 *          Ofir Cohen
 **/

import biuoop.Sleeper;

import java.util.ArrayList;
import java.util.Random;

public class RunGenerations {
    private int generations;
    private int l;
    private int kFromL;
    private Automaton automaton;
    private int k;
    private LifePanel lifePanel;

    public RunGenerations(Automaton a, int g, int quarantine, int l, int kL, LifePanel panel) {
        this.automaton = a;
        this.generations = g;
        this.k = quarantine;
        this.l = l;
        this.kFromL = kL;
        this.lifePanel = panel;
    }

    public RunGenerations(Automaton a, int g, int quarantine, int l, LifePanel panel) {
        this.automaton = a;
        this.generations = g;
        this.k = quarantine;
        this.l = l;
        this.lifePanel = panel;
    }

    /**
     * Run all generations by the rules, the given values, and the initialized automaton from main.
     */
    public void run() {
        int countIterations = 0;
        for (int i = 0; i < generations; i++) {
            countIterations++;
            //when all creatures are sick, stop this run and move to the end screan
            if (this.automaton.getSickCounter() == this.automaton.getCreatures().size()) {
                break;
            }
            //show the graphics of the automaton current state
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor(100);
            this.lifePanel.show();
            //when asked that the quarantine level starts from L generation
            if (l != 0 && i == l) {
                this.k = kFromL;
            }
            this.automaton.setNeighbors();
            ArrayList<Creature> creatures = new ArrayList<>();
            for (Creature creature : automaton.getCreatures()) {
                //if k=8 the cell can't infect others
                //when the cell is sick, handle its neighbors contagion
                if (this.k != 8 && creature.isSick() && !creature.getNeighbors().isEmpty()) {
                    ArrayList<Creature> n = creature.getNeighbors();
                    //handle neighbor number 0(top left corner)
                    if (n.get(0) != null && !n.get(0).isSick()) {
                        Random rand = new Random();
                        int prob = rand.nextInt(100);
                        if (prob <= (int) (n.get(0).getInfectionProb() * 100)) {
                            n.get(0).setSick(true);
                            //count how many sick cells we have
                            this.automaton.incSickCounter();
                        }
                    }
                    //handle the other neighbors by the quarantine level
                    for (int j = (this.k + 1); j < 7; j++) {
                        if (n.get(j) != null && !n.get(j).isSick()) {
                            Random rand = new Random();
                            int prob = rand.nextInt(100);
                            if (prob <= (int) (n.get(j).getInfectionProb() * 100)) {
                                n.get(j).setSick(true);
                                this.automaton.incSickCounter();
                            }
                        }
                    }
                }
                boolean flag = true;
                int first = 0;
                //preventing cells from moving to the same location in each generation
                while (flag) {
                    int moveTo = creature.newLocation();
                    if (!creature.isBlocked() && moveTo != 8) {
                        //delete  the old location from matrix only one time
                        if (first == 0) {
                            this.automaton.setCell(creature.getI(), creature.getJ(), null);
                            first++;
                        }
                        Creature c = this.setNewLocation(creature, moveTo);
                        //when location is available, set the creature there
                        if (c != null) {
                            this.automaton.setCell(c.getI(), c.getJ(), c);
                            creatures.add(creature);
                            flag = false;//stop the loop
                        }else {
                            flag = true;
                        }
                        //when creature has 8 neighbors, or got 8 in the "move to" - stay in place and stop this loop.
                    } else {
                        creatures.add(creature);
                        flag = false;
                    }
                }
            }
            //set the relocated creatures in the automaton, and the changed automaton in the animation runner
            this.automaton.setCreatures(creatures);
            this.lifePanel.setAutomaton(this.automaton);
        }
        //show the sum up for this run, by the iterations until all dead\end of generations
        this.lifePanel.endScreen(countIterations);
    }

    /**
     * Trying to set new cell for the creature by the given random location number 0-7.
     *
     * @param creature is the cell that need to move.
     * @param moveTo   is the random given location.
     * @return the relocated creature if the "move to" cell is empty, and null otherwise.
     */
    public Creature setNewLocation(Creature creature, int moveTo) {
        switch (moveTo) {
            //up left
            case 0:
                //up row
                if (creature.getI() == 0 && creature.getJ() != 0 && creature.getJ() != 199) {
                    creature.setI(199);
                    creature.setJ(creature.getJ() - 1);
                    //left col
                } else if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 0) {
                    creature.setI(creature.getI() - 1);
                    creature.setJ(199);
                    //Top left corner
                } else if (creature.getI() == 0 && creature.getJ() == 0) {
                    creature.setI(199);
                    creature.setJ(199);
                    //Top right corner
                } else if (creature.getI() == 0 && creature.getJ() == 199) {
                    creature.setI(199);
                    creature.setJ(198);
                    //Buttom left corner
                } else if (creature.getI() == 199 && creature.getJ() == 0) {
                    creature.setI(198);
                    creature.setJ(198);
                    //inner cell
                } else {
                    creature.setI(creature.getI() - 1);
                    creature.setJ(creature.getJ() - 1);
                }
                break;
            //up
            case 1:
                //up row
                if (creature.getI() == 0 && creature.getJ() != 0 && creature.getJ() != 199) {
                    creature.setI(199);
                    //Top left corner
                } else if (creature.getI() == 0 && creature.getJ() == 0) {
                    creature.setI(199);
                    creature.setJ(0);
                    //Top right corner
                } else if (creature.getI() == 0 && creature.getJ() == 199) {
                    creature.setI(199);
                    creature.setJ(199);
                    //inner cell
                } else {
                    creature.setI(creature.getI() - 1);
                }
                break;
            //up right
            case 2:
                //up row
                if (creature.getI() == 0 && creature.getJ() != 0 && creature.getJ() != 199) {
                    creature.setI(199);
                    creature.setJ(creature.getJ() + 1);
                    //right col
                } else if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 199) {
                    creature.setI(creature.getI() - 1);
                    creature.setJ(0);
                    //Top left corner
                } else if (creature.getI() == 0 && creature.getJ() == 0) {
                    creature.setI(199);
                    creature.setJ(1);
                    //Top right corner
                } else if (creature.getI() == 0 && creature.getJ() == 199) {
                    creature.setI(199);
                    creature.setJ(0);
                    //Buttom right corner
                } else if (creature.getI() == 199 && creature.getJ() == 199) {
                    creature.setI(198);
                    creature.setJ(0);
                    //inner cell
                } else {
                    creature.setI(creature.getI() - 1);
                    creature.setJ(creature.getJ() + 1);
                }
                break;
            //right
            case 3:
                //right col
                if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 199) {
                    creature.setJ(0);
                    //Top right corner
                } else if (creature.getI() == 0 && creature.getJ() == 199) {
                    creature.setI(0);
                    creature.setJ(0);
                    //Buttom right corner
                } else if (creature.getI() == 199 && creature.getJ() == 199) {
                    creature.setI(199);
                    creature.setJ(0);
                    //inner cell
                } else {
                    creature.setJ(creature.getJ() + 1);
                }
                break;
            //down right
            case 4:
                //right col
                if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 199) {
                    creature.setI(creature.getI() + 1);
                    creature.setJ(0);
                    //Buttom row
                } else if (creature.getI() == 199 && creature.getJ() != 199 && creature.getJ() != 0) {
                    creature.setI(0);
                    creature.setJ(creature.getJ() + 1);
                    //Buttom left corner
                } else if (creature.getI() == 199 && creature.getJ() == 0) {
                    creature.setI(0);
                    creature.setJ(199);
                    //Top right corner
                } else if (creature.getI() == 0 && creature.getJ() == 199) {
                    creature.setI(1);
                    creature.setJ(0);
                    //Buttom right corner
                } else if (creature.getI() == 199 && creature.getJ() == 199) {
                    creature.setI(0);
                    creature.setJ(0);
                    //inner cell
                } else {
                    creature.setI(creature.getI() + 1);
                    creature.setJ(creature.getJ() + 1);
                }
                break;
            //down
            case 5:
                //Buttom row
                if (creature.getI() == 199 && creature.getJ() != 199 && creature.getJ() != 0) {
                    creature.setI(0);
                    //Buttom right corner
                } else if (creature.getI() == 199 && creature.getJ() == 199) {
                    creature.setI(0);
                    creature.setJ(199);
                    //Buttom left corner
                } else if (creature.getI() == 199 && creature.getJ() == 0) {
                    creature.setI(0);
                    creature.setJ(0);
                    //inner cell
                } else {
                    creature.setI(creature.getI() + 1);
                    creature.setJ(creature.getJ());
                }
                break;
            //down left
            case 6:
                //Buttom row
                if (creature.getI() == 199 && creature.getJ() != 199 && creature.getJ() != 0) {
                    creature.setI(0);
                    creature.setJ(creature.getJ() - 1);
                    //Left Col
                } else if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 0) {
                    creature.setI(creature.getI() + 1);
                    creature.setJ(199);
                    //Top left corner
                } else if (creature.getI() == 0 && creature.getJ() == 0) {
                    creature.setI(1);
                    creature.setJ(199);
                    //Buttom right corner
                } else if (creature.getI() == 199 && creature.getJ() == 199) {
                    creature.setI(0);
                    creature.setJ(198);
                    //Buttom left corner
                } else if (creature.getI() == 199 && creature.getJ() == 0) {
                    creature.setI(0);
                    creature.setJ(199);
                    //inner cell
                } else {
                    creature.setI(creature.getI() + 1);
                    creature.setJ(creature.getJ() - 1);
                }
                break;
            //left
            case 7:
                //left col
                if (creature.getI() != 0 && creature.getI() != 199 && creature.getJ() == 0) {
                    creature.setJ(199);
                    //Top left corner
                } else if (creature.getI() == 0 && creature.getJ() == 0) {
                    creature.setI(0);
                    creature.setJ(199);
                    //Buttom left corner
                } else if (creature.getI() == 199 && creature.getJ() == 0) {
                    creature.setI(199);
                    creature.setJ(199);
                    //inner cell
                } else {
                    creature.setJ(creature.getJ() - 1);
                }
                break;
            default:
        }
        //when does't have a neighbor in new location, return the relocated creature
        if (this.automaton.isCellEmpty(creature.getI(), creature.getJ())) {
            return creature;
        }
        //when has a neighbor there
        return null;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }
}
