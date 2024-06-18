package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class PwdCommand implements Command {
  private final FileManager fileManager;

  public PwdCommand(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  @Override
  public Result<FileManager> execute() {
    String path = writePath();

    return new Result<>(new Success(), fileManager, path);
  }

  private String writePath() {
    FileSystem cursor = fileManager.cursor();

    if (cursor.parent() == null) {
      return "/";
    }

    StringBuilder path = new StringBuilder();

    while (cursor.parent() != null) {
      path.insert(0, "/" + cursor.name());
      cursor = cursor.parent();
    }

    return path.toString();
  }
}
