package day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Integer,GridNumber> numbers = new HashMap<>();
    List<Integer> rowMarks = new ArrayList<>(Arrays.asList(0,0,0,0,0));
    List<Integer> colMarks = new ArrayList<>(Arrays.asList(0,0,0,0,0));
    Integer winningNumber;

    public void addNumber(GridNumber number) {
        numbers.put(number.number, number);
    }

    public Collection<GridNumber> getNumbers() {
        return numbers.values();
    }

    public GridNumber getNumberIfExists(Integer number) {
        return numbers.get(number);
    }
}
