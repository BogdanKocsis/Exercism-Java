import java.util.*;
import java.util.stream.Collectors;

class Card implements Comparable<Card> {
    enum Rank {
        _2, _3, _4, _5, _6, _7, _8, _9, T, J, Q, K, A
    }

    enum Suit {
        C, D, H, S
    }

    final Rank rank;
    final Suit suit;

    Card(String cardString) {
        String rankChar = cardString.substring(0, cardString.length() - 1);
        String suitChar = cardString.substring(cardString.length() - 1);

        switch (rankChar) {
            case "10":
                this.rank = Rank.T;
                break;
            case "J":
                this.rank = Rank.J;
                break;
            case "Q":
                this.rank = Rank.Q;
                break;
            case "K":
                this.rank = Rank.K;
                break;
            case "A":
                this.rank = Rank.A;
                break;
            default:
                this.rank = Rank.valueOf("_" + rankChar);
                break;
        }

        this.suit = Suit.valueOf(suitChar);
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.rank.ordinal(), other.rank.ordinal());
    }

    @Override
    public String toString() {
        if (rank == Rank.T) {
            return "10" + suit.toString();
        }
        return rank.toString().substring(1) + suit.toString();
    }
}

class Hand implements Comparable<Hand> {
    enum HandRank {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH
    }

    private final String originalHandString;
    private final List<Card> cards;
    private final HandRank rank;
    private final List<Integer> tieBreakerRanks;

    Hand(String handString) {
        this.originalHandString = handString;
        this.cards = parseHand(handString);
        Collections.sort(this.cards);
        this.rank = evaluateRank();
        this.tieBreakerRanks = determineTieBreakerRanks();
    }

    private List<Card> parseHand(String handString) {
        return Arrays.stream(handString.split(" "))
                .map(Card::new)
                .collect(Collectors.toList());
    }

    private HandRank evaluateRank() {
        boolean isFlush = isFlush();
        boolean isStraight = isStraight();

        if (isFlush && isStraight) {
            return HandRank.STRAIGHT_FLUSH;
        }

        Map<Card.Rank, Long> rankCounts = cards.stream()
                .collect(Collectors.groupingBy(card -> card.rank, Collectors.counting()));

        long maxCount = rankCounts.values().stream().max(Long::compare).orElse(0L);

        if (maxCount == 4) {
            return HandRank.FOUR_OF_A_KIND;
        }
        if (maxCount == 3 && rankCounts.size() == 2) {
            return HandRank.FULL_HOUSE;
        }
        if (isFlush) {
            return HandRank.FLUSH;
        }
        if (isStraight) {
            return HandRank.STRAIGHT;
        }
        if (maxCount == 3) {
            return HandRank.THREE_OF_A_KIND;
        }
        if (maxCount == 2 && rankCounts.size() == 3) {
            return HandRank.TWO_PAIR;
        }
        if (maxCount == 2) {
            return HandRank.ONE_PAIR;
        }

        return HandRank.HIGH_CARD;
    }

    private boolean isFlush() {
        return cards.stream().map(card -> card.suit).distinct().count() == 1;
    }

    private boolean isStraight() {
        if (cards.get(4).rank == Card.Rank.A && cards.get(0).rank == Card.Rank._2 &&
                cards.get(1).rank == Card.Rank._3 && cards.get(2).rank == Card.Rank._4 &&
                cards.get(3).rank == Card.Rank._5) {
            return true;
        }

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).rank.ordinal() != cards.get(i - 1).rank.ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> determineTieBreakerRanks() {
        List<Integer> tieBreaker = new ArrayList<>();
        Map<Card.Rank, Long> rankCounts = cards.stream()
                .collect(Collectors.groupingBy(card -> card.rank, Collectors.counting()));

        switch (rank) {
            case FOUR_OF_A_KIND, FULL_HOUSE:
                rankCounts.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(entry -> entry.getKey().ordinal())
                        .forEach(tieBreaker::add);
                break;
            case TWO_PAIR, ONE_PAIR:
                rankCounts.entrySet().stream()
                        .sorted(Map.Entry.<Card.Rank, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                        .map(entry -> entry.getKey().ordinal())
                        .forEach(tieBreaker::add);
                break;
            case STRAIGHT:
            case STRAIGHT_FLUSH:
                if (cards.get(4).rank == Card.Rank.A && cards.get(0).rank == Card.Rank._2) {
                    tieBreaker.add(cards.get(3).rank.ordinal());
                } else {
                    tieBreaker.add(cards.get(4).rank.ordinal());
                }
                break;
            default:
                cards.stream()
                        .sorted(Comparator.reverseOrder())
                        .map(card -> card.rank.ordinal())
                        .forEach(tieBreaker::add);
                break;
        }
        return tieBreaker;
    }

    @Override
    public int compareTo(Hand other) {
        int rankComparison = Integer.compare(this.rank.ordinal(), other.rank.ordinal());
        if (rankComparison != 0) {
            return rankComparison;
        }

        for (int i = 0; i < this.tieBreakerRanks.size(); i++) {
            int tieBreakerComparison = Integer.compare(this.tieBreakerRanks.get(i), other.tieBreakerRanks.get(i));
            if (tieBreakerComparison != 0) {
                return tieBreakerComparison;
            }
        }
        return 0;
    }

    public String getOriginalHandString() {
        return originalHandString;
    }
}

class Poker {
    private final List<Hand> hands;

    Poker(List<String> hands) {
        this.hands = hands.stream().map(Hand::new).collect(Collectors.toList());
    }

    List<String> getBestHands() {
        if (hands.isEmpty()) {
            return Collections.emptyList();
        }

        hands.sort(Comparator.reverseOrder());

        // The best hand is the first one after sorting
        Hand bestHand = hands.getFirst();

        List<String> bestHands = new ArrayList<>();
        bestHands.add(bestHand.getOriginalHandString());

        for (int i = 1; i < hands.size(); i++) {
            if (hands.get(i).compareTo(bestHand) == 0) {
                bestHands.add(hands.get(i).getOriginalHandString());
            } else {
                break;
            }
        }

        return bestHands;
    }
}