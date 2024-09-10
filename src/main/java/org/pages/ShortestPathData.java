package org.pages;

public class ShortestPathData {
    private final String[] nodeNames;
    private final int distance;

    public ShortestPathData(String[] nodeNames, int distance) {
        this.nodeNames = nodeNames;
        this.distance = distance;
    }
    @Override
    public String toString() {
        return "{ \"nodeNames\": " + String.join(", ", nodeNames) + ", \"distance\": " + distance + " }";
    }
}