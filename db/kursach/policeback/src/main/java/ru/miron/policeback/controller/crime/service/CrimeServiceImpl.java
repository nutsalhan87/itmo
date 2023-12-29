package ru.miron.policeback.controller.crime.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.crime.model.response.BaseCrimeResponse;
import ru.miron.policeback.repositories.CrimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CrimeServiceImpl implements CrimeService {

    private final CrimeRepository crimeRepository;

    @Override
    public List<BaseCrimeResponse> getBaseByPrecinctDistricts(String series) {
        return crimeRepository.getBaseByPrecinctDistricts(series).stream()
                .map(BaseCrimeResponse::init)
                .collect(Collectors.toList());
    }
}
