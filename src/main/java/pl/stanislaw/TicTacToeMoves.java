package pl.stanislaw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicTacToeMoves {

    public static void main(String[] args) {
        List<List<int[]>> allCombinations = generateAllMoveCombinations(5);

        for (List<int[]> moves : allCombinations) {
            for (int[] move : moves) {
                System.out.print("(" + move[0] + "," + move[1] + ") ");
            }
            System.out.println();
        }

        System.out.println("Total combinations: " + allCombinations.size());
    }

    public static List<List<int[]>> generateAllMoveCombinations(int maxMoves) {
        // Initialize the board positions
        List<int[]> positions = new ArrayList<>();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                positions.add(new int[]{x, y});
            }
        }

        // Result container
        List<List<int[]>> result = new ArrayList<>();

        // Generate combinations for 1 to maxMoves
        for (int moves = 1; moves <= maxMoves; moves++) {
            generateCombinations(positions, new ArrayList<>(), result, moves);
        }

        result = result.stream()
                .filter(ints -> ints.size() == 5)
                .toList();

        return result;
    }

    private static void generateCombinations(List<int[]> positions, List<int[]> current,
                                             List<List<int[]>> result, int movesLeft) {
        if (movesLeft == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int[] position : positions) {
            if (current.contains(position)) continue; // Skip if position already used

            current.add(position);
            generateCombinations(positions, current, result, movesLeft - 1);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
