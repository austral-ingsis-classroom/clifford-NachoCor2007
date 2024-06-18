package edu.austral.ingsis.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import org.junit.jupiter.api.Test;

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

  @Test
  public void testPwdCommandSeveralDirs() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    FileManager fileManager2 = fileManager1.run("cd test").value();
    Result<FileManager> result = fileManager2.run("pwd");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "/test");
  }

  @Test
  public void testPwdCommandMoreDirs() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    FileManager fileManager2 = fileManager1.run("cd test").value();
    FileManager fileManager3 = fileManager2.run("mkdir test2").value();
    FileManager fileManager4 = fileManager3.run("cd test2").value();
    Result<FileManager> result = fileManager4.run("pwd");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "/test/test2");
  }
}
