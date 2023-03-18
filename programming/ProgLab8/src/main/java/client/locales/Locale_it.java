package client.locales;

import java.util.ListResourceBundle;

public class Locale_it extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"login", "Accedi"},
                {"register", "Registrazione"},
                {"welcome", "Benvenuti nella guida del Daghestan!"},
                {"russian", "Russo"},
                {"norwegian", "Norvegese"},
                {"italian", "Italiano"},
                {"spanish", "Spagnolo"},
                {"language", "Lingua"},
                {"username", "Nome utente"},
                {"password", "Password"},
                {"user", "Utente"},
                {"filters", "Filtri"},
                {"script", "Esecuzione dello script"},
                {"map", "Mappa"},
                {"dagestanBase", "Database di percorsi personalizzati in Daghestan"},
                {"removeElements", "Elimina tutti i miei elementi"},
                {"exit", "Esci"},
                {"startsWith", "Inizia con una sottostringa"},
                {"contains", "Contiene una sottostringa"},
                {"scriptPath", "Percorso fino allo script"},
                {"execute", "Eseguire"},
                {"name", "Nome"},
                {"fromName", "Nome 'Da'"},
                {"distance", "Avstand"},
                {"remove", "Rimuovere"},
                {"send", "Inviare"},
                {"ifMax", "Modifica o aggiungi se l'elemento è massimo"},
                {"distanceRemove", "Elimina solo per Distanza"},
                {"lowerRemove", "Rimuovi tutti quelli più piccoli di questo"},
                {"greaterRemove", "Rimuovere tutto più grande di questo"},
                {"creationDate", "Data di creazione"},
                {"owner", "Creatore del percorso"},
                {"noScriptInScript", "Impossibile chiamare 'execute_script' all'interno di uno script"},
                {"noFileScript", "File di script non disponibile o non trovato"},
                {"wrongCommand", "Errore: o non esiste un comando del genere o non ci sono abbastanza argomenti"},
                {"emptyInput", "Stringa vuota inserita"},
                {"onlyRoute", "Il programma supporta solo il lavoro con Route"},
                {"unavailableId", "Un oggetto con tale id non è disponibile per l'aggiornamento"},
                {"onlyPositiveInt", "Un numero intero positivo deve essere inserito come id"},
                {"noExitInScript", "Il comando 'exit' non è consentito per l'esecuzione nello script"},
                {"correctDistance", "Immettere la distanza corretta come numero reale"},
                {"help", "help: visualizza la guida per i comandi disponibili\n" +
                        "info: stampa le informazioni sulla raccolta (tipo, data di inizializzazione, numero di elementi, ecc.)\n" +
                        "show: stampa nel flusso di output standard tutti gli elementi della raccolta nella rappresentazione di stringa\n" +
                        "add {element}: aggiungi un nuovo elemento alla raccolta\n" +
                        "update id: aggiorna il valore di un elemento della raccolta il cui id è uguale a quello specificato\n" +
                        "remove_by_id id: rimuovere un elemento da una raccolta dal suo id\n" +
                        "clear: pulisci la collezione\n" +
                        "execute_script file_name: leggere ed eseguire lo script dal file specificato. Lo script " +
                        "contiene i comandi nella stessa forma in cui l'utente li immette in modo interattivo\n" +
                        "add_if_max {element}: aggiungi un nuovo elemento a una raccolta se il suo valore supera il " +
                        "valore dell'elemento più grande di quella raccolta\n" +
                        "remove_greater {element}: rimuovere dalla raccolta tutti gli elementi che superano il valore specificato\n" +
                        "remove_lower {element}: rimuovere dalla raccolta tutti gli elementi più piccoli di quelli specificati\n" +
                        "remove_any_by_distance distance: rimuovere un elemento dalla raccolta il cui valore del campo " +
                        "distance è equivalente a quello specificato\n" +
                        "filter_contains_name name: stampa gli elementi il cui valore del campo name contiene la sottostringa specificata\n" +
                        "filter_starts_with_name name: stampa gli elementi il cui valore del campo name inizia con una sottostringa specificata"},
                {"addSuccessful", "Nuova istanza della classe aggiunta correttamente alla raccolta"},
                {"addUnsuccessful", "Impossibile aggiungere l'istanza della classe al database"},
                {"updateIdSuccessful", "Oggetto con tale id aggiornato con successo"},
                {"updateIdUnsuccessful", "Nessun aggiornamento"},
                {"removeIdSuccessful", "L'oggetto con tale id, se presente, viene eliminato correttamente"},
                {"removeUnsuccessful", "L'eliminazione non è avvenuta"},
                {"yourDataRemoved", "I tuoi dati, se presenti, vengono rimossi dalla raccolta"},
                {"isNotMax", "Il nuovo oggetto non è più grande dell'elemento massimo della raccolta, quindi non è stato aggiunto"},
                {"removedGreater", "Gli elementi della raccolta che superano il valore specificato vengono eliminati correttamente"},
                {"removedLower", "Gli elementi della raccolta inferiori a quelli specificati vengono eliminati correttamente"},
                {"removeDistanceSuccessful", "Il primo elemento contatore nella raccolta il cui valore distance è " +
                        "uguale a quello specificato, se trovato, viene eliminato"},
                {"elementNotFound", "Tale elemento non è stato trovato"},
                {"valuesRestrictions", "Verificare le limitazioni fornite:\n" +
                        "Nessun campo può essere vuoto\n" +
                        "Tutti i campi tranne il nome e il nome From devono essere numeri\n" +
                        "X deve essere maggiore di -140\n" +
                        "La distanza deve essere maggiore di 1"},
                {"port", "Porta"},
                {"inputPositiveIntPort", "Immettere un numero intero positivo come porta"},
                {"invalidPort", "Impossibile connettersi su questa porta"},
                {"authorizationUnsuccessful", "Impossibile accedere"},
                {"registerUnsuccessful", "Impossibile creare un account"},
                {"selectPoint", "Scegli un punto"},
                {"pointCreator", "Creatore del punto"},
                {"from", "Da"},
                {"to", "Al"},
                {"noNextLine", "Durante l'esecuzione dello script, il programma ha raggiunto la fine del file, " +
                        "quindi non è stato completato correttamente"}
        };
    }
}
