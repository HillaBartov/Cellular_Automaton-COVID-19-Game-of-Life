/**
 * @Author- Hilla Bartov
 *          Ofir Cohen
 **/
import biuoop.GUI;

import javax.swing.JFrame;
import java.awt.*;
import java.util.*;
import java.util.Random;

public class COVID19Contagion {
    public static void main(String[] args) {
        int n, l=0, k;
        float p;
        Automaton automaton = new Automaton();
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter number of creatures:");
        n = input.nextInt();
        System.out.println("Please enter probability of contagion(0<=P<=1):");
        p = input.nextFloat();
        System.out.println("Please enter quarantine level:");
        k = input.nextInt();
        if (k!=0) {
            System.out.println("Please enter quarantine starting generation");
            l = input.nextInt();
        }
        //initialize the automaton with the N creatures, and the sick one
        //choose randomly the sick cell
        int[] sick = new int[2];
        sick[0] = rand.nextInt(199);
        sick[1] = rand.nextInt(199);
        Creature creatureSick = new Creature(sick[0], sick[1], p);
        creatureSick.setSick(true);
        automaton.setCell(creatureSick.getI(), creatureSick.getJ(), creatureSick);
        automaton.addCreature(creatureSick);
        for (int i = 0; i < n - 1; i++) {
            int row = rand.nextInt(199), column = rand.nextInt(199);
            //do not enter creature to an already populated cell
            while (automaton.getAutomaton()[row][column] != null) {
                row = rand.nextInt(199);
                column = rand.nextInt(199);
            }
            Creature creature = new Creature(row, column, p);
            automaton.setCell(row, column, creature);
            automaton.addCreature(creature);
        }
        GUI gui = new GUI("COVID19", 1000, 1000);
        LifePanel lifePanel = new LifePanel(gui, automaton, l, k);
        //when asked that the quarantine level starts from L generation, start from quarantine 0 and enter the k later
        if (l != 0) {
            RunGenerations runGenerations = new RunGenerations(automaton, 1000, 0, l, k, lifePanel);
            runGenerations.run();
        } else {
            RunGenerations runGenerations = new RunGenerations(automaton, 1000, k, l, lifePanel);
            runGenerations.run();
        }
    }
}
