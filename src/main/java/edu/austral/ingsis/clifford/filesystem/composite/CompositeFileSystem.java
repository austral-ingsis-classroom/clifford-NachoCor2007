package edu.austral.ingsis.clifford.filesystem.composite;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.List;

public interface CompositeFileSystem extends FileSystem {
  Result<FileSystem> add(FileSystem toAdd);
  Result<FileSystem> remove(FileSystem toRemove);
  List<FileSystem> children();
  Result<FileSystem> getChild(String name);
}
