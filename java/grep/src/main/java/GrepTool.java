import java.util.*;
import java.nio.file.*;

class GrepTool {
    String grep(String keyword, List<String> options, List<String> files) {
        var result = new ArrayList<String>();
        var isCaseInsensitive = options.contains("-i");
        var isPrintLineNumber = options.contains("-n");
        var isPrintOnlyFileNames = options.contains("-l");
        var isInvertProgram = options.contains("-v");
        var isMatchWholeLine = options.contains("-x");

        var isMultipleFiles = files.size() > 1;

        for (var fileName : files) {
            var path = Path.of(fileName);
            try {
                var fileString = Files.readString(path);
                var fileLines = fileString.lines().toList();
                for (var i = 0; i < fileLines.size(); i++) {
                    var line = isCaseInsensitive ? fileLines.get(i).toUpperCase() : fileLines.get(i);
                    var wordToCheck = isCaseInsensitive ? keyword.toUpperCase() : keyword;
                    var containsKeyWord = isMatchWholeLine ? line.equals(wordToCheck) : line.contains(keyword);
                    if ((!isInvertProgram && containsKeyWord) || (isInvertProgram && !containsKeyWord)) {
                        var lineNumber = i + 1;

                        if (isPrintOnlyFileNames) {
                            if (!result.contains(fileName)) result.add(fileName);
                        } else if (isPrintLineNumber && isMultipleFiles)
                            result.add(fileName + ":" + lineNumber + ":" + fileLines.get(i));
                        else if (isPrintLineNumber) result.add(lineNumber + ":" + fileLines.get(i));
                        else if (isMultipleFiles) result.add(fileName + ":" + fileLines.get(i));
                        else result.add(fileLines.get(i));
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return String.join("\n", result);
    }
}