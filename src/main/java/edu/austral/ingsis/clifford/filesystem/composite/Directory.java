package edu.austral.ingsis.clifford.filesystem.composite;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.ArrayList;
import java.util.List;

public record Directory(String name, List<FileSystem> children, FileSystem parent) implements CompositeFileSystem {
  @Override
  public Result<FileSystem> add(FileSystem toAdd) {
    List<FileSystem> newChildren = copyChildren();
    newChildren.add(toAdd);
    return new Result<>(new Success(), createNewDirectory(newChildren), toAdd.name() + " directory created");
  }

  @Override
  public Result<FileSystem> remove(FileSystem toRemove) {
    List<FileSystem> newChildren = copyChildren();

    try {
      newChildren.remove(toRemove);
      return new Result<>(new Success(), createNewDirectory(newChildren), toRemove.name() + " removed");
    } catch (Exception e) {
      return new Result<>(new Failure(), copy(), toRemove.name() + " not found");
    }
  }

  private Directory createNewDirectory(List<FileSystem> newChildren) {
    if (name().equals("/")) {
      return new Directory("/", newChildren, null);
    }
    return new Directory(name(), newChildren, parent());
  }

  @Override
  public List<FileSystem> children() {
    return copyChildren();
  }

  @Override
  public Result<FileSystem> getChild(String name) {
    for (FileSystem child : children) {
      if (child.name().equals(name)) {
        return new Result<>(new Success(), child, "Found " + name);
      }
    }

    return new Result<>(new Failure(), copy(), name + " not found");
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
    return createNewDirectory(children());
  }
}
