package general;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {
    private Serializer() {}

    public static ByteBuffer serialize(Request obj) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(BufferSize.BUFFER_SIZE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BufferSize.BUFFER_SIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        bb.put(byteArrayOutputStream.toByteArray());
        bb.flip();

        return bb;
    }

    public static Request deserialize(ByteBuffer bb) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bb.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        return (Request) objectInputStream.readObject();
    }

}
