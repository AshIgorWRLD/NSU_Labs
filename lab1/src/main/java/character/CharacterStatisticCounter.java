package character;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterStatisticCounter {

    private final Set<CharacterStatistic> characterStatisticSet;
    @Getter
    private int allCharactersAmount;

    public CharacterStatisticCounter() {
        characterStatisticSet = new HashSet<>();
        allCharactersAmount = 0;
    }

    public void addCharacter(Character character) {
        characterStatisticSet.add(new CharacterStatistic(character));
        allCharactersAmount++;
    }

    public Set<CharacterStatistic> mapFile() {
        return characterStatisticSet.stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
