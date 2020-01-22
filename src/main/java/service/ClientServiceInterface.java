package service;

import entity.users.Client;

import java.util.List;

public interface ClientServiceInterface<T extends Client> {

    T create(T t);

    List<T> readAll();

    T readById(String id);

    T update(String id, T tFromClient);

    void delete(String id);
}
