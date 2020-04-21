package ru.itis.companieswebfluxservice.entries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyApiCompaniesRecord {
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("companyname")
    private String companyName;
//    private String companyEmail;
//    private String phoneNumber;
}
