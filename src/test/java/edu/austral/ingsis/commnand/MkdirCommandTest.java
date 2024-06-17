package edu.austral.ingsis.commnand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import org.junit.jupiter.api.Test;
import java.util.List;

public class MkdirCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void mkdirCommandTest() {
    Result<FileManager> runResult = fileManager.run("mkdir horace");
    FileManager afterMkdirCommand = runResult.value();

    FileSystem currentDirectory = afterMkdirCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(runResult.resultType().getResultType(), "Success");
    assertEquals(runResult.message(), "horace directory created");
    assertEquals(children.size(), 1);
    assertEquals(children.getFirst().getDirectory().value().name(), "horace");
  }

  @Test
  public void tryInvalidNameMkdirCommandTest() {
    Result<FileManager> runResult = fileManager.run("mkdir /");
    FileManager afterMkdirCommand = runResult.value();

    FileSystem currentDirectory = afterMkdirCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(runResult.resultType().getResultType(), "Failure");
    assertEquals(runResult.message(), "Invalid directory name");
    assertEquals(children.size(), 0);
  }

  @Test
  public void tryInvalidNameMkdirCommandTest2() {
    Result<FileManager> runResult = fileManager.run("mkdir lo l");
    FileManager afterMkdirCommand = runResult.value();

    FileSystem currentDirectory = afterMkdirCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(runResult.resultType().getResultType(), "Failure");
    assertEquals(runResult.message(), "Invalid command");
    assertEquals(children.size(), 0);
  }

  @Test
  public void existingDirMkdirCommandTest() {
    FileManager afterMkdirCommand = fileManager.run("mkdir horace").value();
    Result<FileManager> runResult = afterMkdirCommand.run("mkdir horace");

    assertEquals(runResult.resultType().getResultType(), "Failure");
    assertEquals(runResult.message(), "horace directory already exists");
  }
}
