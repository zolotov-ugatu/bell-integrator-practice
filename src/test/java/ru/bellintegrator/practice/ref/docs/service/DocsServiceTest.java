package ru.bellintegrator.practice.ref.docs.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.ref.docs.dao.DocsDao;
import ru.bellintegrator.practice.ref.docs.model.Doc;
import ru.bellintegrator.practice.ref.docs.view.DocView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocsServiceTest {

    @InjectMocks
    private DocsServiceImpl service;

    @Mock
    private DocsDao dao;

    @Test
    public void testList(){
        Doc doc = new Doc();
        doc.setId(1);
        doc.setCode(21);
        doc.setName("Паспорт гражданина Российской Федерации");
        List<Doc> daoAnswer = new ArrayList<>();
        daoAnswer.add(doc);
        when(dao.list()).thenReturn(daoAnswer);
        List<DocView> list = service.list();
        verify(dao, times(1)).list();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(doc.getCode(), list.get(0).code);
        Assert.assertEquals(doc.getName(), list.get(0).name);
    }

}
