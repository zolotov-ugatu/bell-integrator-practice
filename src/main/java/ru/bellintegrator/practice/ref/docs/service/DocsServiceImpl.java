package ru.bellintegrator.practice.ref.docs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.ref.docs.dao.DocsDao;
import ru.bellintegrator.practice.ref.docs.model.Doc;
import ru.bellintegrator.practice.ref.docs.view.DocView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocsServiceImpl implements DocsService {

    private final DocsDao dao;

    /**
     * Конструктор
     *
     * @param dao DAO документов
     */
    @Autowired
    public DocsServiceImpl(DocsDao dao){
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DocView> list() {
        List<Doc> list = dao.list();
        return list.stream().map(mapDoc()).collect(Collectors.toList());
    }

    private Function<Doc, DocView> mapDoc(){
        return d -> {
            DocView docView = new DocView();
            docView.code = d.getCode();
            docView.name = d.getName();
            return docView;
        };
    }
}
