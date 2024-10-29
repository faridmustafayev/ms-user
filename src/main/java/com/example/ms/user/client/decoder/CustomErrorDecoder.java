package com.example.ms.user.client.decoder;

import com.example.ms.user.exception.CustomFeignException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static com.example.ms.user.client.decoder.JsonNodeFieldName.CODE;
import static com.example.ms.user.client.decoder.JsonNodeFieldName.MESSAGE;
import static com.example.ms.user.exception.ExceptionConstants.CLIENT_ERROR;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var errorCode = CLIENT_ERROR.getCode();
        var errorMessage = CLIENT_ERROR.getMessage();

        JsonNode jsonNode;
        try (var body = response.body().asInputStream()) {
            jsonNode = new ObjectMapper().readValue(body, JsonNode.class);
        } catch (Exception ex) {
            throw new CustomFeignException(errorCode, errorMessage, response.status());
        }

        if (jsonNode.has(MESSAGE.getValue())) {
            errorCode = jsonNode.get(CODE.getValue()).asText();
            errorMessage = jsonNode.get(MESSAGE.getValue()).asText();
        }

        log.error("ActionLog.decode.error Code: {}, Message: {}, Method: {}", errorCode, errorMessage, methodKey);
        return new CustomFeignException(errorCode, errorMessage, response.status());
    }
}
