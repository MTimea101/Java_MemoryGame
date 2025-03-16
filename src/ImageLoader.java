import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    public static List<ImageIcon> loadImages(Difficulty difficulty) {
        String folderPath = "";
        switch (difficulty) {
            case EASY:
                folderPath = "images_4x4";
                break;
            case MEDIUM:
                folderPath = "images_6x6";
                break;
            case HARD:
                folderPath = "images_8x8";
                break;
        }

        List<ImageIcon> images = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                for (File file : files) {
                    images.add(new ImageIcon(file.getAbsolutePath()));
                }
            }
        } else {
            System.err.println("Error: Folder not found - " + folderPath);
        }

        return images;
    }
}