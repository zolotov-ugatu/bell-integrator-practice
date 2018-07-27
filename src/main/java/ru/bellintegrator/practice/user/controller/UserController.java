package ru.bellintegrator.practice.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.user.service.UserService;
import ru.bellintegrator.practice.user.view.UserListFilter;
import ru.bellintegrator.practice.user.view.UserToSave;
import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    /**
     * Конструктор
     *
     * @param userService сервис, предоставляющий методы для работы с пользователями
     */
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Возвращает отфильтрованный список пользователей.
     *
     * @param filter объект, содержащий сведения для фильтрации
     * @return отфильтрованный список пользователей
     */
    @ApiOperation(value = "Get user list by filter", nickname = "getUserListByFilter", httpMethod = "POST")
    @PostMapping("/list")
    public List<UserView> list(@RequestBody UserListFilter filter){
        return userService.list(filter);
    }

    /**
     * Возвращает пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя
     * @return пользователь с указанным идентификатором
     */
    @ApiOperation(value = "Get user by id", nickname = "getUserById", httpMethod = "GET")
    @GetMapping("/{id}")
    public UserView getById(@PathVariable Long id){
        return userService.getById(id);
    }

    /**
     * Обновляет сведения о пользователе.
     *
     * @param view объект, содержащий новые данные о пользователе
     */
    @ApiOperation(value = "Update user", nickname = "updateUser", httpMethod = "POST")
    @PostMapping("/update")
    public void update(@RequestBody UserView view){
        userService.update(view);
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param userToSave объект, содержащий сведения о нововм пользователе
     */
    @ApiOperation(value = "Save new user", nickname = "saveUser", httpMethod = "POST")
    @PostMapping("/save")
    public void save(@RequestBody UserToSave userToSave){
        userService.save(userToSave);
    }
}
