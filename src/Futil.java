package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        try {
            Files.walkFileTree(Paths.get(dirName), new CustomFileVisitor(resultFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
