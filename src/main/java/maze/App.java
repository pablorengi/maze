package maze;

// import java.util.Scanner;

import java.util.List;
import javax.swing.JFileChooser;

public class App {

    public static void main( String[] args ) {
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter the maze file name: ");
        // String fileName = scanner.next();

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        String fileName = chooser.getSelectedFile().getName();
        
        Maze maze = new Maze(fileName);
        maze.printMaze();
        Solver solver = new Solver();
        List<Point> solution = solver.solve(maze);
        maze.printSolution(solution);
    }
}