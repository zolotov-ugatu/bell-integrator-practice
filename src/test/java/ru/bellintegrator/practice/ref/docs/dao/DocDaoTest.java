package ru.bellintegrator.practice.ref.docs.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.ref.docs.model.Doc;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
@Sql(statements = {
        "DELETE FROM User;",
        "DELETE FROM Doc;",
        "INSERT INTO Doc (id, code, name) VALUES " +
                "(1, 21, 'Паспорт гражданина Российской Федерации')," +
                "(2, 12, 'Вид на жительство в Российской Федерации');",
})
public class DocDaoTest {

    @Autowired
    private DocsDao dao;

    @Test
    public void testList(){
        List<Doc> list = dao.list();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(21, list.get(0).getCode().intValue());
        Assert.assertEquals("Паспорт гражданина Российской Федерации", list.get(0).getName());
        Assert.assertEquals(12, list.get(1).getCode().intValue());
        Assert.assertEquals("Вид на жительство в Российской Федерации", list.get(1).getName());
    }

    @Test
    public void testGetByCode(){
        Doc doc = dao.getByCode(21);
        Assert.assertNotNull(doc);
        Assert.assertEquals(21, doc.getCode().intValue());
        Assert.assertEquals("Паспорт гражданина Российской Федерации", doc.getName());
    }

    @Test
    public void testGetByCodeNotFound(){
        Doc doc = dao.getByCode(0);
        Assert.assertNull(doc);
    }
}
