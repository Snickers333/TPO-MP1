package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {
    FileChannel fileChannelOut;

    public CustomFileVisitor(String resultFileName) {
        try {
            fileChannelOut = FileChannel.open(Paths.get(resultFileName), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        try (FileChannel fileChannelin = FileChannel.open(file, StandardOpenOption.READ)) {
            ByteBuffer buff = ByteBuffer.allocate(1000);
            if (fileChannelin.read(buff) > 0) {
                buff.flip();
                String fileContent = Charset.forName("Cp1250").decode(buff).toString();
                ByteBuffer outBuffer = StandardCharsets.UTF_8.encode(fileContent);
                fileChannelOut.write(outBuffer);
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
