package ru.miron.policeback.util;

import ru.miron.policeback.entities.*;
import ru.miron.policeback.exceptions.IllegalEnumValueException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EnumConverters {
    private static final Map<String, CrimeCase.State> crimeCaseStateMapper;
    private static final Map<String, PersonRelevantToCase.Relation> personCaseRelationsMapper;
    private static final Map<String, PolicemanCase.Status> policemanOnCaseStatusMapper;
    private static final Map<String, Policeman.Rank> rankMapper;
    private static final Map<String, Person.Race> raceMapper;

    static {
        crimeCaseStateMapper = new HashMap<>();
        for (var state : CrimeCase.State.values()) {
            crimeCaseStateMapper.put(state.name().toLowerCase(), state);
        }
        personCaseRelationsMapper = new HashMap<>();
        for (var state : PersonRelevantToCase.Relation.values()) {
            personCaseRelationsMapper.put(state.name().toLowerCase(), state);
        }
        rankMapper = new HashMap<>();
        for (var state : Policeman.Rank.values()) {
            rankMapper.put(state.name().toLowerCase(), state);
        }
        policemanOnCaseStatusMapper = new HashMap<>();
        for (var state : PolicemanCase.Status.values()) {
            policemanOnCaseStatusMapper.put(state.name().toLowerCase(), state);
        }
        raceMapper = new HashMap<>();
        for (var state : Person.Race.values()) {
            raceMapper.put(state.name().toLowerCase(), state);
        }
        raceMapper.remove(Person.Race.SUR_LA_CLEF.name().toLowerCase());
        raceMapper.remove(Person.Race.UBI_SENT.name().toLowerCase());
        raceMapper.remove(Person.Race.YUGO_GRAAD.name().toLowerCase());
        raceMapper.put("SUR-LA-CLEF".toLowerCase(), Person.Race.SUR_LA_CLEF);
        raceMapper.put("UBI SENT".toLowerCase(), Person.Race.UBI_SENT);
        raceMapper.put("YUGO-GRAAD".toLowerCase(), Person.Race.YUGO_GRAAD);
    }

    private static <T>T enumConversion(Function<Object, T> getter, String toConvert) throws IllegalEnumValueException {
        var value = getter.apply(toConvert);
        if (value == null) {
            throw new IllegalEnumValueException();
        }
        return value;
    }

    public static CrimeCase.State convertToCrimeCaseState(String statusString) throws IllegalEnumValueException {
        return enumConversion(crimeCaseStateMapper::get, statusString);
    }
    public static PersonRelevantToCase.Relation convertToPersonCaseRelation(String relationString) throws IllegalEnumValueException {
        return enumConversion(personCaseRelationsMapper::get, relationString);
    }
    public static PolicemanCase.Status convertToPolicemanOnCaseStatus(String caseStatusAsString) throws IllegalEnumValueException {
        return enumConversion(policemanOnCaseStatusMapper::get, caseStatusAsString);
    }
    public static Policeman.Rank convertToRank(String rankAsString) throws IllegalEnumValueException {
        return enumConversion(rankMapper::get, rankAsString);
    }
    public static Person.Race convertToRace(String raceAsString) throws IllegalEnumValueException {
        return enumConversion(raceMapper::get, raceAsString);
    }

    public static String toString(CrimeCase.State state) {
        if (state == null) {
            return null;
        }
        return state.name().toLowerCase();
    }
    public static String toString(PersonRelevantToCase.Relation relation) {
        if (relation == null) {
            return null;
        }
        return relation.name().toLowerCase();
    }
    public static String toString(PolicemanCase.Status status) {
        if (status == null) {
            return null;
        }
        return status.name().toLowerCase();
    }
    public static String toString(Policeman.Rank rank) {
        if (rank == null) {
            return null;
        }
        return rank.name().toLowerCase();
    }
    public static String toString(Person.Race race) {
        if (race == null) {
            return null;
        }
        switch (race) {
            case SUR_LA_CLEF, YUGO_GRAAD -> {
                return race.name().toLowerCase().replaceAll("_", "-");
            }
            case UBI_SENT -> {
                return race.name().toLowerCase().replaceAll("_", " ");
            }
            default -> {
                return race.name().toLowerCase();
            }
        }
    }
}
