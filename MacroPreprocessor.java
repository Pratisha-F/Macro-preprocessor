import java.util.HashMap;
import java.util.Map;

public class MacroPreprocessor {
    public static String preprocess(String input) {
        Map<String, String> macros = new HashMap<>();

        // Step 1: Process macro definitions
        StringBuilder output = new StringBuilder();
        String[] lines = input.split("\\r?\\n");
        for (String line : lines) {
            if (line.startsWith("#define")) {
                String[] parts = line.split("\\s+", 3);
                if (parts.length == 3) {
                    String macroName = parts[1];
                    String macroValue = parts[2];
                    macros.put(macroName, macroValue);
                }
            } else {
                output.append(line).append("\n");
            }
        }

        // Step 2: Expand macros
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            StringBuilder expandedLine = new StringBuilder();
            for (String token : tokens) {
                if (macros.containsKey(token)) {
                    expandedLine.append(macros.get(token));
                } else {
                    expandedLine.append(token);
                }
                expandedLine.append(" ");
            }
            output.append(expandedLine.toString().trim()).append("\n");
        }

        return output.toString();
    }

    public static void main(String[] args) {
        String sourceCode = "#define PI 3.14159\n\npublic class Main {\n    public static void main(String[] args) {\n        double radius = 5.0;\n        double area = PI * radius * radius;\n        System.out.println(area);\n    }\n}";
        String preprocessedCode = preprocess(sourceCode);
        System.out.println(preprocessedCode);
    }
}
