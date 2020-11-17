/**
 * @Author- Hilla Bartov
 *          Ofir Cohen
 **/
import java.awt.*;

import biuoop.DrawSurface;
import biuoop.GUI;

public class LifePanel {
    private GUI gui;
    private Automaton automaton;
    private int quarantine, level;

    public LifePanel(GUI g, Automaton a, int l, int k) {
        this.gui = g;
        this.automaton = a;
        this.level = l;
        this.quarantine = k;
    }

    public void show() {
        DrawSurface d = this.gui.getDrawSurface();
        d.fillRectangle(0, 0, 1000, 1000);
        d.setColor(Color.gray);
        //make the 200x200 automaton pattern
        for (int i = 0; i < this.automaton.getAutomaton().length; i++) {
            d.drawLine(0, i * 5, 1000, i * 5);//row
            d.drawLine(i * 5, 0, i * 5, 1000);//col
        }
        //fill each populated cell in the right color
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                //check if current row,col cell is not empty
                Creature c = this.automaton.getAutomaton()[i][j];
                if (c != null) {
                    if (c.isSick()) {
                        d.setColor(Color.red);
                    } else {
                        d.setColor(Color.green);
                    }
                    d.fillRectangle(i * 5, j * 5, 5, 5);
                }
            }
        }
        this.gui.show(d);
    }

    public void endScreen(int generations) {
        DrawSurface d = this.gui.getDrawSurface();
        d.fillRectangle(0, 0, 1000, 1000);
        d.setColor(Color.blue);
        d.drawText(10, 320, "Number of creatures: " + this.automaton.getCreatures().size(), 45);
        d.drawText(10, 380, "Probability of contagion: " +
                this.automaton.getCreatures().get(0).getInfectionProb(), 45);
        d.drawText(10, 440, "Quarantine level: " + this.quarantine + " starting from generation: " +
                this.level, 45);
        d.setColor(Color.red);
        d.drawText(100, 520, this.automaton.getSickCounter() + " sick cells " + "after " +
                generations + " generations", 45);

        this.gui.show(d);
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }
}
