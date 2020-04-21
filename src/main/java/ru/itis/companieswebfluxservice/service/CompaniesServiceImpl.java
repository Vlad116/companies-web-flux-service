package ru.itis.companieswebfluxservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.itis.companieswebfluxservice.clients.CompaniesClient;
import ru.itis.companieswebfluxservice.entries.CompaniesData;
//import ru.itis.covid.clients.CovidClient;
//import ru.itis.covid.entries.CovidStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompaniesServiceImpl implements CompaniesService {

    @Autowired
    private List<CompaniesClient> clients;

    @Override
    public Flux<CompaniesData> getAll() {
        List<Flux<CompaniesData>> fluxes =  clients.stream().map(this::getAll).collect(Collectors.toList());
        return Flux.merge(fluxes);
    }

    private Flux<CompaniesData> getAll(CompaniesClient client) {
        return client.getAll().subscribeOn(Schedulers.elastic());
    }
}
