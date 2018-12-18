package games;

import org.slf4j.Logger;

import java.util.Random;

class Slot {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

  private Slot() {
  }

  /**
   * Entry point.
   */
  static void main() {
    final int size = 7;
    final int bet = 10;
    final int prize = 1_000;

    int cash = 100;
    int firstCounter = 0;
    int secondCounter = 0;
    int thirdCounter = 0;
    Random r = new Random();

    while (cash > 0) {
      log.info("У Вас {}$, ставка - {}$", cash, bet);
      log.info("Крутим барабаны! Розыгрыш принёс следующие результаты:");
      firstCounter = (firstCounter + r.nextInt(100)) % size;
      secondCounter = (secondCounter + r.nextInt(100)) % size;
      thirdCounter = (thirdCounter + r.nextInt(100)) % size;
      log.info("первый барабан - {}, второй - {}, третий - {}",
          firstCounter, secondCounter, thirdCounter);
      if (firstCounter == secondCounter && secondCounter == thirdCounter) {
        cash += prize;
        log.info("Проигрыш {}$, ваш капитал теперь составляет: {}$",
            prize, cash);
      } else {
        cash -= bet;
        log.info("Выигрыш {}$, ваш капитал теперь составляет: {}$",
            bet, cash);
      }
    }

  }
}