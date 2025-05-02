package de.dhbw.finanztracker.domain;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the basic CRUD operations for a repository.
 * It is used to interact with the data layer of the application.
 * 
 * Hätte man besser machen können, wenn man es etwa dynamischer gemacht hätte und nicht für jede tabelle ein eigenes repositorymachen müsste.
 */
public interface IRepository {
    List<Map<String, Object>> getAll();
    List<Map<String, Object>> getCond(String condition);
    void save(String query);
    void deleteById(Object accountId);
}
