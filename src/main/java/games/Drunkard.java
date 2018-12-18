package games;

class Drunkard {

  private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT];
  private static int[] playersCardTails = new int[2];
  private static int[] playersCardHeads = new int[2];

  private Drunkard() {}

  /**
   * main method.
   */
  static void main() {
    init();
    int iteration = 1;

    while (checkNotEmpty()) {

      int leftCard = getCardFromTop(0);
      int rightCard = getCardFromTop(1);
      System.out.println(String.format("Итерация №%d Игрок №1 карта: %s; игрок №2 карта: %s. ",
          iteration, CardUtils.toString(leftCard), CardUtils.toString(rightCard)));

      int result = compare(CardUtils.getPar(leftCard), CardUtils.getPar(rightCard));
      if (result > 0) {
        addCardsToPlayerDeck(0, leftCard, rightCard);
        System.out.println("Выиграл игрок 1!");
      } else if (result < 0) {
        addCardsToPlayerDeck(1, leftCard, rightCard);
        System.out.println("Выиграл игрок 2!");
      } else {
        addCardsToPlayerDeck(0, leftCard);
        addCardsToPlayerDeck(1, rightCard);
        System.out.println("Спор - каждый остаётся при своих!");
      }

      if (result > 0 && getPlayerDeckSize(1) == 0) {
        System.out.println(String.format("У игрока №1 36 карт, у игрока №2 0 карт%n"
            + "Выиграл первый игрок! Количество произведённых итераций: %d.", iteration));
      } else if (result < 0 && getPlayerDeckSize(0) == 0) {
        System.out.println(String.format("У игрока №1 0 карт, у игрока №2 36 карт%n"
            + "Выиграл второй игрок! Количество произведённых итераций: %d.", iteration));
      } else {
        System.out.println(String.format("У игрока №1 %d карт, у игрока №2 %d карт",
            getPlayerDeckSize(0), getPlayerDeckSize(1)));
        iteration++;
      }

    }
  }

  private static void init() {
    int[] deck = CardUtils.getShaffledCards();
    for (int i = 0; i < playersCards.length; i++) {
      playersCardTails[i] = 0;
      playersCardHeads[i] = deck.length / 2;
    }
    for (int i = 0; i < deck.length; i++) {
      if (i < deck.length / 2) {
        playersCards[0][i] = deck[i];
      } else {
        playersCards[1][i - deck.length / 2] = deck[i];
      }
    }
  }

  private static void addCardsToPlayerDeck(int playerIndex, int... cards) {
    for (int card : cards) {
      playersCards[playerIndex][playersCardHeads[playerIndex]] = card;
      playersCardHeads[playerIndex] = incrementIndex(playersCardHeads[playerIndex]);
    }
  }

  private static int getCardFromTop(int playerIndex) {
    int card = playersCards[playerIndex][playersCardTails[playerIndex]];
    playersCardTails[playerIndex] = incrementIndex(playersCardTails[playerIndex]);
    return card;
  }

  private static int compare(CardUtils.Par cardLeft, CardUtils.Par cardRight) {
    if (cardLeft == CardUtils.Par.ACE && cardRight == CardUtils.Par.SIX) {
      return -1;
    }
    if (cardLeft == CardUtils.Par.SIX && cardRight == CardUtils.Par.ACE) {
      return 1;
    }
    return cardLeft.compareTo(cardRight);
  }

  private static boolean checkNotEmpty() {
    for (int i = 0; i < playersCardTails.length; i++) {
      if (playerCardsIsEmpty(i)) {
        return false;
      }
    }
    return true;
  }

  private static int incrementIndex(int index) {
    return (index + 1) % CardUtils.CARDS_TOTAL_COUNT;
  }

  private static int getPlayerDeckSize(int playerIndex) {
    int tail = playersCardTails[playerIndex];
    int head = playersCardHeads[playerIndex];
    if (head < tail) {
      head += CardUtils.CARDS_TOTAL_COUNT;
    }
    return head - tail;
  }


  private static boolean playerCardsIsEmpty(int playerIndex) {
    int tail = playersCardTails[playerIndex];
    int head = playersCardHeads[playerIndex];
    return tail == head;
  }
}
