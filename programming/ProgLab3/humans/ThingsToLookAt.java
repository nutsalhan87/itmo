package humans;

public enum ThingsToLookAt {
    NEWSPAPER("Газета"),
    SKY("Небо"),
    MAILBOX("Почтовый ящик");

    private String name;

    ThingsToLookAt(String inputName) {
        name = inputName;
    }

    @Override
    public String toString() {
        return name;
    }
}
