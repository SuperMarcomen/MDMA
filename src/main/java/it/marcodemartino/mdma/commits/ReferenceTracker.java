package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;

import java.nio.file.Paths;

public class ReferenceTracker {

    private final References references;

    public ReferenceTracker() {
        references = new References();
    }

    public void load(FileReader fileReader) {
        String refs = fileReader.readFileAsString(Paths.get(FolderNames.REFS.getFolderName().toString(), "refs"));
        String[] lines = refs.split(System.lineSeparator());
        for (String line : lines) {
            String[] args = line.split(":");
            String hash = args[0];
            // If the path is empty, it means it is the root directory (Where MDMA was executed)
            String path = args.length > 1 ? args[1] : "";
            references.addObject(hash, path);
        }
    }

    public String getObjectPath(String hash) {
        return references.getObject(hash);
    }

    public void addBlob(String hash, Blob blob) {
        references.addObject(hash, blob.getPath());
    }

    public void addTree(String hash, Tree tree) {
        references.addObject(hash, tree.getPath());
        for (Blob blob : tree.getBlobs()) {
            addBlob(blob.getName(), blob);
        }
        for (Tree subTree : tree.getTrees()) {
            addTree(subTree.getName(), subTree);
        }
    }

    public References getReferences() {
        return references;
    }
}
