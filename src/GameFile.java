import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameFile {
  final String name;
  final GameFileType type;
  final GameFile[] children;
  final Boolean isLogFile;

  GameFile(String name, GameFileType type, Boolean isLogFile, GameFile... children) {
    this.name = name;
    this.type = type;
    this.isLogFile = isLogFile;
    this.children = children;
  }

  private static LogFile fileStructureHandler(
      GameFile[] filesStructure,
      String initialDirectoryPath,
      LogFile... logFile
  ) throws IOException {
    if (!(logFile.length > 0)) {
      logFile = new LogFile[]{new LogFile(new StringBuilder(), "")};
    }

    for (GameFile file : filesStructure) {
      final String fileUrl = initialDirectoryPath + "/" + file.name;

      if (file.type == GameFileType.DIRECTORY) {
        File newDir = new File(fileUrl);

        if (!newDir.mkdir()) {
          logFile[0].logData.append("Не удалось создать папку ").append(fileUrl).append("\n");

          throw new IOException("Не удалось создать папку: " + fileUrl);
        }

        logFile[0].logData.append("Папка ").append(fileUrl).append(" создана").append("\n");
      }

      if (file.type == GameFileType.FILE) {
        File newFile = new File(fileUrl);

        try {
          if (newFile.createNewFile()) {
            logFile[0].logData.append("Файл ").append(fileUrl).append(" создан").append("\n");
          }
        } catch (IOException error) {
          logFile[0].logData.append("Не удалось создать файл ").append(fileUrl).append("\n");

          throw new IOException("При установке произошла ошибка: " + error);
        }
      }

      if (file.isLogFile) {
        logFile[0].logFileUrl = fileUrl;
      }

      if (file.children.length > 0 && file.type == GameFileType.DIRECTORY) {
        fileStructureHandler(file.children, fileUrl, logFile[0]);
      }
    }

    return logFile[0];
  }

  static public void createFilesStructure(
      GameFile[] filesStructure,
      String initialDirectoryPath
  ) throws IOException {
    LogFile logFile = fileStructureHandler(filesStructure, initialDirectoryPath);

    try (FileWriter logFileWriter = new FileWriter(logFile.logFileUrl, false)) {
      logFileWriter.write(String.valueOf(logFile.logData));
      logFileWriter.flush();
    } catch (IOException error) {
      throw new IOException("При записи лог-файла произошла ошибка: " + error);
    }
  }
}
