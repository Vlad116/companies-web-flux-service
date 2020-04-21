package ru.itis.companieswebfluxservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.itis.companieswebfluxservice.entries.CompaniesData;
import ru.itis.companieswebfluxservice.entries.DataGovOrganizationsApiRecord;
import ru.itis.companieswebfluxservice.entries.MyApiCompaniesRecord;

import java.util.Arrays;

@Component
public class MyCompaniesDataApiWebClientImpl implements CompaniesClient {

    // реактивная штука для отправки запросов на другие сервисы
    private WebClient client;

    public MyCompaniesDataApiWebClientImpl(@Value("${myapidata.url}") String url) {
        client = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
                        .build())
                .baseUrl(url)
                .build();
    }

    @Override

    public Flux<CompaniesData> getAll() {
        return client.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(MyApiCompaniesRecord[].class)) // преобразуем данные со стороннего сервреа в Publisher
                .flatMapIterable(Arrays::asList) // выполняем конвертацию данных с другого сервера в наши, возвращаем мы набор Publisher-ов(?) каждый из которых возвращает объект CovidStatistic
                .map(record ->
                        CompaniesData.builder()
                                .inn(record.getInn())
                                .companyName(record.getCompanyName())
//                                .companyEmail("...")
//                                .phoneNumber("...")
                                .from("http://localhost:8080/companies")
                                .build());
    }
}
