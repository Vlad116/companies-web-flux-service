package ru.itis.companieswebfluxservice.service;

import reactor.core.publisher.Flux;
import ru.itis.companieswebfluxservice.entries.CompaniesData;

public interface CompaniesService {
    Flux<CompaniesData> getAll();
}
