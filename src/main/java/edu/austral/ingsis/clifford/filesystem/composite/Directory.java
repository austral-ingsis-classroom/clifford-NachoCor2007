package edu.austral.ingsis.clifford.filesystem.composite;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.ArrayList;
import java.util.List;

public record Directory(String name, List<FileSystem> children) implements CompositeFileSystem {
  @Override
  public Result<CompositeFileSystem> add(FileSystem toAdd) {
    List<FileSystem> newChildren = copyChildren();
    newChildren.add(toAdd);
    return new Result<>(new Success(), new Directory(name(), newChildren), toAdd.name() + " directory created");
  }

  @Override
  public Result<CompositeFileSystem> remove(FileSystem toRemove) {
    List<FileSystem> newChildren = copyChildren();

    try {
      newChildren.remove(toRemove);
      return new Result<>(new Success(), new Directory(name(), newChildren), toRemove.name() + " removed");
    } catch (Exception e) {
      return new Result<>(new Failure(), copyDirectory(), toRemove.name() + " not found");
    }
  }

  @Override
  public List<FileSystem> children() {
    return copyChildren();
  }

  private List<FileSystem> copyChildren() {
    List<FileSystem> copiedChildren = new ArrayList<>();

    for (FileSystem child : children) {
      copiedChildren.add(child.copy());
    }

    return copiedChildren;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public Result<CompositeFileSystem> getDirectory() {
    return new Result<>(new Success(), copyDirectory(), "Directory");
  }

  @Override
  public FileSystem copy() {
    return copyDirectory();
  }

  private CompositeFileSystem copyDirectory() {
    return new Directory(name(), children());
  }
}
