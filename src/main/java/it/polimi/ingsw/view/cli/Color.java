package it.polimi.ingsw.view.cli;

/**
 * Color cli
 */
public enum Color {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_PINK("\033[38;5;206m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String getEscape()
    {
        return escape;
    }

    @Override
    public String toString()
    {
        return escape;
    }
}
