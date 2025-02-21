package com.ntou.svc.SC0106002;

import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0106002")
public class SC0106002Controller {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(SC0106002Req req) throws Exception {
        return new SC0106002().doAPI(req);
    }
}