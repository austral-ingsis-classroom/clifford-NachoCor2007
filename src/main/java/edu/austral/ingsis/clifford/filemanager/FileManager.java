package edu.austral.ingsis.clifford.filemanager;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.Directory;
import java.util.ArrayList;

public record FileManager(FileSystem cursor, CommandFactory commandFactory) {
  public FileManager(CommandFactory commandFactory) {
    this(new Directory("/", new ArrayList<>(), null), commandFactory);
  }

  @Override
  public FileSystem cursor() {
    return cursor;
  }

  public Result<FileManager> run(String command) {
    Result<Command> commandResult = getCommand(command);

    if (commandResult.isEmpty()) {
      return new Result<>(commandResult.resultType(), copy(), commandResult.message());
    }

    Command commandToExecute = commandResult.value();
    Result<FileManager> newFileManager = commandToExecute.execute();
    return newFileManager;
  }

  private Result<Command> getCommand(String command) {
    return commandFactory.getCommand(this, command);
  }

  public FileManager copy() {
    return new FileManager(cursor(), commandFactory());
  }
}
