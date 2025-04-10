package ru.nutsalhan87.service;

import jakarta.ejb.Remote;
import ru.nutsalhan87.model.RestResponse;

@Remote
public interface VehicleService {
    RestResponse searchByNumberOfWheelsBetween(int from, int to) throws Exception;
    RestResponse fixDistance(Long id) throws Exception;
}
