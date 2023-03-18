package general;

import java.io.Serializable;

public class Answer implements Serializable {
    private final Serializable answer;

    public Answer(Serializable answer) {
        this.answer = answer;
    }

    public Serializable getAnswer() {
        return answer;
    }
}