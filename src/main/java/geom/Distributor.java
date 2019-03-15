package geom;

import java.util.Set;

public interface Distributor {
	Set<Point3D> populate(int n, double cellRadius);
}