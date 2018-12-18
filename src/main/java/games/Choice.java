package games;

import org.slf4j.Logger;

import java.io.IOException;

public class Choice {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Choice.class);
  private static final String LINE_SEPARATOR = System.lineSeparator();

  /**
   * Entry point.
   * @param args from command line.
   */
  public static void main(String[] args) throws IOException {
    log.info("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\", 3 - \"очко\"");
    switch (getCharacterFromUser()) {
      case '1':
        Slot.main();
        break;
      case '2':
        Drunkard.main();
        break;
      case '3':
        BlackJack.main();
        break;
      default:
        log.info("Игры с таким номером нет!");
    }
  }

  static char getCharacterFromUser() throws IOException {
    byte[] input = new byte[1 + LINE_SEPARATOR.length()];
    if (System.in.read(input) != input.length) {
      throw new GameRunException("Пользователь ввёл недостаточное кол-во символов");
    }
    return (char) input[0];
  }
}
