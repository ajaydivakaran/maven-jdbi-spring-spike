package me.spike.repository;

import me.spike.model.Cat;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatRepository {
    private Jdbi jdbi;

    public CatRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Cat> fetchAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cat ORDER BY name")
                .mapToBean(Cat.class)
                .list());
    }

}
