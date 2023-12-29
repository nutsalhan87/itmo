package ru.miron.policeback.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.miron.policeback.controller.auth.service.AuthService;

@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String series) throws UsernameNotFoundException {
        var foundAuth = authService.findBySeries(series);
        if (foundAuth.isEmpty()) {
            throw new UsernameNotFoundException("Policeman with series \"%s\" not found".formatted(series));
        }
        var auth = foundAuth.get();
        var policeman = auth.getPoliceman();
        var rank = RoleName.roleNameByRank(policeman.getRank());
        return new AppUserDetails(policeman.getSeries(), auth.getHashed(), rank);
    }
}
