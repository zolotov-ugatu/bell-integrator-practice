package ru.bellintegrator.practice.user.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.bellintegrator.practice.Application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
@Sql(statements = {
        "DELETE FROM User;",
        "DELETE FROM Doc;",
        "DELETE FROM Country;",
        "INSERT INTO Doc (id, code, name) VALUES " +
                "(1, 21, 'Паспорт гражданина Российской Федерации')," +
                "(2, 12, 'Вид на жительство в Российской Федерации');",
        "INSERT INTO Country (id, code, name) VALUES " +
                "(1, 643, 'Российская Федерация')," +
                "(2, 498, 'Республика Молдова');"
})
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private String jsonBodyFirst;
    private String jsonBodySecond;
    private Long officeId;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
        mockMvc.perform(post("/api/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"Первая организация\", " +
                        "\"fullName\": \"Полное название первой организации\"," +
                        "\"inn\": \"1111111111\"," +
                        "\"kpp\": \"111111111\"," +
                        "\"address\": \"Россия, г. Москва, ул. Ленина, д. 1\"," +
                        "\"phone\":\"+7 111 111-11-11\"," +
                        "\"isActive\": \"true\"}"));
        String response = mockMvc.perform(post("/api/organization/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"Первая организация\", " +
                        "\"fullName\": \"Полное название первой организации\"," +
                        "\"inn\": \"1111111111\"," +
                        "\"isActive\": \"true\"}"))
                .andReturn().getResponse().getContentAsString();
        Long orgId = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"Первый офис\", " +
                        "\"orgId\": \"" + orgId + "\"," +
                        "\"address\": \"Россия, г. Москва, ул. К. Маркса, д. 1\"," +
                        "\"phone\":\"+7 001 111-11-11\"," +
                        "\"isActive\": \"true\"}"));
        response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"orgId\": \"" + orgId + "\"}"))
                .andReturn().getResponse().getContentAsString();
        officeId = JsonPath.parse(response).read("$.data[0].id", Long.class);
        jsonBodyFirst = "{\"firstName\": \"Иван\", " +
                "\"lastName\": \"Иванов\", " +
                "\"middleName\": \"Иванович\", " +
                "\"officeId\": \"" + officeId + "\", " +
                "\"position\": \"Работник\", " +
                "\"phone\": \"+7 000 111 11 11\", " +
                "\"docCode\": \"21\", " +
                "\"docNumber\": \"AA 111111\", " +
                "\"docDate\": \"1991-01-01\", " +
                "\"citizenshipCode\": \"643\", " +
                "\"isIdentified\": \"true\"}";
        jsonBodySecond = "{\"firstName\": \"Петр\", " +
                "\"lastName\": \"Петров\", " +
                "\"middleName\": \"Петрович\", " +
                "\"officeId\": \"" + officeId + "\", " +
                "\"position\": \"Директор\", " +
                "\"phone\": \"+7 000 222 22 22\", " +
                "\"docCode\": \"12\", " +
                "\"docNumber\": \"BB 222222\", " +
                "\"docDate\": \"1992-02-02\", " +
                "\"citizenshipCode\": \"498\", " +
                "\"isIdentified\": \"false\"}";
        mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFirst));
    }

    @Test
    public void testListMultipleResultByOfficeId() throws Exception {
        saveSecondUser();
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].firstName", is("Иван")))
                .andExpect(jsonPath("$.data[0].lastName", is("Иванов")))
                .andExpect(jsonPath("$.data[0].middleName", is("Иванович")))
                .andExpect(jsonPath("$.data[0].position", is("Работник")))
                .andExpect(jsonPath("$.data[1].id", notNullValue()))
                .andExpect(jsonPath("$.data[1].firstName", is("Петр")))
                .andExpect(jsonPath("$.data[1].lastName", is("Петров")))
                .andExpect(jsonPath("$.data[1].middleName", is("Петрович")))
                .andExpect(jsonPath("$.data[1].position", is("Директор")));
    }

    @Test
    public void testListSingleResultByNameLike() throws Exception {
        saveSecondUser();
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\", \"lastName\": \"Иван\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].firstName", is("Иван")))
                .andExpect(jsonPath("$.data[0].lastName", is("Иванов")))
                .andExpect(jsonPath("$.data[0].middleName", is("Иванович")))
                .andExpect(jsonPath("$.data[0].position", is("Работник")));
    }

    @Test
    public void testListEmptyResultByDocCode() throws Exception {
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\", \"docCode\": \"0\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListEmptyResultByCitizenshipCode() throws Exception {
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\", \"citizenshipCode\": \"0\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListSingleResultByAllParameters() throws Exception {
        saveSecondUser();
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\", " +
                "\"firstName\": \"Иван\", " +
                "\"lastName\": \"Иванов\", " +
                "\"middleName\": \"Иванович\", " +
                "\"position\": \"Работник\", " +
                "\"docCode\": \"21\", " +
                "\"citizenshipCode\": \"643\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].firstName", is("Иван")))
                .andExpect(jsonPath("$.data[0].lastName", is("Иванов")))
                .andExpect(jsonPath("$.data[0].middleName", is("Иванович")))
                .andExpect(jsonPath("$.data[0].position", is("Работник")));
    }

    @Test
    public void testListErrorOfficeIdIsNull() throws Exception {
        String jsonBodyFilter = "{\"firstName\": \"Иван\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testListEmptyResultNoOffice() throws Exception {
        String jsonBodyFilter = "{\"officeId\": \"" + (officeId + 1) + "\", \"docCode\": \"0\"}";
        mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testGetByIdSuccess() throws Exception {
        Long id = getFirstUserId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        mockMvc.perform(get("/api/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(id.intValue())))
                .andExpect(jsonPath("$.data.firstName", is("Иван")))
                .andExpect(jsonPath("$.data.lastName", is("Иванов")))
                .andExpect(jsonPath("$.data.middleName", is("Иванович")))
                .andExpect(jsonPath("$.data.position", is("Работник")))
                .andExpect(jsonPath("$.data.phone", is("+7 000 111 11 11")))
                .andExpect(jsonPath("$.data.docName", is("Паспорт гражданина Российской Федерации")))
                .andExpect(jsonPath("$.data.docDate", is(format.parse("1991-01-01").getTime())))
                .andExpect(jsonPath("$.data.docNumber", is("AA 111111")))
                .andExpect(jsonPath("$.data.isIdentified", is(true)));
    }

    @Test
    public void testGetByIdErrorNotFound() throws Exception {
        Long id = getFirstUserId();
        mockMvc.perform(get("/api/user/" + (id + 1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Long id = getFirstUserId();
        mockMvc.perform(post("/api/user/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"id\": \"" + id + "\", " + jsonBodySecond.substring(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        mockMvc.perform(get("/api/user/" + id))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(id.intValue())))
                .andExpect(jsonPath("$.data.firstName", is("Петр")))
                .andExpect(jsonPath("$.data.lastName", is("Петров")))
                .andExpect(jsonPath("$.data.middleName", is("Петрович")))
                .andExpect(jsonPath("$.data.position", is("Директор")))
                .andExpect(jsonPath("$.data.phone", is("+7 000 222 22 22")))
                .andExpect(jsonPath("$.data.docName", is("Вид на жительство в Российской Федерации")))
                .andExpect(jsonPath("$.data.docDate", is(format.parse("1992-02-02").getTime())))
                .andExpect(jsonPath("$.data.docNumber", is("BB 222222")))
                .andExpect(jsonPath("$.data.isIdentified", is(false)));
    }

    @Test
    public void testUpdateErrorNoId() throws Exception {
        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond.substring(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateErrorNotFound() throws Exception {
        Long id = getFirstUserId();
        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\": \"" + (id + 1) + "\", " + jsonBodySecond.substring(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testSaveSuccess() throws Exception {
        mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
        String response = mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"officeId\": \"" + officeId + "\", \"firstName\":\"Петр\"}"))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        mockMvc.perform(get("/api/user/" + id))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(id.intValue())))
                .andExpect(jsonPath("$.data.firstName", is("Петр")))
                .andExpect(jsonPath("$.data.lastName", is("Петров")))
                .andExpect(jsonPath("$.data.middleName", is("Петрович")))
                .andExpect(jsonPath("$.data.position", is("Директор")))
                .andExpect(jsonPath("$.data.phone", is("+7 000 222 22 22")))
                .andExpect(jsonPath("$.data.docName", is("Вид на жительство в Российской Федерации")))
                .andExpect(jsonPath("$.data.docDate", is(format.parse("1992-02-02").getTime())))
                .andExpect(jsonPath("$.data.docNumber", is("BB 222222")))
                .andExpect(jsonPath("$.data.isIdentified", is(false)));
    }

    @Test
    public void testSaveErrorNotFullData() throws Exception {
        mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"firstName\": \"Иван\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testRemoveSuccess() throws Exception {
        Long id = getFirstUserId();
        mockMvc.perform(post("/api/user/remove/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testRemoveErrorNotFound() throws Exception {
        Long id = getFirstUserId();
        mockMvc.perform(post("/api/user/remove/" + (id + 1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    public void saveSecondUser() throws Exception {
        mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond));
    }

    public Long getFirstUserId() throws Exception {
        String jsonBodyFilter = "{\"officeId\": \"" + officeId + "\"}";
        String response = mockMvc.perform(post("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter)).andReturn().getResponse().getContentAsString();
        return JsonPath.parse(response).read("$.data[0].id", Long.class);
    }
}
