package humans;

public enum WearableThings {
    CYLINDER("Цилиндр"),
    BOOTS("Ботинки"),
    PANTS("Штаны"),
    COAT("Плащ"),
    LEATHER_STUFF("Кожанка");

    private String name;

    WearableThings(String inputName) {
        name = inputName;
    }

    @Override
    public String toString() {
        return name;
    }
}
