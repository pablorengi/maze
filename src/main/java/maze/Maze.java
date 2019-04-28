package maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

import static java.lang.Character.*;

public class Maze {

	private char[][] maze;
	private boolean[][] visited;
  private Point start;
  private Point end;
  private int width;
  private int height;

	public Maze(String mazeFile) {
		readMaze(mazeFile);
  }

  private void readMaze(String fileName) {
      try {
          Stream<String> lines = Files.lines(Paths.get(fileName));

          List<String> str = lines.collect(Collectors.toList());

          String[] dimensions = str.get(0).split("\\s+");
          this.width = Integer.parseInt(dimensions[0]);
          this.height = Integer.parseInt(dimensions[1]);

          String[] startPoint = str.get(1).split("\\s+");
          this.start = new Point(Integer.parseInt(startPoint[0]),
            Integer.parseInt(startPoint[1]));

          String[] endPoint = str.get(2).split("\\s+");
          this.end = new Point(Integer.parseInt(endPoint[0]),
            Integer.parseInt(endPoint[1]));

          String map = str.subList(3, str.size())
                        .stream()
                        .collect(Collectors.joining())
                        .replaceAll("\\s+", "");

          this.maze = toArrayDoubleDim(map);
          this.visited = new boolean[this.height][this.width];

          lines.close();
      } catch(IOException io) {
          io.printStackTrace();
      }
  }

  public char[][] toArrayDoubleDim(String str) {
      char[][] strArray = new char[this.height][this.width];
      int firstVal = 0;
      for(int i = 0; i < this.height; i++) {
          for(int j = 0; j < this.width; j++) {
              strArray[i][j] = parseChar(str.charAt(firstVal));
              firstVal++;
          }
      }
      strArray[this.start.y][this.start.x] = 'S';
      strArray[this.end.y][this.end.x] = 'E';
      return strArray;
  }

  public int getHeight() {
      return this.height;
  }

  public int getWidth() {
      return this.width;
  }

  public Point getStart() {
      return this.start;
  }

  public Point getEnd() {
      return this.end;
  }

  public boolean isEnd(int x, int y) {
      return x == end.getX() && y == end.getY();
  }

  public boolean isStart(int x, int y) {
      return x == start.getX() && y == start.getY();
  }

  public boolean isExplored(int row, int col) {
      return visited[row][col];
  }

  public boolean isWall(int row, int col) {
      return maze[row][col] == '#';
  }

  public void setVisited(int row, int col, boolean value) {
      visited[row][col] = value;
  }

  public boolean isValidLocation(int row, int col) {
      if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
        return false;
      }
      return true;
  }

  public char parseChar(char fileValue) {
      return fileValue == '1' ? '#' : ' ';
  }

  public void printMaze() {
    for (int x = 0; x < getHeight(); x++) {
      for (int y = 0; y < getWidth(); y++) {
          System.out.print(this.maze[x][y]);
      }
      System.out.println();
    }
  }

  public void printSolution(List<Point> solution) {
      for (Point p : solution) {
      	if(!isEnd(p.getX(), p.getY()) && !isStart(p.getX(), p.getY()))
      		this.maze[p.getY()][p.getX()] = 'X';
      }
      printMaze();
	}
}