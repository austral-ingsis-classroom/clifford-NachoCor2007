package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CdCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public CdCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    Result<FileSystem> newCursor = getNewCursor();

    if (newCursor.isEmpty()) {
      return new Result<>(newCursor.resultType(), fileManager, newCursor.message());
    }

    FileManager newFileManager = new FileManager(newCursor.value(), fileManager.commandFactory());
    return new Result<>(newCursor.resultType(), newFileManager, newCursor.message());
  }

  private Result<FileSystem> getNewCursor() {
    FileSystem cursor = fileManager.cursor();
    List<String> path = getPathList();

    if (path.isEmpty()) {
      return new Result<>(new Success(), cursor, "No path provided");
    }

    return iterateThroughPath(cursor, path);
  }

  private ArrayList<String> getPathList() {
    String path = options.get("path");

    if (path == null) {
      return new ArrayList<>();
    }

    return new ArrayList<>(List.of(path.split("/")));
  }

  private Result<FileSystem> iterateThroughPath(FileSystem cursor, List<String> path) {
    for (String nextDir : path) {
      if (nextDir.equals(".")) {
        continue;
      }
      if (nextDir.equals("..")) {
        cursor = cursor.parent();
        continue;
      }

      FileSystem newCursor = cursor.getChild(nextDir);
    }

    return new Result<>(new Success(), cursor, "Path found");
  }
}
