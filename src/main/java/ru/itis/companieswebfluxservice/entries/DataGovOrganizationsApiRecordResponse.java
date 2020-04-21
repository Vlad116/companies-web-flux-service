package ru.itis.companieswebfluxservice.entries;

import lombok.Data;

import java.util.List;

@Data
public class DataGovOrganizationsApiRecordResponse {
    private List<DataGovOrganizationsApiRecord> data;
}
