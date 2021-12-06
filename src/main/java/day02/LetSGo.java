package day02;

import utils.InputReadUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LetSGo {
    public static void main(String[] args) throws IOException {
        Position arrival = whereDoYouGoLittleSubmarine(new Position(), InputReadUtils.getFileContentForDay("02"));
        System.out.println("Arrival with simple submarine commands: " + arrival.depth * arrival.hPosition);

        Position secondArrival = whereDoYouGoComplexSubmarine(new Position(), InputReadUtils.getFileContentForDay("02"));
        System.out.println("Arrival with complex submarine commands : " + secondArrival.depth * secondArrival.hPosition);
    }

    private static Position whereDoYouGoLittleSubmarine(Position initialPosition, Stream<String> instructions) {
        instructions.forEach(instruction -> {
            String[] instrParts = instruction.split(" ");
            Command command = Command.from(instrParts[0]);
            int move = Integer.parseInt(instrParts[1]);
            if (command == Command.FWD) {
                initialPosition.hPosition += move;
            } else if (command == Command.DOWN) {
                initialPosition.depth += move;
            } else if (command == Command.UP) {
                initialPosition.depth -= move;
            }
        });
        return initialPosition;
    }
    private static Position whereDoYouGoComplexSubmarine(Position initialPosition, Stream<String> instructions) {
        instructions.forEach(instruction -> {
            String[] instrParts = instruction.split(" ");
            Command command = Command.from(instrParts[0]);
            int move = Integer.parseInt(instrParts[1]);
            if (command == Command.FWD) {
                initialPosition.hPosition += move;
                initialPosition.depth += initialPosition.aim*move;
            } else if (command == Command.DOWN) {
                initialPosition.aim += move;
            } else if (command == Command.UP) {
                initialPosition.aim -= move;
            }
        });
        return initialPosition;
    }
}
