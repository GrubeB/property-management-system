package pl.app.common.util;


import java.util.Optional;
import java.util.UUID;

public class FileUtils {

    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String getFileName(String filename) {
        if (filename.contains(".")) {
            return filename.substring(0, filename.lastIndexOf("."));
        } else {
            return filename;
        }
    }

    public static String getUniqueFileName(String filename) {
        return FileUtils.getFileName(filename) + UUID.randomUUID() + '.' + FileUtils.getExtension(filename).orElse("");
    }

}
