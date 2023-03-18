package workwithexternaldata;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The interface contains the header of a method that implements saving an object of a generalized type
 * (depends on the implementation) to data of a certain format (also depends on the implementation)
 * @param <T>
 */

public interface SaveData<T> {
    void saveInFile(List<T> data, File file) throws IOException;
}
