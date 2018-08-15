package ru.bellintegrator.practice.response.advice;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.bellintegrator.practice.response.view.DataResponseView;
import ru.bellintegrator.practice.response.view.SuccessResponseView;

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
            DataResponseView view = new DataResponseView();
            view.data = o;
            return view;
        }
        SuccessResponseView view = new SuccessResponseView();
        view.result = "success";
        return view;
    }
}
