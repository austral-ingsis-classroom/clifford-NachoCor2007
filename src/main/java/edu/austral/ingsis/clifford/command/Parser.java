package edu.austral.ingsis.clifford.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
  public List<String> parseCommand(String command) {
    return new ArrayList<>(List.of(command.trim().split("\\s+")));
  }

  public Map<String, String> parseMkdirOptions(List<String> params) {
    Map<String, String> options = new HashMap<>();
    nameEntry(params, options);
    return options;
  }

  public Map<String, String> parseTouchOptions(List<String> params) {
    Map<String, String> options = new HashMap<>();
    nameEntry(params, options);
    return options;
  }

  public Map<String, String> parseLsOptions(List<String> params) {
    Map<String, String> options = new HashMap<>();
    orderEntry(params, options);
    return options;
  }

  public Map<String, String> parseCdOptions(List<String> params) {
    Map<String, String> options = new HashMap<>();
    pathEntry(params, options);
    return options;
  }

  public Map<String, String> parseRmOptions(List<String> params) {
    Map<String, String> options = new HashMap<>();
    recursiveEntry(params, options);
    nameEntry(params, options);
    return options;
  }

  private void nameEntry(List<String> params, Map<String, String> options) {
    if (params.isEmpty()) {
      return;
    }
    options.put("name", params.removeFirst());
  }

  private void orderEntry(List<String> params, Map<String, String> options) {
    if (params.isEmpty()) {
      return;
    }
    String order = params.removeFirst();
    List<String> parsedOrder = new ArrayList<>(List.of(order.split("=")));

    if (parsedOrder.size() != 2) {
      return;
    }

    if (!parsedOrder.removeFirst().equals("--ord")) {
      return;
    }

    order = parsedOrder.removeFirst();

    if (!order.equals("asc") && !order.equals("desc")) {
      return;
    }

    options.put("order", order);
  }

  private void pathEntry(List<String> params, Map<String, String> options) {
    if (params.isEmpty()) {
      return;
    }
    String path = params.removeFirst();
    options.put("path", path);
  }

  private void recursiveEntry(List<String> params, Map<String, String> options) {
    if (params.isEmpty()) {
      options.put("recursive", "false");
      return;
    }

    if (!params.getFirst().equals("--recursive")) {
      options.put("recursive", "false");
      return;
    }

    String recursive = params.removeFirst();
    options.put("recursive", recursive.equals("--recursive") ? "true" : "false");
  }
}
