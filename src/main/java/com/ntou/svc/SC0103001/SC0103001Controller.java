package com.ntou.svc.SC0103001;

import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0103001")
public class SC0103001Controller {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(SC0103001Req req) throws Exception {
        return new SC0103001().doAPI(req);
    }
}