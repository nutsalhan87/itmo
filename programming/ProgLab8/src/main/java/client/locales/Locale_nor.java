package client.locales;

import java.util.ListResourceBundle;

public class Locale_nor extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"login", "Inngang"},
                {"register", "Registrering"},
                {"welcome", "Velkommen Til Dagestan Reiseguide!"},
                {"russian", "Russisk"},
                {"norwegian", "Norsk"},
                {"italian", "Italiensk"},
                {"spanish", "Spansk"},
                {"language", "Språk"},
                {"username", "Brukernavn"},
                {"password", "Passord"},
                {"user", "Brukeren"},
                {"filters", "Filter"},
                {"script", "Kjøring Av Skript"},
                {"map", "Kart"},
                {"dagestanBase", "Database over brukerruter I Dagestan"},
                {"removeElements", "Slett alle mine elementer"},
                {"exit", "Gå ut"},
                {"startsWith", "Starter med en substring"},
                {"contains", "Inneholder en substring"},
                {"scriptPath", "Veien til manuset"},
                {"execute", "Utføre"},
                {"name", "Navn"},
                {"fromName", "Navn 'Fra'"},
                {"distance", "Avstand"},
                {"remove", "Fjerne"},
                {"send", "Sende"},
                {"ifMax", "Rediger eller legg til hvis elementet er maksimalt"},
                {"distanceRemove", "Slett bare etter avstand"},
                {"lowerRemove", "Fjern alle mindre enn denne"},
                {"greaterRemove", "Fjern alle større enn denne"},
                {"creationDate", "Dato for opprettelse"},
                {"owner", "Route Creator"},
                {"noScriptInScript", "Du kan ikke ringe 'execute_script' inne i et skript"},
                {"noFileScript", "Skriptfilen er utilgjengelig eller ikke funnet"},
                {"wrongCommand", "Feil: enten er det ingen slik kommando, eller det er ikke nok argumenter"},
                {"emptyInput", "En tom streng er angitt"},
                {"onlyRoute", "Programmet støtter bare arbeide med 'Route'"},
                {"unavailableId", "Et objekt med denne id-en er ikke tilgjengelig for oppdatering"},
                {"onlyPositiveInt", "Et positivt heltall må oppgis som id"},
                {"noExitInScript", "Kommandoen 'exit' er forbudt å utføre i skriptet"},
                {"correctDistance", "Angi riktig avstand som et reelt tall"},
                {"help", "help: utdata hjelp for tilgjengelige kommandoer\n" +
                        "info: utdata informasjon om samlingen (type, initialisering dato, antall elementer, etc.) " +
                        "til standard utgangsstrøm.\n" +
                        "show: utgang til standard utgangsstrøm alle elementene i samlingen i en strengrepresentasjon\n" +
                        "add {element}: legge til et nytt element i samlingen\n" +
                        "update id: oppdatere verdien av et samlingselement hvis id er lik den angitte\n" +
                        "remove_by_id id: slette et element fra samlingen ved hjelp av id-en\n" +
                        "clear: tøm samlingen\n" +
                        "execute_script file_name: les og kjør skriptet fra den angitte filen. Skriptet inneholder " +
                        "kommandoer i samme form som de er angitt av brukeren i interaktiv modus\n" +
                        "add_if_max {element}: legge til et nytt element i samlingen hvis verdien overstiger verdien " +
                        "for det største elementet i denne samlingen\n" +
                        "remove_greater {element}: fjern alle elementer fra samlingen som overskrider den angitte\n" +
                        "remove_lower {element}: fjern alle elementer som er mindre enn den angitte fra samlingen\n" +
                        "remove_any_by_distance distance: slett ett element fra samlingen, verdien av avstandsfeltet " +
                        "som tilsvarer det angitte\n" +
                        "filter_contains_name name: utgangselementer hvis \"name\" - feltverdi inneholder den angitte substringen\n" +
                        "filter_starts_with_name name: utgangselementer hvis feltverdi \"name\" starter med den angitte substringen"},
                {"addSuccessful", "En ny forekomst av klassen har blitt lagt til samlingen"},
                {"addUnsuccessful", "En forekomst av klassen kan ikke legges til i databasen"},
                {"updateIdSuccessful", "Et objekt med denne iden har blitt oppdatert"},
                {"updateIdUnsuccessful", "Det var ingen oppdatering"},
                {"removeIdSuccessful", "Et objekt med denne iden, hvis noen, har blitt slettet"},
                {"removeUnsuccessful", "Slettingen skjedde ikke"},
                {"yourDataRemoved", "Dine data, hvis noen, har blitt slettet fra samlingen"},
                {"isNotMax", "Det nye objektet er ikke større enn det maksimale elementet i samlingen, så det ble ikke lagt til"},
                {"removedGreater", "Samlingselementer som overstiger den angitte, har blitt slettet"},
                {"removedLower", "Samlingselementer som er mindre enn den angitte, har blitt slettet"},
                {"removeDistanceSuccessful", "Det første tellerelementet i samlingen hvis \"distance\" - verdi er lik " +
                        "den angitte, hvis en ble funnet, slettes"},
                {"elementNotFound", "Ingen slike elementer ble funnet"},
                {"valuesRestrictions", "Sjekk disse restriksjonene:\n" +
                        "Ingen felt kan være tomme\n" +
                        "Alle felt unntatt navnet og navnet \"From\" må være tall\n" +
                        "X må være større enn -140\n" +
                        "Avstanden må være større enn 1"},
                {"port", "Port"},
                {"inputPositiveIntPort", "Skriv inn et positivt heltall som port"},
                {"invalidPort", "Kunne ikke koble til denne porten"},
                {"authorizationUnsuccessful", "Kunne ikke logge inn"},
                {"registerUnsuccessful", "Kunne ikke opprette en konto"},
                {"selectPoint", "Velg et punkt"},
                {"pointCreator", "Point Creator"},
                {"from", "Fra"},
                {"to", "Til"},
                {"noNextLine", "Under utførelsen av skriptet nådde programmet slutten av filen, og fullførte dermed" +
                        " ikke utførelsen riktig"}
        };
    }
}
