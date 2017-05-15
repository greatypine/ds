package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DataSource("slave")
public interface CountryMapper {

    List<Country> getAllCountries();

    Country getCountry(Long id);

    Long addCountry(Country country);

    void updateCountry(Country country);

    void deleteCountry(Long id);
}
