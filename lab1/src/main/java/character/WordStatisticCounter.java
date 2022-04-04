package character;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WordStatisticCounter {

    private final Set<WordStatistic> wordStatisticSet;
    @Getter
    private int allWordsAmount;

    public WordStatisticCounter() {
        wordStatisticSet = new HashSet<>();
        allWordsAmount = 0;
    }

    public void addWord(String word) {
        wordStatisticSet.add(new WordStatistic(word));
        allWordsAmount++;
    }

    public Set<WordStatistic> mapFile() {
        return wordStatisticSet.stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
