package games;

import static org.apache.commons.math3.util.MathArrays.shuffle;

public class Drunkard {

  private static final int PARS_TOTAL_COUNT = Par.values().length;
  private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

  private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];
  private static int[] playersCardTails = new int[2];
  private static int[] playersCardHeads = new int[2];

  /**
   * main method.
   */
  public static void main() {
    init();
    int iteration = 1;

    while (checkNotEmpty()) {

      int leftCard = getCardFromTop(0);
      int rightCard = getCardFromTop(1);
      System.out.println(String.format("Итерация №%d Игрок №1 карта: %s; игрок №2 карта: %s. ",
          iteration, toString(leftCard), toString(rightCard)));

      int result = compare(getPar(leftCard), getPar(rightCard));
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
    int[] deck = new int[CARDS_TOTAL_COUNT];
    for (int i = 0; i < deck.length; i++) {
      deck[i] = i;
    }
    shuffle(deck);
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

  private static int compare(Par cardLeft, Par cardRight) {
    if (cardLeft == Par.ACE && cardRight == Par.SIX) {
      return -1;
    }
    if (cardLeft == Par.SIX && cardRight == Par.ACE) {
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

  enum Suit {
    SPADES, // пики
    HEARTS, // червы
    CLUBS, // трефы
    DIAMONDS // бубны
  }

  enum Par {
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK, // Валет
    QUEEN, // Дама
    KING, // Король
    ACE // Туз
  }

  private static Suit getSuit(int cardNumber) {
    return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
  }

  private static Par getPar(int cardNumber) {
    return Par.values()[cardNumber % PARS_TOTAL_COUNT];
  }

  private static String toString(int cardNumber) {
    return getPar(cardNumber) + " " + getSuit(cardNumber);
  }

  private static int incrementIndex(int index) {
    return (index + 1) % CARDS_TOTAL_COUNT;
  }

  private static int getPlayerDeckSize(int playerIndex) {
    int tail = playersCardTails[playerIndex];
    int head = playersCardHeads[playerIndex];
    if (head < tail) {
      head += CARDS_TOTAL_COUNT;
    }
    return head - tail;
  }


  private static boolean playerCardsIsEmpty(int playerIndex) {
    int tail = playersCardTails[playerIndex];
    int head = playersCardHeads[playerIndex];
    return tail == head;
  }
}
