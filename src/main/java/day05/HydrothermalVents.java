package day05;

import utils.InputReadUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HydrothermalVents {

    public static final int SIZE = 1000;

    public static void main(String[] args) throws IOException {
        int nbDangerousAreas = getHowManyDangerousAreas(InputReadUtils.getFileContentForDay("05"));
        System.out.println("Nb of dangerous areas : " + nbDangerousAreas);
    }

    private static int getHowManyDangerousAreas(Stream<String> input) {
        List<String> vents = input.collect(Collectors.toList());
        int[][] ventsStrength = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(ventsStrength[i], 0);
        }

        for (String vent : vents) {
            String[] segment = vent.split(" -> ");
            if (segment.length != 2) throw new IllegalArgumentException("Pb format input : " + vent);
            String[] start = segment[0].split(",");
            String[] end = segment[1].split(",");
            if (start.length != 2 || end.length != 2)
                throw new IllegalArgumentException("Pb format input : " + segment[0] + " ; " + segment[1]);

            Coordinate startCoord = new Coordinate(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
            Coordinate endCoord = new Coordinate(Integer.parseInt(end[0]), Integer.parseInt(end[1]));

            int xOperator = Integer.compare(endCoord.x, startCoord.x);
            int yOperator = Integer.compare(endCoord.y, startCoord.y);
            ;
            if (xOperator == 0 || yOperator == 0) {
            Coordinate current = new Coordinate(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
            ventsStrength[startCoord.x][startCoord.y]++;
            while (!current.equals(endCoord)) {
                current.x += xOperator;
                current.y += yOperator;
                ventsStrength[current.x][current.y]++;
            }
            }
        }

        int countDangerousPlaces = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (ventsStrength[i][j] >= 2) countDangerousPlaces++;
            }
        }
        return countDangerousPlaces;
    }
}
