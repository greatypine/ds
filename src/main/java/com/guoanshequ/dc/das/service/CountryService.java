package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.CountryMapper;
import com.guoanshequ.dc.das.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CountryService")
@Transactional("slave")
public class CountryService {

    @Autowired
    CountryMapper countryDao;

    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }

    public Country getCountry(Long id) {
        return countryDao.getCountry(id);
    }

    public void addCountry(Country country) {
        countryDao.addCountry(country);
    }

    public void updateCountry(Country country) {
        countryDao.updateCountry(country);
    }

    public void deleteCountry(Long id) {
        countryDao.deleteCountry(id);
    }
}

