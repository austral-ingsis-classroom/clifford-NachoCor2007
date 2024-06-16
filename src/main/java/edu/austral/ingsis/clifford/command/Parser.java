package edu.austral.ingsis.clifford.command;

import java.util.List;

public class Parser {
  public List<String> parseCommand(String command) {
    return List.of(command.split(" "));
  }
}
