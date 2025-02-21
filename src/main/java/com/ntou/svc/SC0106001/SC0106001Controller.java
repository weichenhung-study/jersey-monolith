package com.ntou.svc.SC0106001;

import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("SC0106001")
public class SC0106001Controller {
    @GET
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(
            @QueryParam("cid") String cid,
            @QueryParam("cardType") String cardType,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) throws Exception {
        SC0106001Req req = new SC0106001Req();
        req.setCid(cid);
        req.setCardType(cardType);
        req.setStartDate(startDate);
        req.setEndDate(endDate);
        return new SC0106001().doAPI(req);
    }
}