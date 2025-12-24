import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BookStore {
    static final double COST = 8;
    static final double[] DISCOUNT_MULTIPLIERS = {1, 0.95, 0.90, 0.80, 0.75};

    double calculateBasketCost(List<Integer> books) {
        Collections.sort(books);
        final List<Integer> bookPiles = getInitPiles(books);
        final List<Integer> bestPiles = rearrange(bookPiles);
        return getTotal(bestPiles);
    }

    double getTotal(List<Integer> books) {
        double total = 0;
        for (int i = 0; i < books.size(); i++) {
            total += books.get(i) * DISCOUNT_MULTIPLIERS[books.get(i)-1];
        }
        return total * COST;
    }

    List<Integer> rearrange(List<Integer> bookPiles) {
        if (bookPiles.contains(5) && bookPiles.contains(3)) {
            bookPiles.remove(Integer.valueOf(5));
            bookPiles.remove(Integer.valueOf(3));
            bookPiles.addAll(List.of(4,4));
            bookPiles = rearrange(bookPiles);
        }
        return bookPiles;
    }

    List<Integer> getInitPiles(List<Integer> books) {
        List<Integer> mutableBooks = new ArrayList<>(books);
        List<Integer> bookPiles = new ArrayList<>();
        while (!mutableBooks.isEmpty()) {
            List<Integer> distinct_titles = mutableBooks.stream().distinct().toList();
            final int pile = distinct_titles.size();
            bookPiles.add(pile);
            distinct_titles.forEach(mutableBooks::remove);
        }
        return bookPiles;
    }
}