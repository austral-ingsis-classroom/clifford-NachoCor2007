package edu.austral.ingsis.commnand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import org.junit.jupiter.api.Test;

public class CdCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void testCdCommand() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    Result<FileManager> result = fileManager1.run("cd test");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "Moved to directory: test");
    assertEquals(result.value().cursor().name(), "test");
  }

  @Test
  public void testCdCommandSeveralDirs() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    FileManager fileManager2 = fileManager1.run("cd test").value();
    FileManager fileManager3 = fileManager2.run("mkdir test2").value();
    FileManager fileManager4 = fileManager3.run("cd test2").value();
    Result<FileManager> result = fileManager4.run("cd ..");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "Moved to directory: test");
    assertEquals(result.value().cursor().name(), "test");

    FileManager fileManager5 = result.value();
    FileManager fileManager6 = fileManager5.run("cd ..").value();
    Result<FileManager> result2 = fileManager6.run("cd test/test2");

    assertEquals(result2.resultType().getResultType(), "Success");
    assertEquals(result2.message(), "Moved to directory: test2");
    assertEquals(result2.value().cursor().name(), "test2");
  }

  @Test
  public void testCdCommandNoDirectory() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    Result<FileManager> result = fileManager1.run("cd test2");

    assertEquals(result.resultType().getResultType(), "Failure");
    assertEquals(result.message(), "Directory not found");
    assertEquals(result.value().cursor().name(), "/");
  }

  @Test
  public void testCdCommandNoPath() {
    FileManager fileManager1 = fileManager.run("mkdir test").value();
    Result<FileManager> result = fileManager1.run("cd");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "No path provided");
    assertEquals(result.value().cursor().name(), "/");
  }
}
