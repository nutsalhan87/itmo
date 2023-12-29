package ru.miron.policeback.controller.precinct.service;

import ru.miron.policeback.controller.precinct.model.response.BasePrecinctResponse;

public interface PrecinctService {
    BasePrecinctResponse getMyPrecinctWithDistricts(String series);
}
