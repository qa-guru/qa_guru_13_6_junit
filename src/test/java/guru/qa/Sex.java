package guru.qa;

public enum Sex {
    MALE("Мужчина"),
    FEMALE("Девушка");

    public final String desc;

    Sex(String desc) {
        this.desc = desc;
    }
}
