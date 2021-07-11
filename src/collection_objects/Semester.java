package collection_objects;

public enum Semester {
    FIRST,
    THIRD,
    SIXTH,
    EIGHTH;

    public static String GetStringValues() {
        StringBuilder Result = new StringBuilder();
        for (int i = 0; i < values().length; i++ ) {
            Result.append(i).append(" - ").append(values()[i]).append(" | ");
        }
        return Result.toString();
    }
}
