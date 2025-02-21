package com.ntou.svc.SC0105001;

import javax.ws.rs.core.Response;

public class SC0105001Controller {
    public Response doController() throws Exception {
        return new SC0105001().doAPI();
    }
}