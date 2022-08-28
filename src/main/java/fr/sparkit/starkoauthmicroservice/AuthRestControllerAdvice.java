package fr.sparkit.starkoauthmicroservice;


import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import fr.sparkit.starkoauthmicroservice.util.http.HttpErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice()
@Slf4j
public class AuthRestControllerAdvice {

    private static final int CUSTOM_STATUS_CODE = 225;

    @ExceptionHandler({HttpCustomException.class})
    public HttpErrorResponse handleCustomerException(HttpCustomException exception, HttpServletResponse response) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(exception.getErrorCode(),
                exception.getErrorsResponse());
        response.setStatus(CUSTOM_STATUS_CODE);
        log.error("{} => error code : {} , details : {}", HttpCustomException.class.getSimpleName(),
                exception.getErrorCode(), exception.getErrorsResponse().getErrors());
        return httpErrorResponse;
    }

}
