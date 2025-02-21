package com.ntou.svc.SC0106002;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

/** 爭議款項申請:上註記 */
@Log4j2
@NoArgsConstructor
public class SC0106002 {
    public Response doAPI(SC0106002Req req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0106002Res res = new SC0106002Res();

            if(!req.checkReq())
                ResTool.regularThrow(res, SC0106002RC.T162A.getCode(), SC0106002RC.T162A.getContent(), req.getErrMsg());

            int updateResult = new BillrecordDAO()
                    .updateDisputedFlag(voBillrecordSelect(req));
            if(updateResult !=1)
                ResTool.commonThrow(res, SC0106002RC.T162C.getCode(), SC0106002RC.T162C.getContent());

        ResTool.setRes(res, SC0106002RC.T1620.getCode(), SC0106002RC.T1620.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private BillrecordVO voBillrecordSelect(SC0106002Req req){
        BillrecordVO vo = new BillrecordVO();
        vo.setUuid		(req.getUuid());
        return vo;
    }
}
