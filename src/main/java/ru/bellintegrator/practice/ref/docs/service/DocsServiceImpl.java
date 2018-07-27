package ru.bellintegrator.practice.ref.docs.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.ref.docs.view.DocView;

import java.util.List;

@Service
public class DocsServiceImpl implements DocsService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DocView> list() {
        return null;
    }
}
