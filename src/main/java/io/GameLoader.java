package io;
import model.GameState;
import java.io.*;

public class GameLoader {
    public static GameState load() {
        String basePath = System.getProperty("user.dir");
        String savePath = basePath + File.separator + "assets" + File.separator + "save.dat";

        File file = new File(savePath);
        if (!file.exists()) {
            System.out.println("❌ No saved game found at: " + file.getAbsolutePath());
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            GameState state = (GameState) in.readObject();
            System.out.println("✅ Game loaded from: " + file.getAbsolutePath());
            return state;
        } catch (Exception e) {
            System.out.println("⚠️ Error loading save file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
