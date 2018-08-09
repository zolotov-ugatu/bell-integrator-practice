package ru.bellintegrator.practice.advice;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * {@inheritDoc}
 */
@RestControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getMethodAnnotation(ApiOperation.class) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (methodParameter.getParameterType() != void.class){
            return new Object(){
                public Object data = o;
            };
        }
        return new Object(){
            public String result = "success";
        };
    }
}
