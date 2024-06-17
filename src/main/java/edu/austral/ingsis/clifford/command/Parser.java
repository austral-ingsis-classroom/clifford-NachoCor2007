package edu.austral.ingsis.clifford.command;

import java.util.ArrayList;
import java.util.List;

public class Parser {
  public List<String> parseCommand(String command) {
    return new ArrayList<>(List.of(command.trim().split("\\s+")));
  }
}
