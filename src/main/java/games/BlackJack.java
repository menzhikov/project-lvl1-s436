package games;

import java.io.IOException;

import static games.Choice.getCharacterFromUser;

class BlackJack {

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
      addCard2Player(0);
      addCard2Player(0);
      while (sum(0) < 20 && confirm("Берём ещё?")) {
        addCard2Player(0);
      }

      addCard2Player(1);
      addCard2Player(1);
      while (sum(1) < 17) {
        addCard2Player(1);
      }

      int playerSum = getFinalSum(0);
      int computerSum = getFinalSum(1);
      System.out.println(String.format("Сумма ваших очков - %d, компьютера - %d",
          playerSum, computerSum));
      if (playerSum > computerSum) {
        playersMoney[0] += 10;
        playersMoney[1] -= 10;
      } else if (playerSum < computerSum) {
        playersMoney[1] += 10;
        playersMoney[0] -= 10;
      }
    }

    if (playersMoney[0] > 0) {
      System.out.println("Вы выиграли! Поздравляем!");
    } else {
      System.out.println("Вы проиграли. Соболезнуем...");
    }
  }

  private static void initRound() {
    System.out.println("\nУ Вас " + playersMoney[0] + "$, у компьютера - " + playersMoney[1]
        + "$. Начинаем новый раунд!");
    cards = CardUtils.getShaffledCards();
    playersCards = new int[2][MAX_CARDS_COUNT];
    playersCursors = new int[] {0, 0};
    cursor = 0;
  }

  private static void addCard2Player(int player) {
    int card = cards[cursor++];
    String msg;
    if (player == 0) {
      msg = "Вам выпала карта ";
    } else {
      msg = playersCursors[1] > 1 ? "Компьютер решил взять ещё и ему выпала карта "
          : "Компьютеру выпала карта ";
    }
    System.out.println(msg + CardUtils.toString(card));
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

  private static boolean confirm(String message) throws IOException {
    System.out.println(message + " \"Y\" - Да, {любой другой символ} - нет "
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
