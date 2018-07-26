package ru.bellintegrator.practice.organization.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.OrganizationListFilter;
import ru.bellintegrator.practice.organization.view.OrganizationToSave;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Класс контроллера для работы с организациями
 */
@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationService organizationService;

    /**
     * Конструктор
     *
     * @param organizationService сервис, предоставляющий методы работы с организациями
     */
    @Autowired
    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    /**
     * Возвращает отфильтрованный список организаций.
     *
     * @param filter фильтр для списка
     * @return отфильтрованный список
     */
    @ApiOperation(value = "Get organization list by filter", nickname = "getOrganizationsListByFilter", httpMethod = "POST")
    @PostMapping("/list")
    public List<OrganizationView> list(@RequestBody OrganizationListFilter filter){
        return organizationService.list(filter);
    }

    /**
     * Возвращает организацию с указанным идентификатором.
     *
     * @param id идентификатор
     * @return организация с указанным идентификатором
     */
    @ApiOperation(value = "Get organization by Id", nickname = "getOrganizationById", httpMethod = "GET")
    @GetMapping("/{id}")
    public OrganizationView getById(@PathVariable Long id){
        return organizationService.getById(id);
    }

    /**
     * Обновляет сведения об организации.
     *
     * @param view объект с обновленными данными
     */
    @ApiOperation(value = "Updates an organization", nickname = "updateOrganization", httpMethod = "POST")
    @PostMapping("/update")
    public void update(@RequestBody OrganizationView view){
        organizationService.update(view);
    }

    /**
     * Сохраняет сведения о новой организации.
     *
     * @param orgToSave объект, содержащий сведения о новой организации
     */
    @ApiOperation(value = "Saves an organization", nickname = "saveOrganization", httpMethod = "POST")
    @PostMapping("/save")
    public void save(@RequestBody OrganizationToSave orgToSave){
        organizationService.save(orgToSave);
    }

}
