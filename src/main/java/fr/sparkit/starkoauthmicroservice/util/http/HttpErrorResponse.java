package fr.sparkit.starkoauthmicroservice.util.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HttpErrorResponse {

    private int errorCode;
    private List<Object> errors;

    public HttpErrorResponse(int errorCode, ErrorsResponse errorsResponse) {
        super();
        this.errorCode = errorCode;
        this.errors = errorsResponse.getErrors();
    }

    public HttpErrorResponse(int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errors = new ArrayList<>();
    }

}
