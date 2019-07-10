/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/10/2019
 *  Description: Using FordFulkerson algorithm to solve this maxFlow problem
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeMap;

public class BaseballElimination {

    private TreeMap<String, Integer> teams;
    private String[] teamsIndexBased;
    private int num;
    private int[] wins;
    private int[] loss;
    private int[] left;
    private int[][] division;
    private boolean[] isCompute;
    private boolean[] isEliminated;
    private Bag<String>[] certificateOfEliminations;

    public BaseballElimination(String filename) {
        In input = new In(filename);
        this.num = Integer.parseInt(input.readLine());
        this.wins = new int[num];
        this.loss = new int[num];
        this.left = new int[num];
        this.division = new int[num][num];
        this.teams = new TreeMap<>();
        this.teamsIndexBased = new String[num];
        this.isCompute = new boolean[num];
        this.isEliminated = new boolean[num];
        this.certificateOfEliminations = (Bag<String>[]) new Bag[num];
        int index = 0;

        for (int i = 0; i < num; i++) {
            certificateOfEliminations[i] = new Bag<String>();
        }

        while (input.hasNextLine()) {
            String[] line = input.readLine().trim().split("\\s+");
            teams.put(line[0], index);
            teamsIndexBased[index] = line[0];
            wins[index] = Integer.parseInt(line[1]);
            loss[index] = Integer.parseInt(line[2]);
            left[index] = Integer.parseInt(line[3]);
            for (int i = 0; i < num; i++) {
                division[index][i] = Integer.parseInt(line[i + 4]);
            }
            index++;
        }
    }

    public int numberOfTeams() {
        return num;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("Input team is invalid");
        }
        int index = teams.get(team);
        return this.wins[index];
    }

    public int losses(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("Input team is invalid");
        }
        int index = teams.get(team);
        return this.loss[index];
    }

    public int remaining(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("Input team is invalid");
        }
        int index = teams.get(team);
        return this.left[index];
    }

    public int against(String team1, String team2) {
        if (!teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException("Input team is invalid");
        }
        int index1 = teams.get(team1);
        int index2 = teams.get(team2);
        return this.division[index1][index2];
    }

    public boolean isEliminated(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("Input team is invalid");
        }

        int index = teams.get(team);

        if (isCompute[index]) {
            return isEliminated[index];
        }

        isCompute[index] = true;

        int totalWins = wins[index] + left[index];
        for (int i = 0; i < num; i++) {
            if (wins[i] > totalWins) {
                isEliminated[index] = true;
                certificateOfEliminations[index].add(teamsIndexBased[i]);
                return true;
            }
        }

        int v = (num - 1) * (num - 2) / 2 + num + 1;
        FlowNetwork net = new FlowNetwork(v);
        for (int i = 0; i < num; i++) {
            if (i != index) {
                net.addEdge(new FlowEdge(i, index, totalWins - wins[i]));
            }
        }

        int indexGame = num;
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                if (i != index && j != index) {
                    net.addEdge(new FlowEdge(indexGame, i, Double.POSITIVE_INFINITY));
                    net.addEdge(new FlowEdge(indexGame, j, Double.POSITIVE_INFINITY));
                    net.addEdge(new FlowEdge(v - 1, indexGame, division[i][j]));
                    indexGame++;
                }
            }
        }

        FordFulkerson maxFlow = new FordFulkerson(net, v - 1, index);
        for (FlowEdge e : net.adj(v - 1)) {
            if (e.flow() < e.capacity()) {
                isEliminated[index] = true;
            }
        }

        if (isEliminated[index]) {
            for (int i = 0; i < num; i++) {
                if (i != index && maxFlow.inCut(i)) {
                    certificateOfEliminations[index].add(teamsIndexBased[i]);
                }
            }
        }
        else {
            isEliminated[index] = false;
            certificateOfEliminations[index] = null;
        }
        return isEliminated[index];
    }


    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("Input team is invalid");
        }

        int index = teams.get(team);
        if (isCompute[index]) {
            return certificateOfEliminations[index];
        }

        if (isEliminated(team)) {
            return certificateOfEliminations[index];
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
