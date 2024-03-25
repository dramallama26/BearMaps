package bearmaps;

import bearmaps.utils.Constants;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.ps.KDTree;
import bearmaps.utils.ps.NaivePointSet;
import bearmaps.utils.ps.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */

public class AugmentedStreetMapGraph extends StreetMapGraph {
    /** Contains a mapping from a Point to a HashMap */
    HashMap<Point, Long> p2n;
    /** Contains all locations names mapped to a node id */
    HashMap<String, ArrayList<Long>> name2node = new HashMap<>();
    HashMap<Long, String> realname = new HashMap<>();
    HashMap<Long, Double[]> loc = new HashMap<>();
    KDTree pointrep;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        p2n = (HashMap<Point, Long>) nodes.stream().filter(n -> !neighbors(n.id()).isEmpty())
                .collect(Collectors.toMap((n) -> new Point(projectToX(((Node) n).lon(), ((Node) n).lat()), projectToY(((Node) n).lon(), ((Node) n).lat())), (n) -> ((Node) n).id()));
        List<Node> temp = this.getAllNodes().stream().filter(n -> n.name() != null)
                .collect(Collectors.toList());
        for (Node n: temp) {
            String name = cleanString(n.name());
            if (!name2node.containsKey(name)) {
                name2node.put(name, new ArrayList<>());
            }
            name2node.get(name).add(n.id());
            realname.put(n.id(), n.name());
            loc.put(n.id(), new Double[]{n.lon(), n.lat()});
        }
        List<Point> points = new ArrayList<>();
        for (Point p: p2n.keySet()) {
            points.add(p);
        }
        pointrep = new KDTree(points);
    }


    /**
     * For Project Part III
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        double x = projectToX(lon, lat);
        double y = projectToY(lon, lat);
        Point result = pointrep.nearest(x, y);
        return p2n.get(result);
    }

    /**
     * Return the Euclidean x-value for some point, p, in Berkeley. Found by computing the
     * Transverse Mercator projection centered at Berkeley.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean x-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    static double projectToX(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double b = Math.sin(dlon) * Math.cos(phi);
        return (K0 / 2) * Math.log((1 + b) / (1 - b));
    }

    /**
     * Return the Euclidean y-value for some point, p, in Berkeley. Found by computing the
     * Transverse Mercator projection centered at Berkeley.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean y-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    static double projectToY(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double con = Math.atan(Math.tan(phi) / Math.cos(dlon));
        return K0 * (con - Math.toRadians(ROOT_LAT));
    }


    /**
     * For Project Part IV (extra credit)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cpre = cleanString(prefix);
        HashSet<String> match = new HashSet<>();
        List<String> namematches = name2node.keySet().stream().filter(k -> k.startsWith(cpre))
                .collect(Collectors.toList());
        for (String s: namematches) {
            for (Long id: name2node.get(s)) {
                String temp = realname.get(id);
                match.add(temp);
            }
        }
        List<String> matches = match.stream().sorted().collect(Collectors.toList());
        return matches;
    }

    /**
     * For Project Part IV (extra credit)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Long id: name2node.get(cleanString(locationName))) {
            HashMap<String, Object> temp = new HashMap<>();
            Double[] curr = loc.get(id);
            temp.put("name", realname.get(id));
            temp.put("lon", curr[0]);
            temp.put("id", id);
            temp.put("lat", curr[1]);
            results.add(temp);
        }
        return results;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

        
    /**
     * Scale factor at the natural origin, Berkeley. Prefer to use 1 instead of 0.9996 as in UTM.
     * @source https://gis.stackexchange.com/a/7298
     */
    private static final double K0 = 1.0;
    /** Latitude centered on Berkeley. */
    private static final double ROOT_LAT = (Constants.ROOT_ULLAT + Constants.ROOT_LRLAT) / 2;
    /** Longitude centered on Berkeley. */
    private static final double ROOT_LON = (Constants.ROOT_ULLON + Constants.ROOT_LRLON) / 2;

}
