package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filesystem.composite.CompositeFileSystem;

public interface FileSystem {
  String name();
  boolean isDirectory();
  Result<CompositeFileSystem> getDirectory();
  FileSystem copy();
}
