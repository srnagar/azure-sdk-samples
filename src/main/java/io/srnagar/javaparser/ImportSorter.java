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

    public static void sortImports(InputStream input, File outputFile) throws IOException {
        // Parse the Java file from InputStream
        String content = new String(input.readAllBytes());
        CompilationUnit cu = StaticJavaParser.parse(content);

        // Get all imports
        NodeList<ImportDeclaration> imports = cu.getImports();

        if (imports.isEmpty()) {
            System.out.println("No imports to sort");
            return;
        }

        // Sort imports alphabetically
        List<ImportDeclaration> sortedImports = imports.stream()
            .sorted(Comparator.comparing(imp -> imp.getNameAsString()))
            .collect(Collectors.toList());

        // Clear existing imports
        cu.getImports().clear();

        // Add sorted imports back
        for (ImportDeclaration importDecl : sortedImports) {
            cu.addImport(importDecl);
        }

        // Write the sorted code back to file
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(cu.toString());
        }

        System.out.println("Imports sorted successfully and updated file written to " + outputFile.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException {
        // Load ImportSorter.java from resources using class loader
        ClassLoader classLoader = ImportSorter.class.getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("ImportSorter.java")) {
            if (input == null) {
                System.out.println("Resource not found: ImportSorter.java");
                return;
            }
            // Output file path in resources directory
            File outputDirectory = Files.createTempDirectory("output").toFile();
            File outputFile = new File(outputDirectory, "ImportSorterUpdated.java");
            sortImports(input, outputFile);
        }
    }
}
