package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.communication.Result;

public interface CompositeFileSystem extends FileSystem {
  Result<FileSystem> add(FileSystem toAdd);
  Result<FileSystem> remove(FileSystem toRemove);
  FileSystem getChildren();
}
