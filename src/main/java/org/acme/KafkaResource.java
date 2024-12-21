package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.KafkaProducer;

@Path("/kafka")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class KafkaResource {

    @Inject
    KafkaProducer producer;

    @POST
    @Path("/produce")
    public Response sendMessage(String message) {
        producer.sendMessage(message);
        return Response.ok("Message sent: " + message).build();
    }
}
