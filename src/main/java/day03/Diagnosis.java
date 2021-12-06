package day03;

import utils.InputReadUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Diagnosis {

    public static void main(String[] args) throws IOException {
        int increaseNumber = computePowerConsumption(InputReadUtils.getFileContentForDay("03"));
        System.out.println("Increases, one by one : " + increaseNumber);

        int lifeSupportRating = computeLifeSupportRating(InputReadUtils.getFileContentForDay("03"));
        System.out.println("Life support rating : " + lifeSupportRating);
    }

    private static int computePowerConsumption(Stream<String> data) {
        List<String> diagnosisReport = data.collect(Collectors.toList());
        int diagnosisSize = diagnosisReport.size();
        int[] bits1Counts = getBits1Counts(diagnosisReport);

        StringBuilder binaryGamma = new StringBuilder();
        StringBuilder binaryEpsilon = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            if (bits1Counts[i] == diagnosisSize / 2.0) {
                System.out.println("flop");
            }
            if (bits1Counts[i] > diagnosisSize / 2.0) {
                binaryGamma.append("1");
                binaryEpsilon.append("0");
            } else {
                binaryGamma.append("0");
                binaryEpsilon.append("1");
            }
        }

        int decimalEpsilon = Integer.parseInt(binaryEpsilon.toString(), 2);
        int decimalGamma = Integer.parseInt(binaryGamma.toString(), 2);
        return decimalEpsilon * decimalGamma;
    }

    private static int[] getBits1Counts(List<String> diagnosisReport) {
        int[] bits1Counts = new int[12];
        Arrays.fill(bits1Counts, 0);

        diagnosisReport.forEach(line -> {
            String[] bits = line.split("");
            for (int i = 0; i < 12; i++) {
                bits1Counts[i] += Integer.parseInt(bits[i]);
            }
        });
        return bits1Counts;
    }

    private static int computeLifeSupportRating(Stream<String> data) {
        List<String> diagnosisReport = data.collect(Collectors.toList());

        int oxygenGeneratorRating = getOxygenGeneratorRating(diagnosisReport);
        int co2ScrubberRating = getCo2ScrubberRating(diagnosisReport);

        return oxygenGeneratorRating*co2ScrubberRating;
    }

    private static int getOxygenGeneratorRating(List<String> diagnosisReport) {
        int bitConsideredPosition = 0;
        List<String> oxygenGeneratorRatingSelection = List.copyOf(diagnosisReport);
        while (oxygenGeneratorRatingSelection.size() > 1 && bitConsideredPosition < 12) {
            int bits1Counts = 0;
            for (String reportLine : oxygenGeneratorRatingSelection) {
                String consideredBit = String.valueOf(reportLine.charAt(bitConsideredPosition));
                bits1Counts += Integer.parseInt(consideredBit);
            }
            char majorBit = bits1Counts >= oxygenGeneratorRatingSelection.size() / 2.0 ? '1' : '0';
            oxygenGeneratorRatingSelection = keepOnlyLinesWithBitAtPosition(oxygenGeneratorRatingSelection, majorBit, bitConsideredPosition);
            bitConsideredPosition++;
        }

        if (oxygenGeneratorRatingSelection.size() != 1)
            throw new IllegalStateException("no oxygen generator rating found");
        return Integer.parseInt(oxygenGeneratorRatingSelection.get(0), 2);
    }

    private static List<String> keepOnlyLinesWithBitAtPosition(List<String> oxygenGeneratorRatingSelection, char majorBit, final int bitConsideredPosition) {
        oxygenGeneratorRatingSelection = oxygenGeneratorRatingSelection
                .stream()
                .filter(line -> line.charAt(bitConsideredPosition) == majorBit)
                .collect(Collectors.toList());
        return oxygenGeneratorRatingSelection;
    }

    private static int getCo2ScrubberRating(List<String> diagnosisReport) {
        int bitConsideredPosition = 0;
        List<String> co2ScrubberRatingSelection = List.copyOf(diagnosisReport);
        while (co2ScrubberRatingSelection.size() > 1 && bitConsideredPosition < 12) {
            int bits1Counts = 0;
            for (String reportLine : co2ScrubberRatingSelection) {
                String consideredBit = String.valueOf(reportLine.charAt(bitConsideredPosition));
                bits1Counts += Integer.parseInt(consideredBit);
            }
            char minorBit = bits1Counts < co2ScrubberRatingSelection.size() / 2.0 ? '1' : '0';
            co2ScrubberRatingSelection = keepOnlyLinesWithBitAtPosition(co2ScrubberRatingSelection, minorBit, bitConsideredPosition);
            bitConsideredPosition++;
        }

        if (co2ScrubberRatingSelection.size() != 1)
            throw new IllegalStateException("no co2 scrubber rating found");
        return Integer.parseInt(co2ScrubberRatingSelection.get(0), 2);
    }
}
