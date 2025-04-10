package ru.nutsalhan87.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "totalPages",
    "vehicles"
})
public class VehiclesReceiveDto {

    private int totalPages;
    private List<VehicleDto> vehicles;
}
