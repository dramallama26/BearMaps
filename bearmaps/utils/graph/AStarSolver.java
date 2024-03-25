package bearmaps.utils.graph;

import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight = 0;
    private List<Vertex> solution = new ArrayList<>();
    private int statesExplored;
    private double timeSpent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        HashMap<Vertex, Double> distTo = new HashMap<>();
        distTo.put(start, input.estimatedDistanceToGoal(start, end));
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        edgeTo.put(start, null);
        /* Create a PQ where each vertex v will have priority value p equal to the sum of v’s distance from the source plus the heuristic estimate from v to the goal, i.e. p = distTo[v] + h(v, goal).
                Insert the source vertex into the PQ. **/
        MinHeapPQ<Vertex> fringe = new MinHeapPQ<>();
        fringe.insert(start, 0);
        /* Repeat while the PQ is not empty, PQ.peek() is not the goal, and timeout is not exceeded: **/
        while (fringe.size() != 0 && sw.elapsedTime() <= timeout) {
            Vertex p = fringe.poll();
            statesExplored ++;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                Vertex q = e.to();
                double w = e.weight();
                if (end.equals(q)) {
                    List <Vertex> returnPath = new ArrayList<>();
                    solutionWeight = distTo.get(p) + w;
                    returnPath.add(q);
                    while (edgeTo.get(p) != null) {
                        returnPath.add(p);
                        p = edgeTo.get(p);
                    }
                    returnPath.add(start);
                    Collections.reverse(returnPath);
                    outcome = SolverOutcome.SOLVED;
                    solution = returnPath;
                    timeSpent = sw.elapsedTime();
                    return;
                }
                if (!distTo.containsKey(q)) {
                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p);
                    fringe.insert(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }
                else if (distTo.get(p) + w < distTo.get(q)) {
                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p);
                    fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }
            }
        }
        if (sw.elapsedTime() >= timeout) {
            outcome = SolverOutcome.TIMEOUT;
            timeSpent = timeout;
        }
        else {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
        }
    }
    public SolverOutcome outcome() {
    return outcome;
    }
    public List<Vertex> solution() {
    return solution;
    }
    public double solutionWeight() {
    return solutionWeight;
    }
    public int numStatesExplored() {
    return statesExplored;
    }
    public double explorationTime() {
    return timeSpent;
    }
}
