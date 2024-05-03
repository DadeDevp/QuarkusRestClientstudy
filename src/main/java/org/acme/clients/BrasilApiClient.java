package org.acme.clients;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.models.Feriado;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("feriados/v1/")
@RegisterRestClient(configKey = "brasil-api")
public interface BrasilApiClient {
    @GET
    @Path("/{ano}")
    public List<Feriado> getFeriados(@PathParam("ano") String ano);

}
