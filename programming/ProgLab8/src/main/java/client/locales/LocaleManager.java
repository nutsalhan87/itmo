package client.locales;

import gui.TextDrawable;

import java.text.SimpleDateFormat;
import java.util.*;

public class LocaleManager {
    private static ResourceBundle currentLanguageResources =
            ResourceBundle.getBundle("client.locales.Locale", Locales.RUSSIAN.locale);
    private static final List<TextDrawable> changeLocaleListeners = new ArrayList<>();

    private LocaleManager() {}

    public static String getString(String key) {
        try {
            return currentLanguageResources.getString(key);
        } catch (MissingResourceException exc) {
            return key;
        }
    }

    public static String getDate(Date date) {
        if (currentLanguageResources.equals(ResourceBundle.getBundle("client.locales.Locale",
                Locales.RUSSIAN.locale))) {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        } else if (currentLanguageResources.equals(ResourceBundle.getBundle("client.locales.Locale",
                Locales.NORWEGIAN.locale))) {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else if (currentLanguageResources.equals(ResourceBundle.getBundle("client.locales.Locale",
                Locales.ITALIAN.locale))) {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } else {
            return new SimpleDateFormat("MM/dd/yyyy").format(date);
        }
    }

    public static void changeLocale(Locale locale) {
        currentLanguageResources = ResourceBundle.getBundle("client.locales.Locale", locale);
        callListeners();
    }

    public static void addListener(TextDrawable drawMethod) {
        changeLocaleListeners.add(drawMethod);
    }

    private static void callListeners() {
        for (TextDrawable changeLocaleListener : changeLocaleListeners) {
            changeLocaleListener.drawText();
        }
    }
}
