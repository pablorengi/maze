package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Solver {
	private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	public List<Point> solve(Maze maze) {
        LinkedList<Point> nextToVisit = new LinkedList<>();
        Point start = maze.getStart();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Point cur = nextToVisit.remove();

            if (!maze.isValidLocation(cur.getY(), cur.getX()) ||
                maze.isExplored(cur.getY(), cur.getX())) {
                continue;
            }

            if (maze.isWall(cur.getY(), cur.getX())) {
                maze.setVisited(cur.getY(), cur.getX(), true);
                continue;
            }

            if (maze.isEnd(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
                //adding the wrapping offset
                int width = checkWidth(cur.getX() + direction[0], maze.getWidth());
                int height = checkHeight(cur.getY() + direction[1], maze.getHeight());
                Point point = new Point(width, height, cur);
                nextToVisit.add(point);
                maze.setVisited(cur.getY(), cur.getX(), true);
            }
        }
        return Collections.emptyList();
    }

    private int checkWidth(int column, int width) {
        if(column < 0)
            return column + width;
        else if(column >= width)
            return 0;
        else return column;
    }

    private int checkHeight(int row, int height) {
        if(row < 0)
            return row + height;
        else if(row >= height)
            return 0;
        else return row;
    }

    private List<Point> backtrackPath(Point cur) {
        List<Point> path = new ArrayList<>();
        Point iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }
}