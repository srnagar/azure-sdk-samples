package io.srnagar.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImportSorter {

    public static void sortImports(InputStream javaFileStream) throws IOException {
        // Parse the Java file
        String content = new String(javaFileStream.readAllBytes());
        CompilationUnit cu = StaticJavaParser.parse(content);
        // Get all imports
        NodeList<ImportDeclaration> imports = cu.getImports();
        if (imports.isEmpty()) {
            System.out.println("No imports to sort");
            return;
        }
        // Sort imports alphabetically
        List<ImportDeclaration> sortedImports = imports.stream().sorted(Comparator.comparing(imp -> imp.getNameAsString())).collect(Collectors.toList());
        // Clear existing imports
        cu.getImports().clear();
        // Add sorted imports back
        for (ImportDeclaration importDecl : sortedImports) {
            cu.addImport(importDecl);
        }
        // Write the sorted code back to file
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write(cu.toString());
        }
        System.out.println("Imports sorted successfully!");
    }

    public static void main(String[] args) throws IOException {
        // Example usage
        if (args.length < 1) {
            System.out.println("Usage: java ImportSorter <path-to-java-file>");
            return;
        }
        InputStream resourceAsStream = ImportSorter.class.getResourceAsStream("ImportSorter.java");
        if (resourceAsStream == null) {
            System.out.println("File not found: " + args[0]);
            return;
        }
        // Choose one of the sorting methods:
        // Simple alphabetical sort
        sortImports(resourceAsStream);
        // Or with static/regular grouping
        // sortImportsWithGrouping(javaFile);
        // Or with custom order (java.*, javax.*, others)
        // sortImportsCustomOrder(javaFile);
    }
}
