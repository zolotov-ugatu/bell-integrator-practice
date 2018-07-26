package ru.bellintegrator.practice.office.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.OfficeListFilter;
import ru.bellintegrator.practice.office.view.OfficeToSave;
import ru.bellintegrator.practice.office.view.OfficeView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Класс контроллера для работы с офисами
 */
@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeService officeService;

    /**
     * Конструктор
     * @param officeService сервис, предоставляющий методы работы с офисами
     */
    @Autowired
    public OfficeController(OfficeService officeService){
        this.officeService = officeService;
    }

    /**
     * Возвращает отфильтрованный список офисов.
     *
     * @param filter фильтр для списка
     * @return отфильтрованный список
     */
    @ApiOperation(value = "Get office list by filter", nickname = "getOfficeListByFilter", httpMethod = "POST")
    @PostMapping("/list")
    public List<OfficeView> list(@RequestBody OfficeListFilter filter){
        return officeService.list(filter);
    }


    /**
     * Возвращает офис с указанным идентификатором.
     *
     * @param id идентификатор офиса
     * @return офис с указанным идентификатором
     */
    @ApiOperation(value = "Get office by id", nickname = "getOfficeById", httpMethod = "GET")
    @GetMapping("/{id}")
    public OfficeView getById(@PathVariable Long id){
        return officeService.getById(id);
    }

    /**
     * Обновляет сведения об офисе.
     *
     * @param view объект, содержащий сведения для обновления
     */
    @ApiOperation(value = "Update office", nickname = "updateOffice", httpMethod = "POST")
    @PostMapping("/update")
    public void update(@RequestBody OfficeView view){
        officeService.update(view);
    }

    /**
     * Сохраняет сведения о новом офисе
     *
     * @param officeToSave объект, содержащий сведения о новом офисе
     */
    @ApiOperation(value = "Save new office", nickname = "saveOffice", httpMethod = "POST")
    @PostMapping("/save")
    public void save(@RequestBody OfficeToSave officeToSave){
        officeService.save(officeToSave);
    }
}
