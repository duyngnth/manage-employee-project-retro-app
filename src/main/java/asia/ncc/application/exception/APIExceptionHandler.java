package asia.ncc.application.exception;

import asia.ncc.application.payload.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationResponse handleCRUDException(EntityNotFoundException e, WebRequest request) {
        return new ApplicationResponse(null, false, e.getMessage());
    }

    @ExceptionHandler({
            EmployeeException.class,
            ProjectException.class,
            AssignmentException.class,
            EvaluationException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApplicationResponse handleEntityException(Exception e, WebRequest request) {
        return new ApplicationResponse(null, false, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationResponse handleSQLException(SQLException e, WebRequest request) {
        return new ApplicationResponse(null, false, e.getMessage());
    }
}
