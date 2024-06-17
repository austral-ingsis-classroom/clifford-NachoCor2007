package edu.austral.ingsis.clifford.filesystem.composite;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.List;

public interface CompositeFileSystem extends FileSystem {
  Result<CompositeFileSystem> add(FileSystem toAdd);
  Result<CompositeFileSystem> remove(FileSystem toRemove);
  List<FileSystem> children();
}
