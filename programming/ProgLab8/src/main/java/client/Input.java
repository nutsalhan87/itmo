package client;

import java.io.IOException;

/**
 * The functional interface contains the header of the method implementing data input from an external source
 */
public interface Input {
    String readLine() throws IOException;
}
