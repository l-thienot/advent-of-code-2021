package day04;

import org.apache.commons.lang3.StringUtils;
import utils.InputReadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bingo {
    public static void main(String[] args) throws IOException {
        int winnerScore = computeWinnerScore(InputReadUtils.getFileContentForDay("04"));
        System.out.println("Winner score : " + winnerScore);
        int looserScore = computeLooserScore(InputReadUtils.getFileContentForDay("04"));
        System.out.println("Looser score : " + looserScore);
    }

    private static int computeWinnerScore(Stream<String> gameData) {
        List<String> gameBoardAndNumbers = gameData.collect(Collectors.toList());
        List<Integer> numbers = getNumbers(gameBoardAndNumbers);
        List<Board> boards = getBoards(gameBoardAndNumbers.subList(2, gameBoardAndNumbers.size()));

        Board winner = playUntilFirstWins(numbers, boards);

        return getFinalScore(winner);
    }

    private static int getFinalScore(Board winner) {
        return winner.getNumbers().stream().filter(nb -> !nb.marked).map(nb -> nb.number).reduce(Integer::sum).orElse(0) * winner.winningNumber;
    }

    private static int computeLooserScore(Stream<String> gameData) {
        List<String> gameBoardAndNumbers = gameData.collect(Collectors.toList());
        List<Integer> numbers = getNumbers(gameBoardAndNumbers);
        List<Board> boards = getBoards(gameBoardAndNumbers.subList(2, gameBoardAndNumbers.size()));

        Board looser = playUntilLastWins(numbers, boards);

        return getFinalScore(looser);
    }

    private static List<Board> getBoards(List<String> rawBoards) {
        List<Board> boards = new ArrayList<>();
        if (rawBoards.isEmpty()) return boards;

        Board currentBoard = new Board();
        int rowNb = 0;
        for (String data : rawBoards) {
            if (data.isBlank()) {
                boards.add(currentBoard);
                currentBoard = new Board();
                rowNb = 0;
            } else {
                List<String> row = Arrays.asList(StringUtils.normalizeSpace(data).split(" "));
                for (int colNb = 0; colNb < 5; colNb++) {
                    int nb = Integer.parseInt(row.get(colNb));
                    currentBoard.addNumber(new GridNumber(nb, rowNb, colNb));
                }
                rowNb++;
            }
        }
        boards.add(currentBoard);
        return boards;
    }

    private static Board playUntilFirstWins(List<Integer> numbers, List<Board> boards) {
        for (Integer number : numbers) {
            for (Board board : boards) {
                GridNumber gridNumber = board.getNumberIfExists(number);
                if (gridNumber != null) {
                    gridNumber.marked = true;
                    int markedNbOnThisCol = board.colMarks.get(gridNumber.col) + 1;
                    int markedNbOnThisRow = board.rowMarks.get(gridNumber.row) + 1;
                    board.colMarks.set(gridNumber.col, markedNbOnThisCol);
                    board.rowMarks.set(gridNumber.row, markedNbOnThisRow);

                    if (markedNbOnThisCol == 5 || markedNbOnThisRow == 5) {
                        board.winningNumber = number;
                        return board;
                    }
                }
            }
        }

        throw new IllegalStateException("no winner");
    }


    private static Board playUntilLastWins(List<Integer> numbers, List<Board> boards) {
        for (Integer number : numbers) {
            List<Board> boardsForThisTurn = List.copyOf(boards);
            for (Board board : boardsForThisTurn) {
                GridNumber gridNumber = board.getNumberIfExists(number);
                if (gridNumber != null) {
                    gridNumber.marked = true;
                    int markedNbOnThisCol = board.colMarks.get(gridNumber.col) + 1;
                    int markedNbOnThisRow = board.rowMarks.get(gridNumber.row) + 1;
                    board.colMarks.set(gridNumber.col, markedNbOnThisCol);
                    board.rowMarks.set(gridNumber.row, markedNbOnThisRow);

                    if (markedNbOnThisCol == 5 || markedNbOnThisRow == 5) {
                        if (boards.size() > 1) {
                            boards.remove(board);
                        } else {
                            board.winningNumber = number;
                            return board;
                        }
                    }
                }
            }
        }

        throw new IllegalStateException("no winner");
    }
    private static List<Integer> getNumbers(List<String> gameBoardAndNumbers) {
        return Arrays.stream(gameBoardAndNumbers.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
