package ru.bellintegrator.practice.office.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OfficeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private String jsonBodyFirst;
    private String jsonBodySecond;
    private Long orgId;

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
        orgId = JsonPath.parse(response).read("$.data[0].id", Long.class);
        jsonBodyFirst = "{\"name\": \"Первый офис\", " +
                "\"orgId\": \"" + orgId + "\"," +
                "\"address\": \"Россия, г. Москва, ул. К. Маркса, д. 1\"," +
                "\"phone\":\"+7 001 111-11-11\"," +
                "\"isActive\": \"true\"}";
        mockMvc.perform(post("/api/office/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFirst));
        jsonBodySecond = "{\"name\": \"Второй офис\", " +
                "\"orgId\": \"" + orgId + "\"," +
                "\"address\": \"Россия, г. Москва, ул. К. Маркса, д. 2\"," +
                "\"phone\":\"+7 001 222-22-22\"," +
                "\"isActive\": \"false\"}";
    }

    @Test
    public void testListMultipleResultByOrgId() throws Exception {
        mockMvc.perform(post("/api/office/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond));
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\"}";
        mockMvc.perform(post("/api/office/list")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первый офис")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)))
                .andExpect(jsonPath("$.data[1].id", notNullValue()))
                .andExpect(jsonPath("$.data[1].name", is("Второй офис")))
                .andExpect(jsonPath("$.data[1].isActive", is(false)));
    }

    @Test
    public void testListSingleResultByNameLike() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\":\"офис\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первый офис")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByName() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\":\"Второй офис\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListResultByIsActive() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"isActive\":\"true\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первый офис")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListEmptyResultByIsActive() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"isActive\":\"false\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testListEmptyResultByAllParameters() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("Первый офис")))
                .andExpect(jsonPath("$.data[0].isActive", is(true)));
    }

    @Test
    public void testListErrorOrgIdIsNull() throws Exception {
        String jsonBodyFilter = "{\"name\": \"Первый офис\", \"isActive\":\"true\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testListEmptyResultNoOrganization() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + (orgId + 1) + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testIdSuccessResult() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(get("/api/office/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(id.intValue())))
                .andExpect(jsonPath("$.data.name", is("Первый офис")))
                .andExpect(jsonPath("$.data.address", is("Россия, г. Москва, ул. К. Маркса, д. 1")))
                .andExpect(jsonPath("$.data.phone", is("+7 001 111-11-11")))
                .andExpect(jsonPath("$.data.isActive", is(true)));
    }

    @Test
    public void testIdErrorNotFound() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(get("/api/office/" + (id + 1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"id\": \"" + id + "\", " + jsonBodySecond.substring(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
        mockMvc.perform(get("/api/office/" + id))
                .andExpect(jsonPath("$.data.id", is(id.intValue())))
                .andExpect(jsonPath("$.data.name", is("Второй офис")))
                .andExpect(jsonPath("$.data.address", is("Россия, г. Москва, ул. К. Маркса, д. 2")))
                .andExpect(jsonPath("$.data.phone", is("+7 001 222-22-22")))
                .andExpect(jsonPath("$.data.isActive", is(false)));
    }

    @Test
    public void testUpdateErrorNotFound() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\": " + (id + 1) + "\", " + jsonBodySecond.substring(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testUpdateErrorIdIsNull() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testSaveSuccess() throws Exception {
        mockMvc.perform(post("/api/office/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodySecond))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
        String jsonBodyFilter = "{\"name\":\"Второй офис\",\"orgId\":\"" + orgId + "\",\"isActive\":\"false\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testSaveErrorNotEnoughParameters() throws Exception {
        mockMvc.perform(post("/api/office/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"orgId\":\"" + orgId + "\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }

    @Test
    public void testRemoveSuccess() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/remove/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.result", is("success")));
        jsonBodyFilter = "{\"name\":\"Первый офис\",\"orgId\":\"" + orgId + "\",\"isActive\":\"true\"}";
        mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void testRemoveErrorNotFound() throws Exception {
        String jsonBodyFilter = "{\"orgId\": \"" + orgId + "\", \"name\": \"Первый офис\", \"isActive\":\"true\"}";
        String response = mockMvc.perform(post("/api/office/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBodyFilter))
                .andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.data[0].id", Long.class);
        mockMvc.perform(post("/api/office/remove/" + (id + 1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", notNullValue()));
    }
}
