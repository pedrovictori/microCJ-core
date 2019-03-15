package geom;

import java.util.*;

public class RegularRandomDistributor implements Distributor{
	private static Point3D[] regularIntervals;

	static { //generate all 18 directions to generate a new, touching cell
		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		points.add(new Point3D(-1, -1, 0));
		points.add(new Point3D(-1, 0, 0));
		points.add(new Point3D(-1, 1, 0));
		points.add(new Point3D(0, -1, 0));
		points.add(new Point3D(0, 1, 0));
		points.add(new Point3D(1, -1, 0));
		points.add(new Point3D(1, 0, 0));
		points.add(new Point3D(1, 1, 0));

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

	@Override
	public Set<Point3D> populate(int n, double cellRadius) {
		Point3D[] modRegularIntervals = new Point3D[18];
		for (int i = 0; i < 18; i++) {
			modRegularIntervals[i] = regularIntervals[i].multiply(cellRadius * 2);
		}

		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		Random rand = new Random();
		List<Point3D> listForRandoms = new ArrayList<>(points);

		for (int i = 0; i < n; ) {
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
}
