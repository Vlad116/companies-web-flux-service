package ru.itis.companieswebfluxservice.clients;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.itis.companieswebfluxservice.entries.CompaniesData;
import ru.itis.companieswebfluxservice.entries.MyApiCompaniesRecord;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Component
public class MyApiCompaniesFromDbWebClientImpl implements CompaniesClient {

    private ConnectionFactory connectionFactory;

    public MyApiCompaniesFromDbWebClientImpl() {
        ConnectionFactoryOptions options = builder()
                .option(DRIVER, "postgresql")
                .option(PORT, 5432)
                .option(HOST, "localhost")
                .option(USER, "postgres")
                .option(PASSWORD, "logistic")
                .option(DATABASE, "companiesdata")
                .build();
        this.connectionFactory = ConnectionFactories.get(options);
    }

    @Override
    public Flux<CompaniesData> getAll() {
        DatabaseClient client = DatabaseClient.create(connectionFactory);
        return client.execute("select * from \"company\"").as(MyApiCompaniesRecord.class).fetch().all()
                .map(row -> CompaniesData.builder()
                        .inn(row.getInn())
                        .companyName(row.getCompanyName())
//                        .companyEmail(row.getCompanyEmail())
                        .from("DB")
                        .build()
                );
    }

//    // реактивная штука для отправки запросов на другие сервисы
//    private WebClient client;
//
//    public DataGovOrganizationsApiWebClientImpl(@Value("${thevirustracker.url}") String url) {
//        client = WebClient.builder()
//                .exchangeStrategies(ExchangeStrategies.builder()
//                                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
//                                .build())
//                .baseUrl(url)
//                .build();
//    }
//
//    @Override
//    public Flux<CompaniesData> getAll() {
//        return client.get()
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .flatMap(clientResponse -> clientResponse.bodyToMono(DataGovOrganizationsApiRecordResponse.class)) // преобразуем данные со стороннего сервреа в Publisher
//                .flatMapIterable(DataGovOrganizationsApiRecordResponse::getData) // выполняем конвертацию данных с другого сервера в наши, возвращаем мы набор Publisher-ов(?) каждый из которых возвращает объект CovidStatistic
//                .map(record ->
//                        CompaniesData.builder()
//                                    .country(record.getCountryCode())
//                                    .dateTime(record.getDate())
//                                    .from("TheVirusTracker")
//                                    .recovered(Integer.parseInt(record.getRecovered()))
//                                    .build());
//    }
}
