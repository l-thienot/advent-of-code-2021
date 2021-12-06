package day06;

import utils.InputReadUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LanternfishFarming {

    public static void main(String[] args) throws IOException {
        int lanternFishPopulation = makeLanternfishesLive(InputReadUtils.getFileContentForDay("06"), 80);
        System.out.println(lanternFishPopulation + " LanternFishes");
    }

    private static int makeLanternfishesLive(Stream<String> input, int daysToLive) {
        List<Lanternfish> population = input
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(ttl -> new Lanternfish(Integer.parseInt(ttl)))
                .collect(Collectors.toList());
        for (int i = 0; i < daysToLive; i++) {
            List<Lanternfish> populationBeforeToday = List.copyOf(population);
            for (Lanternfish fish : populationBeforeToday) {
                boolean shouldCreateNewFish = fish.shouldCreateNewFish();
                if (shouldCreateNewFish) population.add(new Lanternfish(8));
            }

        }
        return population.size();
    }


}
