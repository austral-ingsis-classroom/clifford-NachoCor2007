package edu.austral.ingsis.command;

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

  @Test
  public void lsCommandInOrderTest() {
    FileManager afterMkdirCommand = fileManager.run("mkdir a").value();
    FileManager afterMkdirCommand2 = afterMkdirCommand.run("mkdir c").value();
    FileManager afterMkdirCommand3 = afterMkdirCommand2.run("mkdir b").value();
    Result<FileManager> ascendingRunResult = afterMkdirCommand3.run("ls --ord=asc");

    assertEquals(ascendingRunResult.resultType().getResultType(), "Success");
    assertEquals(ascendingRunResult.message(), "a b c");

    Result<FileManager> descendingRunResult = afterMkdirCommand3.run("ls --ord=desc");

    assertEquals(descendingRunResult.resultType().getResultType(), "Success");
    assertEquals(descendingRunResult.message(), "c b a");
  }
}
