package asia.ncc.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationResponse<T> {
    private T result;
    private boolean success;
    private String error;

    public static <T> ApplicationResponse<T> succeed(T result) {
        return new ApplicationResponse<>(result, true, null);
    }

    public static ApplicationResponse fail(String error) {
        return new ApplicationResponse(null, false, error);
    }
}
