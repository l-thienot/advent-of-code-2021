package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InputReadUtils {

    public static Stream<String> getFileContentForDay(String dayNumber) throws IOException {
        Path input = Paths.get("C:\\Users\\l.thienot\\Documents\\AdventOfCode\\src\\main\\resources\\day"+dayNumber+"\\input.txt");
        return Files.lines(input);
    }
}
