package ru.bellintegrator.practice.ref.docs.view;

public class DocView {

    /**
     * Название документа, удостоверяющего личность
     */
    public String name;

    /**
     * Код документа
     */
    public Integer code;

    @Override
    public String toString(){
        return "{ name: \"" + name +
                "\"; code: " + code + " }";
    }
}
