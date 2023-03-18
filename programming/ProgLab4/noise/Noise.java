package noise;

public class Noise {
    private final Object obj;
    private final int noiseLevel;

    public Noise(int inputNoiseLevel, Object inputObj) {
        noiseLevel = inputNoiseLevel;
        obj = inputObj;
    }

    public Object getObj() {
        return obj;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }
}
