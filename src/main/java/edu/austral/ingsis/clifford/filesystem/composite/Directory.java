package edu.austral.ingsis.clifford.filesystem.composite;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.ResultType;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.ArrayList;
import java.util.List;

public record Directory(String name, List<FileSystem> children, FileSystem parent) implements CompositeFileSystem {

  @Override
  public Result<FileSystem> add(FileSystem toAdd) {
    children.add(toAdd);
    return new Result<>(new Success(), this, "'" + toAdd.name() + "' directory created");
  }

  @Override
  public Result<FileSystem> remove(FileSystem toRemove) {
    boolean removeStatus = children.remove(toRemove);

    ResultType resultType = removeStatus ? new Success() : new Failure();
    String message = removeStatus ? "'" + toRemove.name() + "' removed" : "'" + toRemove.name() + "' not found";
    return new Result<>(resultType, this, message);
  }

  @Override
  public Result<FileSystem> getChild(String name) {
    for (FileSystem child : children) {
      if (child.name().equals(name)) {
        return new Result<>(new Success(), child, "Found '" + name + "'");
      }
    }

    return new Result<>(new Failure(), null, "'" + name + "' not found");
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public Result<CompositeFileSystem> getDirectory() {
    return new Result<>(new Success(), this, "Directory");
  }

  @Override
  public FileSystem copy() {
    return new Directory(name(), children(), parent());
  }
}
