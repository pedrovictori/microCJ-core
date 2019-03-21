package geom;

import java.util.*;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

	@Override
	public Point3D locateEmptySpotNextTo(Point3D location, double distance, Set<Point3D> locations) {
		return null;
		// TODO: 19/03/2019 implement
	}
}
