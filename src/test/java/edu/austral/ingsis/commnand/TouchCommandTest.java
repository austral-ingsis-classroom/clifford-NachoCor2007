package edu.austral.ingsis.commnand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TouchCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void touchCommandTest() {
    Result<FileManager> result = fileManager.run("touch file.mp4");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "'file.mp4' file created");

    FileManager afterTouchCommand = result.value();
    FileSystem currentDirectory = afterTouchCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(children.size(), 1);
    assertEquals(children.getFirst().name(), "file.mp4");
  }

  @Test
  public void tryInvalidNameTouchCommandTest() {
    Result<FileManager> result = fileManager.run("touch /");

    assertEquals(result.resultType().getResultType(), "Failure");
    assertEquals(result.message(), "Invalid file name");

    FileManager afterTouchCommand = result.value();
    FileSystem currentDirectory = afterTouchCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(children.size(), 0);
  }

  @Test
  public void tryInvalidNameTouchCommandTest2() {
    Result<FileManager> result = fileManager.run("touch file .mp4");

    assertEquals(result.resultType().getResultType(), "Failure");
    assertEquals(result.message(), "Invalid command");

    FileManager afterTouchCommand = result.value();
    FileSystem currentDirectory = afterTouchCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(children.size(), 0);
  }

  @Test
  public void existingFileTouchCommandTest() {
    FileManager afterTouchCommand = fileManager.run("touch file.mp4").value();
    Result<FileManager> result = afterTouchCommand.run("touch file.mp4");

    assertEquals(result.resultType().getResultType(), "Success");
    assertEquals(result.message(), "'file.mp4' file created");

    FileSystem currentDirectory = afterTouchCommand.cursor();
    List<FileSystem> children = currentDirectory.getDirectory().value().children();

    assertEquals(children.size(), 1);
    assertEquals(children.getFirst().name(), "file.mp4");
  }
}
