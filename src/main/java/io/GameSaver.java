package io;
import model.GameState;
import java.io.*;

public class GameSaver {
    public static void save(GameState state) {
        try {
            // 🔹 Get project directory path
            String basePath = System.getProperty("user.dir");
            File dir = new File(basePath + File.separator + "assets");
            if (!dir.exists()) dir.mkdirs();

            String savePath = dir.getAbsolutePath() + File.separator + "save.dat";

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))) {
                out.writeObject(state);
                System.out.println("✅ Game saved to: " + savePath);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
