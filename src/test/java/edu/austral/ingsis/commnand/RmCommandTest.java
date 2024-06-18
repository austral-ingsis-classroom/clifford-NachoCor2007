package edu.austral.ingsis.commnand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.CommandFactory;
import edu.austral.ingsis.clifford.command.Parser;
import edu.austral.ingsis.clifford.communication.Result;
import edu.austral.ingsis.clifford.filemanager.FileManager;
import org.junit.jupiter.api.Test;

public class RmCommandTest {
  private final Parser parser = new Parser();
  private final CommandFactory commandFactory = new CommandFactory(parser);
  private final FileManager fileManager = new FileManager(commandFactory);

  @Test
  public void rmCommandFileTest() {
    FileManager fileManager1 = fileManager.run("touch horace").value();
    Result<FileManager> fileManager2 = fileManager1.run("rm horace");

    assertEquals("horace removed", fileManager2.message());
    assertEquals("Success", fileManager2.resultType().getResultType());
    assertEquals("", fileManager2.value().run("ls").message());
  }

  @Test
  public void rmCommandDirTest2() {
    FileManager fileManager1 = fileManager.run("mkdir horace").value();
    Result<FileManager> fileManager2 = fileManager1.run("rm --recursive horace");

    assertEquals("horace removed", fileManager2.message());
    assertEquals("Success", fileManager2.resultType().getResultType());
    assertEquals("", fileManager2.value().run("ls").message());
  }

  @Test
  public void rmCommandDirNoRecTest() {
    FileManager fileManager1 = fileManager.run("mkdir horace").value();
    Result<FileManager> fileManager2 = fileManager1.run("rm horace");

    assertEquals("cannot remove 'horace', is a directory", fileManager2.message());
    assertEquals("Failure", fileManager2.resultType().getResultType());
    assertEquals("horace", fileManager2.value().run("ls").message());
  }
}
