package model;
import java.io.*;
import java.util.*;

public class Dictionary {
    private final List<String> words = new ArrayList<>();

    public Dictionary(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 5 && line.matches("[a-zA-Z]+")) {
                    words.add(line.toUpperCase());
                }
            }
            System.out.println("✅ Loaded " + words.size() + " words from dictionary.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toUpperCase());
    }

    public String getRandomWord() {
        if (words.isEmpty()) return "APPLE";
        Random r = new Random();
        return words.get(r.nextInt(words.size()));
    }
}
