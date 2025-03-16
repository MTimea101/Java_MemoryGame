import java.io.*;
import java.util.*;

public class FileHandler {
    private final String baseFilePath; // Az alapértelmezett fájl elérési útja (pl. "results_")

    public FileHandler(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public void saveResults(String playerName, int timeElapsed, int attempts, Difficulty difficulty) {
        String filePath = getFilePath(difficulty); // Fájl az aktuális nehézségi szinthez
        List<String> results = loadResults(difficulty);
        boolean updated = false;

        for (int i = 0; i < results.size(); i++) {
            String[] parts = results.get(i).split(",");
            if (parts[0].equals(playerName)) {
                int existingTime = Integer.parseInt(parts[1]);
                if (timeElapsed < existingTime) { // Csak jobb eredmény esetén frissít
                    results.set(i, playerName + "," + timeElapsed + "," + attempts);
                }
                updated = true;
                break;
            }
        }

        if (!updated) {
            results.add(playerName + "," + timeElapsed + "," + attempts);
        }

        writeResultsToFile(results, filePath); // Mentés fájlba
    }

    public List<String> loadResults(Difficulty difficulty) {
        List<String> results = new ArrayList<>();
        String filePath = getFilePath(difficulty); // Fájl az aktuális nehézségi szinthez

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error while loading results: " + e.getMessage());
        }

        // Eredmények idő szerinti rendezése
        results.sort((a, b) -> Integer.compare(
                Integer.parseInt(a.split(",")[1]),
                Integer.parseInt(b.split(",")[1])
        ));

        return results;
    }

    public List<String[]> loadBestResults(Difficulty difficulty) {
        Map<String, String[]> bestResults = new HashMap<>();
        String filePath = getFilePath(difficulty); // Fájl az aktuális nehézségi szinthez

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String playerName = parts[0];
                    int timeElapsed = Integer.parseInt(parts[1]);
                    int attempts = Integer.parseInt(parts[2]);

                    if (!bestResults.containsKey(playerName) ||
                            Integer.parseInt(bestResults.get(playerName)[1]) > timeElapsed) {
                        bestResults.put(playerName, new String[]{playerName, String.valueOf(timeElapsed), String.valueOf(attempts)});
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while loading best results: " + e.getMessage());
        }

        // Idő szerint rendezett lista
        List<String[]> sortedResults = new ArrayList<>(bestResults.values());
        sortedResults.sort((a, b) -> Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1])));
        return sortedResults;
    }

    private void writeResultsToFile(List<String> results, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while writing results: " + e.getMessage());
        }
    }

    private String getFilePath(Difficulty difficulty) {
        return baseFilePath + difficulty.name().toLowerCase() + ".txt"; // Pl. results_easy.txt
    }

    public List<String[]> loadAllResults() {
        List<String[]> allResults = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            String filePath = getFilePath(difficulty);
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String[] resultWithDifficulty = new String[]{parts[0], parts[1], parts[2], difficulty.name()};
                    allResults.add(resultWithDifficulty);
                }
            } catch (IOException e) {
                System.err.println("Error while loading results from " + filePath + ": " + e.getMessage());
            }
        }
        return allResults;
    }

}
