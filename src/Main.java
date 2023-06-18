import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    GameFile[] mainDirectory = {
        new GameFile("Main.java", GameFileType.FILE, false),
        new GameFile("Utils.java", GameFileType.FILE, false),
    };
    GameFile[] srcDirectory = {
        new GameFile("main", GameFileType.DIRECTORY, false, mainDirectory),
        new GameFile("test", GameFileType.DIRECTORY, false)
    };
    GameFile[] resDirectory = {
        new GameFile("drawables", GameFileType.DIRECTORY, false),
        new GameFile("vectors", GameFileType.DIRECTORY, false),
        new GameFile("icons", GameFileType.DIRECTORY, false)
    };
    GameFile[] tempDirectory = {
        new GameFile("temp.txt", GameFileType.FILE, true),
    };
    GameFile[] filesStructure = {
        new GameFile("src", GameFileType.DIRECTORY, false, srcDirectory),
        new GameFile("res", GameFileType.DIRECTORY, false, resDirectory),
        new GameFile("savegames", GameFileType.DIRECTORY, false),
        new GameFile("temp", GameFileType.DIRECTORY, false, tempDirectory),
    };

    GameFile.createFilesStructure(filesStructure,"/Users/alekseyogoltsov/Games");

  }
}