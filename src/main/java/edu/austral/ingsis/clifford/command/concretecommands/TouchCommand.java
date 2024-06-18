package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;
import edu.austral.ingsis.clifford.filesystem.individual.File;
import java.util.Map;

public class TouchCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public TouchCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    CompositeFileSystem currentDirectory = getCurrentDirectory();

    String name = options.get("name");
    if (name != null) {
      return createAndAddFile(currentDirectory, name);
    } else {
      return new Result<>(new Failure(), fileManager, "No name provided");
    }
  }

  private CompositeFileSystem getCurrentDirectory() {
    FileSystem currentDirectory = fileManager.cursor();
    return currentDirectory.getDirectory().value();
  }

  private Result<FileManager> createAndAddFile(CompositeFileSystem currentDirectory, String name) {
    boolean validName = validateFileName(name);
    if (validName) {
      return getFileManagerResult(currentDirectory, name);
    } else {
      return new Result<>(new Failure(), fileManager.copy(), "Invalid file name");
    }
  }

  private boolean validateFileName(String name) {
    return !name.contains("/") && !name.contains(" ");
  }

  private Result<FileManager> getFileManagerResult(
      CompositeFileSystem currentDirectory, String name) {
    Result<FileSystem> filledNewFile = getFileSystemResult(currentDirectory, name);

    if (filledNewFile.resultType().getResultType().equals("Failure")) {
      return new Result<>(new Failure(), fileManager.copy(), filledNewFile.message());
    }

    FileManager newFileManager =
        new FileManager(filledNewFile.value(), fileManager.commandFactory());

    return new Result<>(new Success(), newFileManager, "'" + name + "' file created");
  }

  private Result<FileSystem> getFileSystemResult(
      CompositeFileSystem currentDirectory, String name) {
    FileSystem newFile = new File(name, currentDirectory);

    boolean alreadyExists = fileAlreadyExists(currentDirectory, name);

    if (alreadyExists) {
      return new Result<>(new Failure(), null, "'" + name + "' file already exists");
    }

    return currentDirectory.add(newFile);
  }

  private boolean fileAlreadyExists(CompositeFileSystem currentDirectory, String name) {
    return currentDirectory.children().stream()
        .anyMatch(file -> file.name().equals(name) && !file.isDirectory());
  }
}
