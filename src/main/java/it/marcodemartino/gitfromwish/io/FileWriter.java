package it.marcodemartino.gitfromwish.io;

import java.nio.file.Path;

public interface FileWriter {

    void writeFile(Path path, byte[] bytes);
}
