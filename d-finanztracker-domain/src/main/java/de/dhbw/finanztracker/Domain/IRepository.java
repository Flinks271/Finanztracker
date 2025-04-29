package de.dhbw.finanztracker.domain;

import java.sql.ResultSet;
import java.util.UUID;

public interface IRepository {
    ResultSet getAll();
    ResultSet getById(UUID id);
    void save(String query);
    void deleteById(UUID accountId);
}
