package character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterStatistic implements Comparable<CharacterStatistic> {

    private Character value;
    private int amount;

    public CharacterStatistic(Character value) {
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

        CharacterStatistic characterStatistic = (CharacterStatistic) other;
        if (characterStatistic.value.equals(value)) {
            ((CharacterStatistic) other).increase();
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public int compareTo(CharacterStatistic other) {
        int result = amount - other.amount;
        if (result != 0) {
            return result / Math.abs(result);
        }
        return result;
    }
}
