package com.ntou.svc.SC0106001;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

/** 爭議款項申請:消費紀錄區間查詢 */
@Log4j2
@NoArgsConstructor
public class SC0106001 {
    public Response doAPI(SC0106001Req req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0106001Res res = new SC0106001Res();

        if(!req.checkReq())
            ResTool.regularThrow(res, SC0106001RC.T161A.getCode(), SC0106001RC.T161A.getContent(), req.getErrMsg());

        ArrayList<BillrecordVO> billList = new BillrecordDAO()
                .selectCusBillAll(voBillrecordSelect(req), req.getStartDate(), req.getEndDate());

        ResTool.setRes(res, SC0106001RC.T1610.getCode(), SC0106001RC.T1610.getContent());
        res.setResult(billList);

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private BillrecordVO voBillrecordSelect(SC0106001Req req){
        BillrecordVO vo = new BillrecordVO();
        vo.setCid		(req.getCid());
        vo.setCardType	(req.getCardType());
        return vo;
    }
}
