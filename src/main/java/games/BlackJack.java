package games;

import org.slf4j.Logger;

import java.io.IOException;

import static games.Choice.getCharacterFromUser;

class BlackJack {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);
  private static final int MAX_VALUE = 21;
  private static final int MAX_CARDS_COUNT = 8;

  private static int[] cards; // Основная колода
  private static int cursor; // Счётчик карт основной колоды

  private static int[][] playersCards; // карты игроков. Первый индекс - номер игрока
  private static int[] playersCursors; // курсоры карт игроков. Индекс - номер игрока
  private static int[] playersMoney = {100, 100};

  private BlackJack() {
  }

  /**
   * main method.
   */
  static void main() throws IOException {
    while (playersMoney[0] > 0 && playersMoney[1] > 0) {
      initRound();
      addCard2PlayerNTimes(0, 2);
      while (sum(0) < 20 && confirm()) {
        addCard2Player(0);
      }

      addCard2PlayerNTimes(1, 2);
      while (sum(1) < 17) {
        addCard2Player(1);
      }

      int playerSum = getFinalSum(0);
      int computerSum = getFinalSum(1);
      log.info("Сумма ваших очков - {}, компьютера - {}", playerSum, computerSum);
      if (playerSum > computerSum) {
        playersMoney[0] += 10;
        playersMoney[1] -= 10;
      } else if (playerSum < computerSum) {
        playersMoney[1] += 10;
        playersMoney[0] -= 10;
      }
    }

    if (playersMoney[0] > 0) {
      log.info("Вы выиграли! Поздравляем!");
    } else {
      log.info("Вы проиграли. Соболезнуем...");
    }
  }

  private static void addCard2PlayerNTimes(int player, int times) {
    while (times-- > 0) {
      addCard2Player(player);
    }
  }

  private static void initRound() {
    log.info("\nУ Вас {}$, у компьютера - {}$. Начинаем новый раунд!",
        playersMoney[0], playersMoney[1]);
    cards = CardUtils.getShaffledCards();
    playersCards = new int[2][MAX_CARDS_COUNT];
    playersCursors = new int[] {0, 0};
    cursor = 0;
  }

  private static void addCard2Player(int player) {
    int card = cards[cursor++];
    String msg;
    if (player == 0) {
      msg = "Вам выпала карта {}";
    } else {
      msg = playersCursors[1] > 1 ? "Компьютер решил взять ещё и ему выпала карта {}"
          : "Компьютеру выпала карта {}";
    }
    String cardName = CardUtils.toString(card);
    log.info(msg, cardName);
    playersCards[player][playersCursors[player]++] = card;
  }

  private static int sum(int player) {
    int result = 0;
    for (int i = 0; i < playersCursors[player]; i++) {
      result += value(playersCards[player][i]);
    }
    return result;
  }

  private static int getFinalSum(int player) {
    int score = sum(player);
    return score > MAX_VALUE ? 0 : score;
  }

  private static boolean confirm() throws IOException {
    log.info("Берём ещё? \"Y\" - Да, {любой другой символ} - нет "
        + "(Что бы выйти из игры, нажмите Ctrl + C)");
    switch (getCharacterFromUser()) {
      case 'Y':
      case 'y':
        return true;
      default:
        return false;
    }
  }

  private static int value(int card) {
    switch (CardUtils.getPar(card)) {
      case JACK:
        return 2;
      case QUEEN:
        return 3;
      case KING:
        return 4;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      case ACE:
      default:
        return 11;
    }
  }
}
