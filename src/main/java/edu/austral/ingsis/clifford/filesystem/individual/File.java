package edu.austral.ingsis.clifford.filesystem.individual;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;

public record File(String name) implements FileSystem {
  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public Result<CompositeFileSystem> getDirectory() {
    return new Result<>(new Failure(), null, "This is a file, not a directory");
  }

  @Override
  public FileSystem copy() {
    return new File(name());
  }
}
