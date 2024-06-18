package edu.austral.ingsis.commnand;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void testPwdCommand() {
    Result<FileManager> result = fileManager.run("pwd");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "/");
  }
}
