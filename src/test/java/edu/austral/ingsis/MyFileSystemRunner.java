package edu.austral.ingsis;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import java.util.ArrayList;
import java.util.List;

public class MyFileSystemRunner implements FileSystemRunner {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> result = new ArrayList<>();
    FileManager tempFileManager = fileManager;

    for (String command : commands) {
      Result<FileManager> runResult = tempFileManager.run(command);
      tempFileManager = runResult.value();
      result.add(runResult.message());
    }

    return result;
  }
}
