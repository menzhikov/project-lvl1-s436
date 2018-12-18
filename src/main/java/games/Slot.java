package games;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

  private Slot() {
  }

  /**
   * Entry point.
   */
  public static void main() {
    final int size = 7;
    final int bet = 10;
    final int prize = 1_000;

    int cash = 100;
    int firstCounter = 0;
    int secondCounter = 0;
    int thirdCounter = 0;

    while (cash > 0) {
      System.out.println(String.format("У Вас %d$, ставка - %d$", cash, bet));
      System.out.println("Крутим барабаны! Розыгрыш принёс следующие результаты:");
      firstCounter = (firstCounter + (int) round(random() * 100)) % size;
      secondCounter = (secondCounter + (int) round(random() * 100)) % size;
      thirdCounter = (thirdCounter + (int) round(random() * 100)) % size;
      System.out.println(String.format("первый барабан - %d, второй - %d, третий - %d",
          firstCounter, secondCounter, thirdCounter));
      if (firstCounter == secondCounter && secondCounter == thirdCounter) {
        cash += prize;
        System.out.println(String.format("Проигрыш %d$, ваш капитал теперь составляет: %d$",
            prize, cash));
      } else {
        cash -= bet;
        System.out.println(String.format("Выигрыш %d$, ваш капитал теперь составляет: %d$",
            bet, cash));
      }
    }

  }
}