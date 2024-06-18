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
    StringBuilder path = new StringBuilder(cursor.name());

    while (cursor.parent() != null) {
      cursor = cursor.parent();
      path.insert(0, cursor.name() + "/");
    }

    return path.toString();
  }
}
