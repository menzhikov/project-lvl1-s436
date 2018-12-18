package games;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

  private static final int SIZE = 7;
  private static final int BET = 10;
  private static final int PRIZE = 1_000;

  private static int cash = 100;
  private static String[] msg = {"У Вас %d$, ставка - %d$",
      "Крутим барабаны! Розыгрыш принёс следующие результаты:",
      "первый барабан - %d, второй - %d, третий - %d",
      "Проигрыш %d$, ваш капитал теперь составляет: %d$",
      "Выигрыш %d$, ваш капитал теперь составляет: %d$"};
  private static int firstCounter;
  private static int secondCounter;
  private static int thirdCounter;

  /**
   * Entry point.
   * @param args from command line.
   */
  public static void main(String... args) {

    while (cash > 0) {
      display(String.format(msg[0], cash, BET));
      display(msg[1]);
      firstCounter = roll(firstCounter);
      secondCounter = roll(secondCounter);
      thirdCounter = roll(thirdCounter);
      display(String.format(msg[2], firstCounter, secondCounter, thirdCounter));
      if (firstCounter == secondCounter && secondCounter == thirdCounter) {
        cash += PRIZE;
        display(String.format(msg[3], PRIZE, cash));
      } else {
        cash -= BET;
        display(String.format(msg[3], BET, cash));
      }
    }

  }

  private static void display(String msg) {
    System.out.println(msg);
  }

  private static int roll(int counter) {
    return (counter + (int) round(random() * 100)) % SIZE;
  }
}