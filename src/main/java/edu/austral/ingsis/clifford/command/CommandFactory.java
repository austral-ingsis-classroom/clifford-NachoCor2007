package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.command.concretecommands.*;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.communication.commtype.Failure;
import edu.austral.ingsis.clifford.communication.commtype.Success;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import java.util.List;
import java.util.Map;

public class CommandFactory {
  private final Parser parser;

  public CommandFactory(Parser parser) {
    this.parser = parser;
  }

  public Result<Command> getCommand(FileManager fileManager, String command) {
    List<String> parsedCommand = parseCommand(command);

    if (noCommandProvided(parsedCommand)) {
      return new Result<>(new Failure(), null, "No command provided");
    }

    return createCommand(fileManager, parsedCommand);
  }

  private boolean noCommandProvided(List<String> parsedCommand) {
    return parsedCommand.size() == 1 && parsedCommand.getFirst().isEmpty();
  }

  private List<String> parseCommand(String command) {
    return parser.parseCommand(command);
  }

  private Result<Command> createCommand(FileManager fileManager, List<String> params) {
    try {
      String commandName = params.removeFirst();
      return switch (commandName) {
        case "mkdir" -> createMkdirCommand(fileManager, params);
        case "touch" -> createTouchCommand(fileManager, params);
        case "ls" -> createLsCommand(fileManager, params);
        case "pwd" -> createPwdCommand(fileManager, params);
        case "cd" -> createCdCommand(fileManager, params);
        case "rm" -> createRmCommand(fileManager, params);
        default -> new Result<>(new Failure(), null, "Command not found");
      };
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createMkdirCommand(FileManager fileManager, List<String> params) {
    try {
      Map<String, String> options = parser.parseMkdirOptions(params);

      if (!params.isEmpty()) {
        return new Result<>(new Failure(), null, "Invalid command");
      }

      return new Result<>(
          new Success(), new MkdirCommand(fileManager, options), "Mkdir command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createTouchCommand(FileManager fileManager, List<String> params) {
    try {
      Map<String, String> options = parser.parseTouchOptions(params);

      if (!params.isEmpty()) {
        return new Result<>(new Failure(), null, "Invalid command");
      }

      return new Result<>(
          new Success(), new TouchCommand(fileManager, options), "Touch command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createLsCommand(FileManager fileManager, List<String> params) {
    try {
      Map<String, String> options = parser.parseLsOptions(params);

      return new Result<>(new Success(), new LsCommand(fileManager, options), "Ls command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createPwdCommand(FileManager fileManager, List<String> params) {
    try {
      return new Result<>(new Success(), new PwdCommand(fileManager), "Pwd command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createCdCommand(FileManager fileManager, List<String> params) {
    try {
      Map<String, String> options = parser.parseCdOptions(params);
      return new Result<>(new Success(), new CdCommand(fileManager, options), "Cd command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }

  private Result<Command> createRmCommand(FileManager fileManager, List<String> params) {
    try {
      Map<String, String> options = parser.parseRmOptions(params);
      return new Result<>(new Success(), new RmCommand(fileManager, options), "Rm command created");
    } catch (Exception e) {
      return new Result<>(new Failure(), null, "Something went wrong while parsing the command");
    }
  }
}
