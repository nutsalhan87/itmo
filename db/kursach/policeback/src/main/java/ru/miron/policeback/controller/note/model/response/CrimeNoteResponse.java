package ru.miron.policeback.controller.note.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.note.CrimeNoteProjection;

@Data
@AllArgsConstructor
public class CrimeNoteResponse {
    private Integer id;
    private Integer policemanId;
    private String note;

    public static CrimeNoteResponse init(CrimeNoteProjection projection) {
        return new CrimeNoteResponse(
                projection.getId(),
                projection.getPolicemanOwnerId(),
                projection.getNoteText()
        );
    }
}
