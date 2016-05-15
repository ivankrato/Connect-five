package piskvorky;

import java.io.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class takes Saveable objects and make file from them.
 *
 * @author Ivan Kratochvíl
 */
public class Saver {

    /**
     * List of objects to save.
     */
    private ArrayList<Saveable> toSave;
    /**
     * Instance of class Player - player, who is on move at the time of saving.
     */
    private Player onMove;

    /**
     * Constructor, takes references to instances of classes, which should be
     * saved and makes them into List.
     *
     * @param objectsToSave objects to save
     */
    public Saver(Saveable... objectsToSave) {
        toSave = new ArrayList<Saveable>(Arrays.asList(objectsToSave));
    }

    /**
     * Takes the objects to save and write them into the file.
     *
     * @param file file to save the objects in
     */
    public void save(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            for (Saveable element : toSave) {
                switch (toSave.indexOf(element)) {
                    case 0:
                        pw.println("## Cross player");
                        break;
                    case 1:
                        pw.println("## Circle player");
                        break;
                    case 2:
                        pw.println("## Gaming area");
                        break;
                    case 3:
                        pw.println("## Checker");
                        break;
                    case 4:
                        pw.println("## Move history");
                        break;
                }
                pw.println(element.toSaveString());
            }
            pw.println("## On move");
            pw.println("mark = " + onMove.getMark());
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            System.out.println(ex);
            return;
        }
    }

    /**
     * Take particular file and load the properties of objects which are in the
     * list
     *
     * @param file file to load from
     */
    public void load(File file) {
        //crossPlayer, circlePlayer, gamingArea, checker, moveHistory
        int mark = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb;
            br.readLine(); //skip the first line
            for (Saveable element : toSave) {
                String line = br.readLine();
                sb = new StringBuilder();
                do {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                } while (!line.startsWith("##"));
                element.loadFromString(sb.toString());
            }
            IOException error = new IOException("File corrupted.");
            Scanner sc = new Scanner(br);
            String toFind = "mark = ";
            if (sc.findInLine(toFind) != null) {
                if (sc.hasNextInt()) {
                    mark = sc.nextInt();
                } else {
                    throw error;
                }
            } else {
                throw error;
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex);
            return;
        }
        if (mark == 1) {
            onMove = (Player) toSave.get(0);
        } else if (mark == 2) {
            onMove = (Player) toSave.get(1);
        }
    }

    /**
     * Adds object to save into list of these objects.
     *
     * @param element object to save
     */
    public void add(Saveable element) {
        toSave.add(element);
    }

    /**
     * @return instance of class Player - player who should be on move
     */
    public Player getOnMove() {
        return onMove;
    }

    /**
     * Sets the onMove variable.
     * @param onMove player, who is on move
     */
    public void setOnMove(Player onMove) {
        this.onMove = onMove;
    }
}
