package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;

public interface Command {
  Result<FileManager> execute();
}
