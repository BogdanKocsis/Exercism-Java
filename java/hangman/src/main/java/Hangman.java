import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

class Hangman {

    Observable<Output> play(Observable<String> words, Observable<String> letters) {
        return Observable.create(source -> {
            Data data = new Data();
            words.doOnNext(word -> {
                data.word = word;
                data.arr = word.split("");
                data.results = new String[data.arr.length];
                Arrays.fill(data.results, "_");
                data.guess = new HashSet<>();
                data.misses = new HashSet<>();
                data.parts = new ArrayList<>();
                data.used = new HashSet<>();
            }).subscribe();

            letters.doOnNext(letter -> {
                if (data.used.contains(letter)) {
                    source.onError(new IllegalArgumentException(String.format("Letter %s was already played", letter)));
                } else {
                    data.used.add(letter);
                    boolean isMiss = true;
                    for (int i = 0; i < data.arr.length; i++) {
                        if (data.arr[i].equals(letter)) {
                            data.guess.add(letter);
                            data.results[i] = letter;
                            isMiss = false;
                        }
                    }
                    if (isMiss) {
                        data.misses.add(letter);
                        switch (data.misses.size()) {
                            case 1:
                                data.parts.add(Part.HEAD);
                                break;
                            case 2:
                                data.parts.add(Part.BODY);
                                break;
                            case 3:
                                data.parts.add(Part.LEFT_ARM);
                                break;
                            case 4:
                                data.parts.add(Part.RIGHT_ARM);
                                break;
                            case 5:
                                data.parts.add(Part.LEFT_LEG);
                                break;
                            case 6:
                                data.parts.add(Part.RIGHT_LEG);
                                break;
                            default: {
                            }
                        }
                    }
                }
                String result = String.join("", data.results);
                source.onNext(new Output(data.word, result, data.guess, data.misses, data.parts, !(result.contains("_")) ? Status.WIN : (data.parts.size() < 6 ? Status.PLAYING : Status.LOSS)));
            }).doOnComplete(() -> {
                if (data.used.isEmpty())
                    source.onNext(new Output(data.word, String.join("", data.results), data.guess, data.misses, data.parts, Status.PLAYING));
                source.onComplete();
            }).subscribe();
        });
    }

    private static class Data {
        String[] arr = null;
        String[] results;
        Set<String> guess;
        Set<String> misses;
        Set<String> used;
        List<Part> parts;
        String word;
    }
}
