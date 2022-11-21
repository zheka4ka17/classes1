import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CardGame2 {
    //Раздается по 26 карт
    private static char[] values = "23456789TJQKA".toCharArray();
    private static char[] suits = "♣♦♥♠".toCharArray(); //diamonds (♦), clubs (♣), hearts (♥) and spades (♠)
    private static int count = values.length * suits.length; //Общее количество карт - 52
    private static int[] cards = new int[count];

    static {
        for (int i = 0; i < count; i++) cards[i] = i; //Заполнение колоды карт
    }

    private static Queue<Integer> deck1 = new LinkedList<>();
    private static Queue<Integer> deck2 = new LinkedList<>();

    public static void main(String[] args) {

//System.out.println (getRank('3','c'));
        shuffle();
        print();
        process();
        //printCard(1);
    }

    /*
    //Получить условный номер карты
    public static int getRank(char value, char suit) {
        String sValues = String.valueOf(values);
        String sSuit = String.valueOf(suits);
        int numValue = sValues.indexOf(value) + 1;
        int numSuit = sSuit.indexOf(suit) + 1;
        System.out.printf("%d, %d%n", numValue, numSuit);
        return numValue * numSuit;
    }
     */

    public static int calcValue(int rank) {
        return rank % values.length;
    }

    public static int calcSuit(int rank) {
        return rank / values.length;
    }

    //Перемешать колоду
    public static void shuffle() {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int j = random.nextInt(count);
            swap(cards, i, j);
        }
    }

    public static final void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void print() {
        for (int i = 0; i < count; i++)
            System.out.print(cards[i] + " ");
        System.out.println();
    }

    public static void process() {
        //Карты раздаются в две руки поровну
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0)
                deck1.offer(cards[i]);
            else
                deck2.offer(cards[i]);
        }
        while (deck1.size() != 0 && deck2.size() != 0) {
            //Достаем из каждой руки по карте
            int rank1  = deck1.poll();
            int rank2  = deck2.poll();
            int value1 = calcValue(rank1);
            int value2 = calcValue(rank2);
            if (value1 > value2) {
                deck1.offer(rank1);
                deck1.offer(rank2);
            } else if (value1 < value2) {
                deck2.offer(rank2);
                deck2.offer(rank1);
            } else {
                System.out.printf("War: ");

                int rank3 = deck1.poll();
                int rank4 = deck2.poll();
                int rank5 = deck1.poll();
                int rank6 = deck2.poll();
                printCard(rank3); System.out.print(", ");
                printCard(rank4); System.out.print(", ");
                printCard(rank5); System.out.print(", ");
                printCard(rank6); System.out.print(", ");

                int value3 = calcValue(rank3);
                int value4 = calcValue(rank4);
                int value5 = calcValue(rank5);
                int value6 = calcValue(rank6);
                if (value5 > value6) {
                    deck1.offer(rank1);
                    deck1.offer(rank2);
                    deck1.offer(rank3);
                    deck1.offer(rank4);
                    deck1.offer(rank5);
                    deck1.offer(rank6);
                } else if (value5 < value6) {
                    deck2.offer(rank1);
                    deck2.offer(rank2);
                    deck2.offer(rank3);
                    deck2.offer(rank4);
                    deck2.offer(rank5);
                    deck2.offer(rank6);
                }
                else { //Если второй раз подряд следует ситуация "war", возвращаем по 3 карты игрокам (ничья)
                    deck1.offer(rank1);
                    deck1.offer(rank3);
                    deck1.offer(rank5);
                    deck2.offer(rank2);
                    deck2.offer(rank4);
                    deck2.offer(rank6);
                }
            }
            printCard(rank1);
            System.out.print(", ");
            printCard(rank2);
            System.out.print("; ");
            System.out.printf("%d, %d%n", deck1.size(), deck2.size());
            //Смоделировать ситуацию, когда под конец игры срабатывает "war",
            // и у одного из игроков не хватает карт, чтобы ее разрешить
        }
    }

    private static void printCard(int rank){
        int value = calcValue(rank);
        int suit = calcSuit(rank);

//System.out.printf
  //      ("%d, %d, ",value,suit);
        char cValue =  values[value];
        char cSuit = suits[suit];
        System.out.printf("%c%c(%d)",cValue, cSuit, value);
    }
}

