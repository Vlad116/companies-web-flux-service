package ru.itis.companieswebfluxservice.clients;

import reactor.core.publisher.Flux;
import ru.itis.companieswebfluxservice.entries.CompaniesData;

public interface CompaniesClient {
    Flux<CompaniesData> getAll();
}
