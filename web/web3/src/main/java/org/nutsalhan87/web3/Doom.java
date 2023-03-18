package org.nutsalhan87.web3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;

@Named
@ApplicationScoped
public class Doom {
    public Doom() {}
    public void redirect() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/views/doom/doom.xhtml");
    }
}
