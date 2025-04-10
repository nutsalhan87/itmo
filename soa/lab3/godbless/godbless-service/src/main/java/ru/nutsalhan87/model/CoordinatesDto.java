package ru.nutsalhan87.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "x",
    "y"
})
public class CoordinatesDto {
    private double x;
    private Double y;
}
