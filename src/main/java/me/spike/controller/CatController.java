package me.spike.controller;

import me.spike.model.Cat;
import me.spike.repository.CatRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatController {
    private CatRepository repository;

    public CatController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/v1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cat> getCats() {
        return repository.fetchAll();
    }

}
