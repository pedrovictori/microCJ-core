package geom;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomRecursiveDistributor implements Distributor{

	@Override
	public Set<Point3D> populate(int n, double cellRadius) {
		Set<Point3D> points = new HashSet<>();
		points.add(Point3D.ZERO);
		Random r = new Random();

		for (int i = 1; i < n; i++) {
			Point3D element = findEmptyCenter(cellRadius*2, points.iterator().next(), points, cellRadius, r);
			points.add(element);
		}
		return points;
	}

	/**
	 * Returns a point at a distance from another point such as the new point is placed at that minimum given distance from any other point in the set. If this is not possible, returns null
	 * @param location The origin point
	 * @param distance The minimum distance between the new point and any point in the set, and the exact distance between the origin and the new point
	 * @param locations A set of points
	 * @return The specified new point, or null if no such point found
	 */
	@Override
	public Point3D locateEmptySpotNextTo(Point3D location, double distance, Set<Point3D> locations) {
		Point3D newPoint = null;
		Random r = new Random();
		for (int i = 0; i < 30*distance; i++) { //creates normal random points until an empty one is found. Maximum number of 30*distance tries should be enough
			Point3D rndPoint = getNormalRandomCoordinatesAroundSphere(location, distance, r);
			boolean empty = true;

			for (Point3D p : locations) { //checks if empty
				if (p.distance(rndPoint) < distance) {
					empty = false;
				}
			}

			if (empty) {
				newPoint = rndPoint;
				break;
			}
		}

		return newPoint;
	}

	/**
	 * Returns a point from a normal random distribution that is on the surface of a sphere with given center and radius
	 * @param origin the center of the sphere
	 * @param radius the radius of the sphere
	 * @param r a Random generator
	 * @return a normal random Point3d on the surface of the sphere
	 */
	private static Point3D getNormalRandomCoordinatesAroundSphere(Point3D origin, double radius, Random r) {
		double[] rndArray = new double[3];
		double rndSqrSum = 0;

		for (int i = 0; i < 3; i++) {
			rndArray[i] = r.nextGaussian();
			rndSqrSum += rndArray[i] * rndArray[i];
		}

		double factor = radius / Math.sqrt(rndSqrSum);

		double x = rndArray[0]*factor;
		double y = rndArray[1]*factor;
		double z = rndArray[2]*factor;
		return origin.add(x, y, z);
	}

	private static Point3D findEmptyCenter(double distance, Point3D origin, Set<Point3D> cellCenters, double cellRadius, Random r) {

		Point3D ec = getNormalRandomCoordinatesAroundSphere(origin, distance, r);
		boolean empty = true;
		for (Point3D p: cellCenters) {
			if (p.distance(ec) < (cellRadius*1.8)) {
				empty = false;
			}
		}
		if (empty) {
			return ec;
		} else { //TODO improve this and get rid of recursion
			return findEmptyCenter(distance+0.003, origin, cellCenters, cellRadius, r); //that number was determined via trial and error as the lowest one needed to avoid an stack overflow error
		}
	}

	private static Point3D randomPointAtDistance(double distance, Point3D origin, Random r) {
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
