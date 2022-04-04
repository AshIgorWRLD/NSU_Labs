package character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordStatistic implements Comparable<WordStatistic> {

    private String value;
    private int amount;

    public WordStatistic(String value) {
        this.value = value;
        this.amount = 1;
    }

    private void increase() {
        amount++;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (null == other || other.getClass() != this.getClass()) {
            return false;
        }

        WordStatistic characterStatistic = (WordStatistic) other;
        if (characterStatistic.value.equals(value)) {
            ((WordStatistic) other).increase();
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(WordStatistic other) {
        int result = amount - other.amount;
        if (result != 0) {
            return result / Math.abs(result);
        }
        return result;
    }
}
