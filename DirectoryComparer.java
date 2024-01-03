import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryComparer {

    public static void main(String[] args) throws IOException {
        File dir1 = new File("path/to/first/directory");
        File dir2 = new File("path/to/second/directory");

        compareDirectories(dir1, dir2);
    }

    private static void compareDirectories(File dir1, File dir2) throws IOException {
        List<File> javaFilesDir1 = findJavaFiles(dir1);
        List<File> javaFilesDir2 = findJavaFiles(dir2);

        for (File file1 : javaFilesDir1) {
            File file2 = new File(dir2, file1.getName());
            if (javaFilesDir2.contains(file2)) {
                compareJavaFiles(file1, file2);
            }
        }
    }

    private static List<File> findJavaFiles(File dir) {
        return Arrays.stream(dir.listFiles())
                     .filter(file -> file.getName().endsWith(".java"))
                     .collect(Collectors.toList());
    }

    private static void compareJavaFiles(File file1, File file2) throws IOException {
        List<String> lines1 = Files.lines(file1.toPath())
                                   .filter(line -> !line.trim().startsWith("package"))
                                   .collect(Collectors.toList());

        List<String> lines2 = Files.lines(file2.toPath())
                                   .filter(line -> !line.trim().startsWith("package"))
                                   .collect(Collectors.toList());

        if (!lines1.equals(lines2)) {
            System.out.println("Different Java File (excluding package difference): " + file1.getName());
        }
    }
}

