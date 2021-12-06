package day06;

import utils.InputReadUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class LargeScaleLanternfishFarming {

    public static void main(String[] args) throws IOException {
        long lanternFishPopulation = makeLanternfishesLive(InputReadUtils.getFileContentForDay("06"), 256 );
        System.out.println(lanternFishPopulation + " LanternFishes");
    }

    private static long makeLanternfishesLive(Stream<String> input, long daysToLive) {
        long[] lanternfishes = new long[9];
        Arrays.fill(lanternfishes, 0);
        input.flatMap(line -> Arrays.stream(line.split(",")))
                .forEach(ttl -> lanternfishes[Integer.parseInt(ttl)]++);


        for (int i = 0; i < daysToLive; i++) {
            long[] lanternfishesBeforeAging = Arrays.copyOf(lanternfishes, 9);
            for (int j = 8; j > 0; j--) {
                lanternfishes[j] -= lanternfishesBeforeAging[j];
                lanternfishes[j - 1] += lanternfishesBeforeAging[j];
            }
            lanternfishes[0] -= lanternfishesBeforeAging[0]; // enlever ceux à 0
            lanternfishes[6] += lanternfishesBeforeAging[0]; // remettre à 6 ceux qui ont procréé
            lanternfishes[8] += lanternfishesBeforeAging[0]; // creer les nouveaux
        }
        return Arrays.stream(lanternfishes).reduce(Long::sum).orElse(0);
    }


}
