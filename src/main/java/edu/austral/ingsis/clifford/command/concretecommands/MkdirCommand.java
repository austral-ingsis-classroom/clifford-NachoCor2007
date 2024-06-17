package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.Directory;
import edu.austral.ingsis.clifford.filesystem.individual.File;

import java.util.Map;

public class MkdirCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public MkdirCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    CompositeFileSystem currentDirectory = getCurrentDirectory();

    String name = options.get("name");
    if (name != null) {
      return createAndAddDirectory(currentDirectory, name);
    } else {
      return new Result<>(new Failure(), fileManager, "No name provided");
    }
  }

  private CompositeFileSystem getCurrentDirectory() {
    FileSystem currentDirectory = fileManager.cursor();
    return currentDirectory.getDirectory().value();
  }

  private Result<FileManager> createAndAddDirectory(CompositeFileSystem currentDirectory, String name) {
    boolean validName = validateDirName(name);
    if (validName) {
      Directory newDirectory = new Directory(name, currentDirectory.children(), currentDirectory);
      currentDirectory.add(newDirectory);
      return new Result<>(new Success(), fileManager.copy(), name + " directory created");
    } else {
      return new Result<>(new Failure(), fileManager.copy(), "Invalid directory name");
    }
  }

  private boolean validateDirName(String name) {
    return !name.contains(" ") && !name.contains("/");
  }
}
