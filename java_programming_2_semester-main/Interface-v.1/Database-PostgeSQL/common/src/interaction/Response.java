package interaction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String answer;

    public Response(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
