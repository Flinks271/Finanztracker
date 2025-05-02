package de.dhbw.finanztracker.domain;

import java.util.List;
import java.util.Map;

public interface IRepository {
    List<Map<String, Object>> getAll();
    List<Map<String, Object>> getCond(String condition);
    void save(String query);
    void deleteById(Object accountId);
}
