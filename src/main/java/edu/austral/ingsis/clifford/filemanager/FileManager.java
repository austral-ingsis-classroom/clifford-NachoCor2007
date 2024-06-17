package edu.austral.ingsis.clifford.filemanager;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.composite.Directory;

import java.util.List;

public record FileManager(FileSystem cursor, CommandFactory commandFactory) {
  public FileManager(CommandFactory commandFactory) {
    this(new Directory("/", List.of(), null), commandFactory);
  }

  @Override
  public FileSystem cursor() {
    return cursor.copy();
  }

  public Result<FileManager> run(String command) {
    Result<Command> commandResult = getCommand(command);

    if (commandResult.isEmpty()) {
      return new Result<>(commandResult.resultType(), copy(), commandResult.message());
    }

    Command commandToExecute = commandResult.value();
    return commandToExecute.execute();
  }

  private Result<Command> getCommand(String command) {
    return commandFactory.getCommand(this, command);
  }

  public FileManager copy() {
    return new FileManager(cursor(), commandFactory());
  }
}
