package ru.itis.companieswebfluxservice.controllers;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.itis.companieswebfluxservice.entries.CompaniesData;
import ru.itis.companieswebfluxservice.service.CompaniesService;


@RestController
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    @RequestMapping(value = "/records",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            method = RequestMethod.GET)
    public Flux<CompaniesData> getAll() {
        return companiesService.getAll();
    }

}
