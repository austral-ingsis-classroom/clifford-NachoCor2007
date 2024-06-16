package edu.austral.ingsis.clifford.command;

import java.util.List;

public class CommandFactory {
  private final Parser parser;

  public CommandFactory(Parser parser) {
    this.parser = parser;
  }

  public Command getCommand(String command) {
    return null;
  }

  private List<String> parseCommand(String command) {
    return parser.parseCommand(command);
  }
}
