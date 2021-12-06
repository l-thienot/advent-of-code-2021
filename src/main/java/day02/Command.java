package day02;

import java.util.Arrays;

public enum Command {
    FWD("forward"),
    DOWN("down"),
    UP("up");
    private final String libelle;

    Command(String libelle) {
        this.libelle = libelle;
    }

    public static Command from(String instruction) {
        return Arrays.stream(Command.values())
                .filter(inst -> inst.libelle.equals(instruction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("unexpected instruction : " + instruction));
    }
}
