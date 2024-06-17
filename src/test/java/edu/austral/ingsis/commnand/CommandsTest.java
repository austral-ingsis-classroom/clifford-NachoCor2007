package edu.austral.ingsis.commnand;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTest {
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
    assertEquals(children.size(), 1);
    assertEquals(children.getFirst().getDirectory().value().name(), "horace");
  }
}
