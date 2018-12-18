package games;

import java.io.IOException;

public class Choice {
  /**
   * Entry point.
   * @param args from command line.
   */
  public static void main(String[] args) throws IOException {
    System.out.println("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\"");
    switch (System.in.read()) {
      case '1':
        Slot.main();
        break;
      case '2':
        Drunkard.main();
        break;
      default:
        System.out.println("Игры с таким номером нет!");
    }
  }

}
