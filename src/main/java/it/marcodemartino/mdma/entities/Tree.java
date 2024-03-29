package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree extends MDMAPathEntity {

    private final Map<String, Blob> blobs;
    private final Map<String, Tree> trees;

    public Tree(String name, String path, List<Blob> blobsList, List<Tree> treesList) {
        super(name, path);
        blobs = new HashMap<>();
        blobsList.forEach(blob -> blobs.put(blob.getName(), blob));
        trees = new HashMap<>();
        treesList.forEach(tree -> trees.put(tree.getName(), tree));
    }

    @Override
    public String print() {
        return print(getPath(), blobs.values(), trees.values());
    }

    public static String generateHash(Hashing hashing, String path, Collection<Blob> blobsList, Collection<Tree> treesList) {
        return hashing.hash(print(path, blobsList, treesList).getBytes());
    }

    private static String print(String path, Collection<Blob> blobsList, Collection<Tree> treesList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path).append(System.lineSeparator());
        blobsList.forEach(blob -> stringBuilder.append(blob.print()).append(System.lineSeparator()));
        treesList.forEach(tree -> stringBuilder.append(tree.flatPrint()).append(System.lineSeparator()));
        return stringBuilder.toString().trim();
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }

    private String flatPrint() {
        return "tree:" + name;
    }

    public String getName() {
        return name;
    }

    public void addBlob(Blob blob) {
        blobs.put(blob.getName(), blob);
    }

    public Blob getBlob(String name) {
        return blobs.get(name);
    }

    public Collection<Blob> getBlobs() {
        return blobs.values();
    }

    public void addTree(Tree tree) {
        trees.put(tree.getName(), tree);
    }

    public Tree getTree(String name) {
        return trees.get(name);
    }

    public Collection<Tree> getTrees() {
        return trees.values();
    }
}
