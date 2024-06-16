package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import java.util.Map;

public class LsCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public LsCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    return null;
  }
}
