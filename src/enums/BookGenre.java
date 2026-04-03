package enums;

/**
 * Enum representing different book genres
 */
public enum BookGenre {
    FICTION,
    NON_FICTION,
    SCIENCE_FICTION,
    MYSTERY,
    THRILLER,
    ROMANCE,
    BIOGRAPHY,
    HISTORY,
    SCIENCE,
    TECHNOLOGY,
    SELF_HELP,
    FANTASY,
    HORROR,
    POETRY,
    DRAMA,
    PHILOSOPHY,
    RELIGION,
    TRAVEL,
    COOKBOOK,
    ART,
    CHILDREN,
    YOUNG_ADULT,
    OTHER;

    @Override
    public String toString() {
        return name().replace('_', ' ');
    }
}

