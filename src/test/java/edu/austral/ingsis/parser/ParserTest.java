package edu.austral.ingsis.parser;

import edu.austral.ingsis.clifford.command.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest{
  private final Parser parser = new Parser();

  @Test
  public void testParseCommand(){
    String command = "ls -l";
    List<String> parsedString = parser.parseCommand(command);

    List<String> expectedParse = List.of("ls", "-l");

    assertEquals(expectedParse, parsedString);
  }

  @Test
  public void testParseCommand2(){
    String command = "ls -l -a";
    List<String> parsedString = parser.parseCommand(command);

    List<String> expectedParse = List.of("ls", "-l", "-a");

    assertEquals(expectedParse, parsedString);
  }

  @Test
  public void testSeveralSpacesCommand() {
    String command = "ls    -l";
    List<String> parsedString = parser.parseCommand(command);

    List<String> expectedParse = List.of("ls", "-l");

    assertEquals(expectedParse, parsedString);
  }
}
