package ru.miron.policeback.security;

import ru.miron.policeback.entities.Policeman;
import ru.miron.policeback.exceptions.IllegalRoleNameException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RoleName {
    MAJOR,
    MINOR;

    private static final Map<String, RoleName> roleMapper;

    static {
        var roles = RoleName.values();
        roleMapper = Arrays.stream(roles)
                .collect(Collectors.toMap(RoleName::name, r -> r));
    }

    /**
     *
     * @param value upper case value which should be equal to {@link RoleName#name()} call
     * @return corresponding {@link RoleName} object
     * @throws IllegalRoleNameException if {@code value} is illegal
     */
    public static RoleName roleNameByString(String value) throws IllegalRoleNameException {
        var foundRole = roleMapper.get(value);
        if (foundRole == null) {
            throw new IllegalRoleNameException(
                    "Input role value is illegal - no role with specified (\"%s\") name"
                            .formatted(value));
        }
        return foundRole;
    }

    public static RoleName roleNameByRank(Policeman.Rank rank) {
        return switch (rank) {
            case MAJOR -> RoleName.MAJOR;
            case MINOR -> RoleName.MINOR;
        };
    }

    public String nameWithRolePrefix() {
        return "ROLE_" + name();
    }

}
