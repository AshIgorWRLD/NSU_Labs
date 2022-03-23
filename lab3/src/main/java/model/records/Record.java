package model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;

import model.GameType;

@Getter
@AllArgsConstructor
public class Record {
    private String name;
    private int value;
    private GameType gameType;
}
