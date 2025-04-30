package de.dhbw.finanztracker.domain;

import java.sql.ResultSet;
import java.util.UUID;

public interface IRepository {
    ResultSet getAll();
    ResultSet getWhere(String condition);
    void save(String query);
    void deleteById(UUID accountId);
}
