package geom;

import core.Cell;

import java.util.*;

public class CellDistribution {
	private static Point3D[] regularIntervals;
	static{ //generate all 18 directions to generate a new, touching cell
		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		points.add(new Point3D(-1,-1,0));
		points.add(new Point3D(-1,0,0));
		points.add(new Point3D(-1,1,0));
		points.add(new Point3D(0,-1,0));
		points.add(new Point3D(0,1,0));
		points.add(new Point3D(1,-1,0));
		points.add(new Point3D(1,0,0));
		points.add(new Point3D(1,1,0));

		//for concurrent modification
		Set<Point3D> pointsCopy = new HashSet<>(points);

		//z+-10
		for (Point3D point : points) {
			Point3D zFactor = new Point3D(0, 0, 1);
			pointsCopy.add(point.add(zFactor));
			pointsCopy.add(point.add(zFactor.multiply(-1)));
		}

		//remove point zero and corners +-10
		pointsCopy.remove(Point3D.ZERO);
		points.addAll(pointsCopy);
		for (Point3D point : points) {
			if (Math.abs(point.getX()) == 1 && Math.abs(point.getY()) == 1 && Math.abs(point.getZ()) == 1) {
				pointsCopy.remove(point);
			}
		}
		regularIntervals = pointsCopy.toArray(new Point3D[0]);
	}

	/**
	 * private constructor for non-instanciability
	 */
	private CellDistribution() {
	}

	public static Set<Point3D> randomPackingAtRegularIntervals(int n, double cellRadius) {
		Point3D[] modRegularIntervals = new Point3D[18];
		for (int i = 0; i < 18; i++) {
			modRegularIntervals[i] = regularIntervals[i].multiply(cellRadius*2);
		}

		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		Random rand = new Random();
		List<Point3D> listForRandoms = new ArrayList<>(points);

		for (int i = 0; i < n;) {
			int r = rand.nextInt(18);
			int s = rand.nextInt(listForRandoms.size());
			Point3D element = listForRandoms.get(s).add(modRegularIntervals[r]);
			if (points.add(element)) {
				i++;
				listForRandoms.add(element);
			}
		}
		return points;
	}

	public static Set<Point3D> recursiveRandomPacking(int n, double cellRadius) {
		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);

		for (int i = 0; i < n; i++) {
			Point3D element = findEmptyCenter(cellRadius*2, points.iterator().next(), points, cellRadius);
			points.add(element);
		}
		return points;
	}

	private static Point3D findEmptyCenter(double distance, Point3D origin, Set<Point3D> cellCenters, double cellRadius) {

		Point3D ec = randomPointAtDistance(distance, origin);
		boolean empty = true;
		for (Point3D p: cellCenters) {
			if (p.distance(ec) < (cellRadius*2)) {
				empty = false;
			}
		}
		if (empty) {
			return ec;
		} else {
			return findEmptyCenter(distance+0.005, origin, cellCenters, cellRadius); //that number was determined via trial and error as the lowest one needed to avoid an stack overflow error
		}
	}

	private static Point3D randomPointAtDistance(double distance, Point3D origin) {
		Random rand = new Random();
		Point3D randomPoint = new Point3D(rand.nextInt(100) - 50, rand.nextInt(100) - 50, rand.nextInt(100) - 50);
		double x1 = origin.getX();
		double y1 = origin.getY();
		double z1 = origin.getZ();
		double x2 = randomPoint.getX();
		double y2 = randomPoint.getY();
		double z2 = randomPoint.getZ();
		double d = Math.sqrt( //d = sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)
				Math.pow(x2 - x1, 2) +
						Math.pow(y2 - y1, 2) +
						Math.pow(z2 - z1, 2));

		double u = distance / d;
		double x3 = (1 - u) * x1 + u * x2;
		double y3 = (1 - u) * y1 + u * y2;
		double z3 = (1 - u) * z1 + u * z2;
		return new Point3D(x3, y3, z3);
	}
}
