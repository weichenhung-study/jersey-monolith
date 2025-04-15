package com.ntou.svc.SC0103001;

import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.DateTool;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.ResTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

/** 信用卡開卡 */
@Log4j2
@NoArgsConstructor
public class SC0103001 {
    public Response doAPI(SC0103001Req req) throws Exception {
        ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());

		log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0103001Res res = new SC0103001Res();

        if(!req.checkReq())
            ResTool.regularThrow(res, SC0103001RC.T131A.getCode(), SC0103001RC.T131A.getContent(), req.getErrMsg());
        
		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());
        CuscreditDAO daoCuscredit = new CuscreditDAO();
        CuscreditVO voCuscredit = daoCuscredit.selectKey(
                req.getCid(), req.getCardType());

        int updateResult = daoCuscredit.updateActivationRecord(voCuscreditUpdate(req));
        MailVO vo = new MailVO();
        if(updateResult !=1) {
            ResTool.setRes(res, SC0103001RC.T131C.getCode(), SC0103001RC.T131C.getContent());

            vo.setEmailAddr(voCuscredit.getEmail());
            vo.setSubject("信用卡開卡開卡失敗");
            vo.setContent("<h1>請聯繫客服</h1><h2>02-1234567</h2>");
            throw new TException(res);
        }
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());

        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("信用卡開卡完成");
        vo.setContent("<h1>您申請的信用卡已開卡完成</h1><h2>歡迎使用!</h2>");
        new JavaMail().sendMail(vo);

        ResTool.setRes(res, SC0103001RC.T1310.getCode(), SC0103001RC.T1310.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());
        ExecutionTimer.exportTimings(this.getClass().getSimpleName() + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
		return Response.status(Response.Status.OK).entity(res).build();
    }

    private CuscreditVO voCuscreditUpdate(SC0103001Req req){
        CuscreditVO vo = new CuscreditVO();
        vo.setCid  		(req.getCid  	 ());
        vo.setCardType  (req.getCardType ());
        vo.setActivationRecord(CuscreditVO.ActivationRecord.OPEN.getValue());
        return vo;
    }
}
