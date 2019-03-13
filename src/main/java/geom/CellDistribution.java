package geom;

import java.util.*;

public class CellDistribution {
	private static Point3D[] regularIntervals;
	static{ //generate all 18 directions to generate a new, touching cell
		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		points.add(new Point3D(10,10,0));
		points.add(new Point3D(0,10,0));
		points.add(new Point3D(10,0,0));

		for (Point3D point : points) {
			Point3D neg = point.multiply(-1);
			points.add(neg);
			points.add(point.add(neg));
		}
		for (Point3D point : points) {
			Point3D zfactor = new Point3D(0, 0, 10);
			points.add(point.add(zfactor));
			points.add(point.add(zfactor.multiply(-1)));
		}

		regularIntervals = points.toArray(new Point3D[0]);
	}
	/**
	 * private constructor for non-instanciability
	 */
	private CellDistribution() {
	}

	public static Point3D[] RandomPackingAtRegularIntervals(int n) {
		Point3D[] centers = new Point3D[n];
		centers[0] = Point3D.ZERO;

	}


}
