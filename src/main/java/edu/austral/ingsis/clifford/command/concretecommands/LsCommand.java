package edu.austral.ingsis.clifford.command.concretecommands;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    String order = options.get("order");

    if (order == null) {
      return children.stream().map(FileSystem::name).collect(Collectors.joining(" "));
    }

    return listChildrenInOrder(children, order.equals("asc"));
  }

  private String listChildrenInOrder(List<FileSystem> children, boolean asc) {
    Stream<FileSystem> childrenStream = children.stream();

    if (asc) {
      return childrenStream
          .sorted(Comparator.comparing(FileSystem::name))
          .map(FileSystem::name)
          .collect(Collectors.joining(" "));
    } else {
      return childrenStream
          .sorted(Comparator.comparing(FileSystem::name).reversed())
          .map(FileSystem::name)
          .collect(Collectors.joining(" "));
    }
  }
}
