package service.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record
ResultResponse<T>(T result) {

    public static <T> ResultResponse<T> of(T result) {
        return new ResultResponse<>(result);
    }
}
