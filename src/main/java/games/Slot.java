package games;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

  /**
   * Entry point.
   *
   * @param args from command line.
   */
  public static void main(String... args) {
    final int size = 7;
    final int bet = 10;
    final int prize = 1_000;

    String[] msg = {"У Вас %d$, ставка - %d$",
        "Крутим барабаны! Розыгрыш принёс следующие результаты:",
        "первый барабан - %d, второй - %d, третий - %d",
        "Проигрыш %d$, ваш капитал теперь составляет: %d$",
        "Выигрыш %d$, ваш капитал теперь составляет: %d$"};
    int cash = 100;
    int firstCounter = 0;
    int secondCounter = 0;
    int thirdCounter = 0;

    while (cash > 0) {
      System.out.println(String.format(msg[0], cash, bet));
      System.out.println(msg[1]);
      firstCounter = (firstCounter + (int) round(random() * 100)) % size;
      secondCounter = (secondCounter + (int) round(random() * 100)) % size;
      thirdCounter = (thirdCounter + (int) round(random() * 100)) % size;
      System.out.println(String.format(msg[2], firstCounter, secondCounter, thirdCounter));
      if (firstCounter == secondCounter && secondCounter == thirdCounter) {
        cash += prize;
        System.out.println(String.format(msg[3], prize, cash));
      } else {
        cash -= bet;
        System.out.println(String.format(msg[3], bet, cash));
      }
    }

  }
}