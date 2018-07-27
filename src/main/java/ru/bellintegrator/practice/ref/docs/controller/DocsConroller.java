package ru.bellintegrator.practice.ref.docs.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.ref.docs.service.DocsService;
import ru.bellintegrator.practice.ref.docs.view.DocView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class DocsConroller {

    private final DocsService docsService;

    /**
     * Конструктор
     *
     * @param docsService сервис, предоставляющий методы получения справочной информации о докуметах
     */
    @Autowired
    public DocsConroller(DocsService docsService){
        this.docsService = docsService;
    }

    /**
     * Возвращает список документов, удостоверяющих личность и их кодов.
     *
     * @return список документов и их кодов
     */
    @ApiOperation(value = "Get documents list", nickname = "getDocumentsList", httpMethod = "POST")
    @PostMapping("/docs")
    public List<DocView> list(){
        return docsService.list();
    }
}
