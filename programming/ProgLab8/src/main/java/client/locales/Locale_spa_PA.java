package client.locales;

import java.util.ListResourceBundle;

public class Locale_spa_PA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"login", "Entrar"},
                {"register", "Registro"},
                {"welcome", "Bienvenido a la guía de Daguestán!"},
                {"russian", "Rusa"},
                {"norwegian", "Noruego"},
                {"italian", "Italiano"},
                {"spanish", "Español"},
                {"language", "Idioma"},
                {"username", "Nombre de usuario"},
                {"password", "Contraseña"},
                {"user", "Usuario"},
                {"filters", "Filtros"},
                {"script", "Ejecución del script"},
                {"map", "Mapa"},
                {"dagestanBase", "Base de rutas personalizadas en Daguestán"},
                {"removeElements", "Eliminar todos mis elementos"},
                {"exit", "Salir"},
                {"startsWith", "Comienza con una subcadena"},
                {"contains", "Contiene una subcadena"},
                {"scriptPath", "Ruta de acceso al script"},
                {"execute", "Ejecutar"},
                {"name", "Nombre"},
                {"fromName", "Nombre 'Desde'"},
                {"distance", "Distancia"},
                {"remove", "Eliminar"},
                {"send", "Enviar"},
                {"ifMax", "Editar o agregar si el elemento es máximo"},
                {"distanceRemove", "Eliminar sólo por distancia"},
                {"lowerRemove", "Eliminar todos los más pequeños que este"},
                {"greaterRemove", "Eliminar todos los grandes que este"},
                {"creationDate", "Fecha de creación"},
                {"owner", "Creador de ruta"},
                {"noScriptInScript", "No se puede llamar a 'execute_script' dentro de un script"},
                {"noFileScript", "El archivo de script no está disponible o no se encuentra"},
                {"wrongCommand", "Error: o no hay tal comando o no hay suficientes argumentos"},
                {"emptyInput", "Se ha introducido una línea vacía"},
                {"onlyRoute", "El programa solo Admite el trabajo con Route"},
                {"unavailableId", "El objeto con este id no está disponible para la actualización"},
                {"onlyPositiveInt", "Se debe introducir un número entero positivo como id"},
                {"noExitInScript", "El comando 'exit' no se puede ejecutar en el script"},
                {"correctDistance", "Introduzca la distancia correcta como un número real"},
                {"help", "help: muestra la ayuda de los comandos disponibles\n" +
                        "info: muestra la información de la colección (tipo, fecha de inicialización, número de " +
                        "elementos, etc.)en el flujo de salida estándar\n" +
                        "show: genere todos los elementos de la colección en la vista de cadena en el flujo de salida estándar\n" +
                        "add {element}: añadir un nuevo elemento a la colección\n" +
                        "update id: actualizar el valor de un elemento de colección cuyo id es igual al especificado\n" +
                        "remove_by_id id: eliminar un elemento de la colección por su id\n" +
                        "clear: borrar colección\n" +
                        "execute_script file_name: Leer y ejecutar el script desde el archivo especificado. El script " +
                        "contiene los comandos en la misma forma en que los ingresa el usuario de forma interactiva\n" +
                        "add_if_max {element}: agregue un nuevo elemento a la colección si su valor excede el valor " +
                        "del elemento más grande de la colección\n" +
                        "remove_greater {element}: eliminar de la colección todos los elementos que excedan el valor especificado\n" +
                        "remove_lower {element}: eliminar de la colección todos los elementos más pequeños que el especificado\n" +
                        "remove_any_by_distance distance: eliminar de la colección un elemento cuyo valor de campo " +
                        "distance es equivalente al especificado\n" +
                        "filter_contains_name name: muestra los elementos cuyo valor de campo name contiene la subcadena especificada\n" +
                        "filter_starts_with_name name: muestra los elementos cuyo valor de campo name comienza con la subcadena especificada"},
                {"addSuccessful", "La nueva instancia de clase se agregó correctamente a la colección"},
                {"addUnsuccessful", "La instancia de clase no se pudo agregar a la base de datos"},
                {"updateIdSuccessful", "El objeto con este id se ha actualizado correctamente"},
                {"updateIdUnsuccessful", "No se ha producido ninguna actualización"},
                {"removeIdSuccessful", "El objeto con este id, si lo hubiera, se eliminó correctamente"},
                {"removeUnsuccessful", "La eliminación no ocurrió"},
                {"yourDataRemoved", "Sus datos, si los hubiera, se eliminaron de la colección"},
                {"isNotMax", "El nuevo objeto no es mayor que el elemento máximo de la colección, por lo que no se ha añadido"},
                {"removedGreater", "Los elementos de la colección que exceden el especificado se eliminaron correctamente"},
                {"removedLower", "Los elementos de colección que son más pequeños que el especificado se eliminan correctamente"},
                {"removeDistanceSuccessful", "Se elimina el primer contador de la colección cuyo valor de distance " +
                        "es igual al especificado, si se encontró uno"},
                {"elementNotFound", "No se encontró tal elemento"},
                {"valuesRestrictions", "Compruebe los datos de restricción:\n" +
                        "Ningún campo puede estar vacío\n" +
                        "Todos los campos, excepto el nombre y el nombre From, deben ser números\n" +
                        "X debe ser mayor que -140\n" +
                        "La distancia debe ser mayor que 1"},
                {"port", "Puerto"},
                {"inputPositiveIntPort", "Escriba un número entero positivo como puerto"},
                {"invalidPort", "No se pudo conectar en este puerto"},
                {"authorizationUnsuccessful", "No se pudo iniciar sesión"},
                {"registerUnsuccessful", "No se pudo crear una cuenta"},
                {"selectPoint", "Seleccione un punto"},
                {"pointCreator", "Creador de puntos"},
                {"from", "Desde"},
                {"to", "A"},
                {"noNextLine", "Durante la ejecución del script, el programa llegó al final del archivo, " +
                        "por lo que no completó su ejecución correctamente"}
        };
    }
}
