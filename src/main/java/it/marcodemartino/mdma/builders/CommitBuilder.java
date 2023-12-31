package it.marcodemartino.mdma.builders;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class CommitBuilder extends Builder<Commit> {

    private final Builder<Tree> treeBuilder;
    private String author;
    private LocalDateTime currentDateTime;

    public CommitBuilder(FileReader fileReader, Hashing hashing, Builder<Tree> treeBuilder) {
        super(fileReader, hashing);
        this.treeBuilder = treeBuilder;
        this.author = "";
        this.currentDateTime = LocalDateTime.now();
    }

    @Override
    public Commit build(Path path) {
        Tree mainTree = treeBuilder.build(path);
        currentDateTime = LocalDateTime.now();
        return new Commit(
                Commit.generateHash(hashing, author, currentDateTime, mainTree),
                mainTree,
                author,
                currentDateTime
        );
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
