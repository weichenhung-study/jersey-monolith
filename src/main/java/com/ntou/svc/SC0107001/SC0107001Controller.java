package com.ntou.svc.SC0107001;

import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0107001")
public class SC0107001Controller {
    @POST
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(SC0107001Req req) throws Exception {
        return new SC0107001().doAPI(req);
    }
}