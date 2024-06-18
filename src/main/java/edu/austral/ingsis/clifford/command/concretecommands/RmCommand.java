package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;
import java.util.Map;

public class RmCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public RmCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    return updatedFileManager();
  }

  private Result<FileManager> updatedFileManager() {
    Result<FileSystem> modFileSystem = updatedFileSystem();

    if (modFileSystem.isEmpty()) {
      return new Result<>(modFileSystem.resultType(), fileManager.copy(), modFileSystem.message());
    }

    FileManager newFileManager =
        new FileManager(modFileSystem.value(), fileManager.commandFactory());
    return new Result<>(modFileSystem.resultType(), newFileManager, modFileSystem.message());
  }

  private Result<FileSystem> updatedFileSystem() {
    CompositeFileSystem currentDirectory = fileManager.cursor().getDirectory().value();
    String name = options.get("name");
    boolean recursive = Boolean.parseBoolean(options.get("recursive"));

    return removeFile(currentDirectory, name, recursive);
  }

  private Result<FileSystem> removeFile(
      CompositeFileSystem currentDirectory, String name, boolean recursive) {
    Result<FileSystem> fileToRemove = currentDirectory.getChild(name);

    if (fileToRemove.isEmpty()) {
      return new Result<>(fileToRemove.resultType(), currentDirectory, fileToRemove.message());
    }

    if (fileToRemove.value().isDirectory() && !recursive) {
      return new Result<>(
          new Failure(), currentDirectory, "Cannot remove '" + name + "', is a directory");
    }

    return currentDirectory.remove(fileToRemove.value());
  }
}
