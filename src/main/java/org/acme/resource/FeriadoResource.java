package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.clients.BrasilApiClient;
import org.acme.models.Feriado;
import org.acme.service.FeriadoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.List;

@Path("/feriados")
public class FeriadoResource {
    @Inject
    FeriadoService feriadoService;

    @GET
    @Path("/{ano}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feriado> getTodosFeriados(@PathParam("ano") String ano) {

        return feriadoService.getTodosFeriados(ano);
    }

    @POST
    @Path("/{ano}")
    public String verificarDataFeriado(@PathParam("ano") String ano, String data) {
        return feriadoService.checkFeriado(ano, data);
    }

}
