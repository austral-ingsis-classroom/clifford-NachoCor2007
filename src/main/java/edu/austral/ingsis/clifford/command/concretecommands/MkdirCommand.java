package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.Directory;

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
      return getFileManagerResult(currentDirectory, name);
    } else {
      return new Result<>(new Failure(), fileManager.copy(), "Invalid directory name");
    }
  }

  private Result<FileManager> getFileManagerResult(CompositeFileSystem currentDirectory, String name) {
    Result<FileSystem> filledNewDirectory = getFileSystemResult(currentDirectory, name);

    if (filledNewDirectory.isEmpty()) {
      return new Result<>(new Failure(), fileManager.copy(), filledNewDirectory.message());
    }

    FileManager newFileManager = new FileManager(filledNewDirectory.value(), fileManager.commandFactory());

    return new Result<>(new Success(), newFileManager, "'" + name + "' directory created");
  }

  private Result<FileSystem> getFileSystemResult(CompositeFileSystem currentDirectory, String name) {
    CompositeFileSystem newDirectory = new Directory(name, currentDirectory.children(), currentDirectory);

    boolean alreadyExists = dirAlreadyExists(currentDirectory, name);

    if (alreadyExists) {
      return new Result<>(new Failure(), null, "'" + name + "' directory already exists");
    }

    return currentDirectory.add(newDirectory);
  }

  private boolean dirAlreadyExists(CompositeFileSystem currentDirectory, String name) {
    return currentDirectory.children()
        .stream()
        .anyMatch(fileSystem -> fileSystem.name().equals(name) && fileSystem.isDirectory());
  }

  private boolean validateDirName(String name) {
    return !name.contains(" ") && !name.contains("/");
  }
}
