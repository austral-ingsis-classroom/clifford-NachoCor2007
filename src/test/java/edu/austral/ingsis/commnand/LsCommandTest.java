package edu.austral.ingsis.commnand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import org.junit.jupiter.api.Test;

public class LsCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void lsCommandTest() {
    FileManager afterMkdirCommand = fileManager.run("mkdir horace").value();
    Result<FileManager> runResult = afterMkdirCommand.run("ls");

    assertEquals(runResult.resultType().getResultType(), "Success");
    assertEquals(runResult.message(), "horace");
  }

  @Test
  public void emptyLsCommandTest() {
    Result<FileManager> runResult = fileManager.run("ls");

    assertEquals(runResult.resultType().getResultType(), "Success");
    assertEquals(runResult.message(), "");
  }

  @Test
  public void severalChildrenLsCommandTest() {
    FileManager afterMkdirCommand = fileManager.run("mkdir horace").value();
    FileManager afterMkdirCommand2 = afterMkdirCommand.run("mkdir horace2").value();
    FileManager afterMkdirCommand3 = afterMkdirCommand2.run("mkdir steve").value();
    Result<FileManager> runResult = afterMkdirCommand3.run("ls");

    assertEquals(runResult.resultType().getResultType(), "Success");
    assertEquals(runResult.message(), "horace horace2 steve");
  }
}
