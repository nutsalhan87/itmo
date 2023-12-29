package ru.miron.policeback.projections.note;

public interface CrimeNoteProjection {
    Integer getId();
    Integer getPolicemanOwnerId();
    String getNoteText();
}
