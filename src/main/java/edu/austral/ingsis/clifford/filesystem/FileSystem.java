package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.communication.Result;

public interface FileSystem {
  String getName();
  boolean isDirectory();
  Result<CompositeFileSystem> getDirectory();
  FileSystem copy();
}
