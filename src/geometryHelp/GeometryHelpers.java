package geometryHelp;

import java.util.ArrayList;
import java.util.List;

public class GeometryHelpers {
	public static Point getPointClosestClosestFromListOfPoints(Point closestTo, List<Point> points) {
		List<Point> closestPoints = null;
		
		double currentClosestDistance = Double.MAX_VALUE;
		
		for (Point currPoint : points) {
			double currDistance = closestTo.distanceTo(currPoint);
			
			// New best
			if (currDistance < currentClosestDistance) {
				closestPoints = new ArrayList<Point>();
				closestPoints.add(currPoint);
				currentClosestDistance = currDistance;
			}
			// Tie
			else if (currDistance == currentClosestDistance) {
				closestPoints.add(currPoint);
			}
		}
		double returnX = 0;
		double returnY = 0;
		for (Point point : closestPoints) {
			returnX += point.x;
			returnY += point.y;
		}
		returnX /= closestPoints.size();
		returnY /= closestPoints.size();
		
		return new Point(returnX, returnY);
	}

}
