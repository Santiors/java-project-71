package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.DataFormatException;


public class Differ {
    public static String generate(String inputPath1, String inputPath2, String formatName) throws Exception {
        Path filePath1 = getAbsolutePath(inputPath1);
        Path filePath2 = getAbsolutePath(inputPath2);

        String dataFromFile1 = getDataFromFile(filePath1);
        String dataFromFile2 = getDataFromFile(filePath2);

        String formatOfFile1 = getFormat(inputPath1);
        String formatOfFile2 = getFormat(inputPath2);

        Map<String, Object> dataMap1 = Parser.parse(dataFromFile1);
        Map<String, Object> dataMap2 = Parser.parse(dataFromFile2);
        Map<String, String> resultMap;

        if (formatOfFile1.equals(formatOfFile2)) {
            resultMap = compareData(dataMap1, dataMap2);
        } else {
            throw new DataFormatException();
        }

        return getDefaultOutputFormat(resultMap, dataMap1, dataMap2);
    }

    public static String generate(String inputPath1, String inputPath2) throws Exception {
        String defaultFormat = "stylish";
        return generate(inputPath1, inputPath2, defaultFormat);
    }

    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static String getDataFromFile(Path file) throws IOException {
        return Files.readString(file);
    }

    private static String getFormat(String format) {
        String resultFormat = "";

        if (format.endsWith(".json")) {
            resultFormat = "json";
        }
        return resultFormat;
    }

    private static Map<String, String> compareData(Map<String, Object> dataMap1, Map<String, Object> dataMap2) {
        Map<String, String> resultMap = new TreeMap<>();

        Set<String> keys = new TreeSet<>(dataMap1.keySet());
        keys.addAll(dataMap2.keySet());

        for (String key : keys) {
            if (!dataMap1.containsKey(key)) {
                resultMap.put(key, "added");
            } else if (!dataMap2.containsKey(key)) {
                resultMap.put(key, "deleted");
            } else if (!(dataMap2.get(key).equals(dataMap1.get(key)))) {
                resultMap.put(key, "changed");
            } else {
                resultMap.put(key, "unchanged");
            }
        }
        return resultMap;
    }

    private static String getDefaultOutputFormat(Map<String, String> dataForFormat, Map<String, Object> dataMap1,
                                                 Map<String, Object> dataMap2) {
        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, String> elem : dataForFormat.entrySet()) {
            if (elem.getValue().equals("added")) {
                result.append(" + ").append(elem.getKey()).append(": ")
                        .append(dataMap2.get(elem.getKey())).append("\n");
            } else if (elem.getValue().equals("deleted")) {
                result.append(" - ").append(elem.getKey()).append(": ")
                        .append(dataMap1.get(elem.getKey())).append("\n");
            } else if (elem.getValue().equals("changed")) {
                result.append(" - ").append(elem.getKey()).append(": ")
                        .append(dataMap1.get(elem.getKey())).append("\n")
                        .append(" + ").append(elem.getKey()).append(": ")
                        .append(dataMap2.get(elem.getKey())).append("\n");
            } else {
                result.append("   ").append(elem.getKey()).append(": ")
                        .append(dataMap2.get(elem.getKey())).append("\n");
            }
        }
        result.append("}\n");
        return result.toString();
    }
}