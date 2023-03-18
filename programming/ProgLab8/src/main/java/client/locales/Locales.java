package client.locales;

import java.util.Locale;

public enum Locales {
    RUSSIAN(new Locale("ru")),
    NORWEGIAN(new Locale("nor")),
    ITALIAN(Locale.ITALIAN),
    SPANISH(new Locale("spa", "PA"));
    Locale locale;
    Locales(Locale locale) {
        this.locale = locale;
    }
    public Locale getLocale() {
        return locale;
    }
}
