package com.ntou.svc.SC0101001;


import com.ntou.tool.Common;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0101001")
public class SC0101001Controller {
    @POST
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(SC0101001Req req) throws Exception {
        return new SC0101001().doAPI(req);
    }
}