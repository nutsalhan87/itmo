package general;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {
    private Serializer() {}

    public static ByteBuffer serialize(Serializable obj) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(BufferSize.BUFFER_SIZE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BufferSize.BUFFER_SIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        bb.put(byteArrayOutputStream.toByteArray());
        bb.flip();

        return bb;
    }

    public static Serializable deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        ByteBuffer bb = byteBuffer.duplicate();
        bb.flip();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bb.array(), 0, bb.limit());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        return (Serializable) objectInputStream.readObject();
    }

}
