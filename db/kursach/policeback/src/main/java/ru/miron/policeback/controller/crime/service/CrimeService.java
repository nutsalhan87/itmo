package ru.miron.policeback.controller.crime.service;

import ru.miron.policeback.controller.crime.model.response.BaseCrimeResponse;

import java.util.List;

public interface CrimeService {
    List<BaseCrimeResponse> getBaseByPrecinctDistricts(String series);
}
