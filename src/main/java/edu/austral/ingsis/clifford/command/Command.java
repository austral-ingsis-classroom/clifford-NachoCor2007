package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filemanager.FileManager;

import java.util.List;

public interface Command {
  FileManager execute(FileManager fileManager, List<String> params);
}
