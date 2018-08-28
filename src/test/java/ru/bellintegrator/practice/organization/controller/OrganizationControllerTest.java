package ru.bellintegrator.practice.organization.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.bellintegrator.practice.Application;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OrganizationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private String jsonBodyFirst;
    private String jsonBodySecond;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
        jsonBodyFirst = "{\"name\": \"Первая организация\", " +
                "\"fullName\": \"Полное название первой организации\"," +
                "\"inn\": \"1111111111\"," +
                "\"kpp\": \"111111111\"," +
                "\"address\": \"Россия, г. Москва, ул. Ленина, д. 1\"," +
                "\"phone\":\"+7 111 111-11-11\"," +
                "\"isActive\": \"true\"}";
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFirst));
        jsonBodySecond = "{\"name\": \"Вторая организация\", " +
                "\"fullName\": \"Полное название второй организации\"," +
                "\"inn\": \"2222222222\"," +
                "\"kpp\": \"222222222\"," +
                "\"address\": \"Россия, г. Москва, ул. Ленина, д. 2\"," +
                "\"phone\":\"+7 222 222-22-22\"," +
                "\"isActive\": \"false\"}";
    }

    @Test
    public void testListMultipleResultByName() throws Exception {
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond));
        String jsonBodyFilter = "{\"name\": \"организация\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)))
                .andExpect(jsonPath("$.data[1].name", is("Вторая организация")))
                .andExpect(jsonPath("$.data[1].fullName", is("Полное название второй организации")))
                .andExpect(jsonPath("$.data[1].inn", is("2222222222")))
                .andExpect(jsonPath("$.data[1].kpp", is("222222222")))
                .andExpect(jsonPath("$.data[1].address", is("Россия, г. Москва, ул. Ленина, д. 2")))
                .andExpect(jsonPath("$.data[1].phone", is("+7 222 222-22-22")))
                .andExpect(jsonPath("$.data[1].isActive", is(false)));
    }

    @Test
    public void testListSingleResultByNameExact() throws Exception{
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"Первая организация\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListSingleResultByNameLike() throws Exception{
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"Первая\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByName() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"ПерваяВторая\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListResultByInn() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"организация\", \"inn\": \"1111111111\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByInn() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"Первая организация\", \"inn\": \"2222222222\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListResultByIsActive() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"организация\", \"isActive\": \"true\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByIsActive() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"Первая организация\", \"isActive\": \"false\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListResultByAllParameters() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"организация\", \"inn\": \"1111111111\", \"isActive\": \"true\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Первая организация")))
                .andExpect(jsonPath("$.data[0].fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data[0].inn", is("1111111111")))
                .andExpect(jsonPath("$.data[0].kpp", is("111111111")))
                .andExpect(jsonPath("$.data[0].address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data[0].phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByAllParameters() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"name\": \"организация\", \"inn\": \"1111111111\", \"isActive\": \"false\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListErrorNameIsNull() throws Exception {
        saveSecondOrganization();
        String jsonBodyFilter = "{\"inn\": \"1111111111\", \"isActive\": \"false\"}";
        mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testIdSuccess() throws Exception {
        Long id = getFirstOrganizationId();
        mockMvc.perform(get("/api/organization/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.name", is("Первая организация")))
                .andExpect(jsonPath("$.data.fullName", is("Полное название первой организации")))
                .andExpect(jsonPath("$.data.inn", is("1111111111")))
                .andExpect(jsonPath("$.data.kpp", is("111111111")))
                .andExpect(jsonPath("$.data.address", is("Россия, г. Москва, ул. Ленина, д. 1")))
                .andExpect(jsonPath("$.data.phone", is("+7 111 111-11-11")))
                .andExpect(jsonPath("$.data.isActive", is(true)));
    }

    @Test
    public void testIdErrorNotFound() throws Exception {
        Long id = getFirstOrganizationId();
        mockMvc.perform(get("/api/organization/" + ++id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Long id = getFirstOrganizationId();
        mockMvc.perform(post("/api/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":\"" + id + "\", " + jsonBodySecond.substring(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")));
        mockMvc.perform(get("/api/organization/" + id))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.name", is("Вторая организация")))
                .andExpect(jsonPath("$.data.fullName", is("Полное название второй организации")))
                .andExpect(jsonPath("$.data.inn", is("2222222222")))
                .andExpect(jsonPath("$.data.kpp", is("222222222")))
                .andExpect(jsonPath("$.data.address", is("Россия, г. Москва, ул. Ленина, д. 2")))
                .andExpect(jsonPath("$.data.phone", is("+7 222 222-22-22")))
                .andExpect(jsonPath("$.data.isActive", is(false)));
        mockMvc.perform(post("/api/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateErrorIdIsNull() throws Exception {
        Long id = getFirstOrganizationId();
        mockMvc.perform(post("/api/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testSaveSuccess() throws Exception {
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", is("success")));
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"Организация\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue(String.class)));
    }

    @Test
    public void testSaveErrorNotEnoughParameters() throws Exception {
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"Организация\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue(String.class)));
    }

    @Test
    public void testRemoveSuccess() throws Exception{
        Long id = getFirstOrganizationId();
        mockMvc.perform(post("/api/organization/remove/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", is("success")));
        mockMvc.perform(get("/api/organization/" + id))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testRemoveErrorAlreadyRemoved() throws Exception{
        Long id = getFirstOrganizationId();
        mockMvc.perform(post("/api/organization/remove/" + id));
        mockMvc.perform(post("/api/organization/remove/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    private void saveSecondOrganization() throws Exception {
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond));
    }

    private Long getFirstOrganizationId() throws Exception {
        String jsonBodyFilter = "{\"name\": \"Первая организация\", \"inn\": \"1111111111\", \"isActive\": \"true\"}";
        String response = mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        return JsonPath.parse(response).read("$.data[0].id", Long.class);
    }
}
