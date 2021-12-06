package day01;

import utils.InputReadUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepthSonar {
    public static void main(String[] args) throws IOException {
        int increaseNumber = countIncreases(InputReadUtils.getFileContentForDay("01"));
        System.out.println("Increases, one by one : " + increaseNumber);

        int windowIncreaseNumber = countIncreasesByWindow(InputReadUtils.getFileContentForDay("01"));
        System.out.println("Increases, by window of three : " + windowIncreaseNumber);
    }


    private static int countIncreases(Stream<String> lines) {
        List<Integer> measurements = lines.map(Integer::valueOf).collect(Collectors.toList());
        Integer lastMeasurement = null;
        int nbLargerThanPrevious = 0;
        for (Integer measurement : measurements) {
            if (lastMeasurement != null && lastMeasurement < measurement) {
                nbLargerThanPrevious++;
            }
            if (measurement.equals(lastMeasurement)) {
                System.out.println("two measurements are equal");
            }
            lastMeasurement = measurement;
        }

        return nbLargerThanPrevious;
    }

    private static int countIncreasesByWindow(Stream<String> lines) {
        List<Integer> measurements = lines.map(Integer::valueOf).collect(Collectors.toList());

        List<Integer> windowsFirstElements = measurements.subList(0, measurements.size() - 2);
        List<Integer> windowsSecondElements = measurements.subList(1, measurements.size() - 1);
        List<Integer> windowsThirdElements = measurements.subList(2, measurements.size());

        Integer lastMeasurement = null;
        int nbLargerThanPrevious = 0;
        for (int index = 0; index < measurements.size() - 2; index++) {
            int currentMeasurement = windowsFirstElements.get(index)
                    + windowsSecondElements.get(index)
                    + windowsThirdElements.get(index);

            if (lastMeasurement != null && lastMeasurement < currentMeasurement) {
                nbLargerThanPrevious++;
            }
            lastMeasurement = currentMeasurement;
        }

        return nbLargerThanPrevious;
    }
}
