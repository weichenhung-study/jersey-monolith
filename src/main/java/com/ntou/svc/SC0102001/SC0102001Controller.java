package com.ntou.svc.SC0102001;

import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0102001")
public class SC0102001Controller {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(SC0102001Req req) throws Exception {
        return new SC0102001().doAPI(req);
    }
}