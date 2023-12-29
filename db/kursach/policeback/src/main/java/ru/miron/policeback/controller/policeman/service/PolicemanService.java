package ru.miron.policeback.controller.policeman.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import ru.miron.policeback.controller.policeman.model.response.BasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.CasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.NoContextNoRankPolicemanResponse;

import java.util.List;

public interface PolicemanService {
    BasePolicemanResponse getSelfBaseInfo(String series);

    List<NoContextNoRankPolicemanResponse> getSlaves(String series);

    CasePartnersResponse getCasePartners(Integer crimeId, String series);

    List<Integer> getDistrictIds(String series);

    @AllArgsConstructor
    @Getter
    class CasePartnersResponse {
        private Cases responseCase;
        private List<CasePolicemanResponse> okResponse;

        public static CasePartnersResponse initOk(@NonNull List<CasePolicemanResponse> okResponse) {
            return new CasePartnersResponse(Cases.OK, okResponse);
        }

        public static CasePartnersResponse initNotOk(Cases notOkResponseCase) {
            if (notOkResponseCase == Cases.OK) {
                throw new IllegalArgumentException();
            }
            return new CasePartnersResponse(notOkResponseCase, null);
        }

        public enum Cases {
            OK,
            NOT_ASSIGNED,
            REMOVED,
            NO_CRIME
        }
    }
}
