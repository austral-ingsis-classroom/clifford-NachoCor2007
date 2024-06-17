package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LsCommand implements Command {
  private final FileManager fileManager;
  private final Map<String, String> options;

  public LsCommand(FileManager fileManager, Map<String, String> options) {
    this.fileManager = fileManager;
    this.options = options;
  }

  @Override
  public Result<FileManager> execute() {
    CompositeFileSystem currentDirectory = getCurrentDirectory();
    List<FileSystem> children = currentDirectory.children();

    if (children.isEmpty()) {
      return new Result<>(new Success(), fileManager, "");
    }

    String listedChildren = listChildren(children);

    return new Result<>(new Success(), fileManager, listedChildren);
  }

  private CompositeFileSystem getCurrentDirectory() {
    FileSystem currentDirectory = fileManager.cursor();
    return currentDirectory.getDirectory().value();
  }

  private String listChildren(List<FileSystem> children) {
    return children.stream()
        .map(FileSystem::name)
        .collect(Collectors.joining(" "));
  }
}
