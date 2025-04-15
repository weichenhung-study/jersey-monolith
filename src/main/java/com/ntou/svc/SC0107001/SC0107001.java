package com.ntou.svc.SC0107001;

import com.ntou.db.billofmonth.BillofmonthDAO;
import com.ntou.db.billofmonth.BillofmonthVO;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.ExecutionTimer;
import com.ntou.tool.ResTool;
import com.ntou.tool.DateTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

/** 客戶繳交信用卡費 */
@Log4j2
@NoArgsConstructor
public class SC0107001 {
    public Response doAPI(SC0107001Req req) throws Exception {
        ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());

		log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0107001Res res = new SC0107001Res();

        if(!req.checkReq())
            ResTool.regularThrow(res, SC0107001RC.T171A.getCode(), SC0107001RC.T171A.getContent(), req.getErrMsg());

		ExecutionTimer.startStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());
        BillofmonthDAO daoBillofmonth = new BillofmonthDAO();
        BillofmonthVO vo = setUpdatePayDate(req);
        ArrayList<BillofmonthVO> listBillofmonth = daoBillofmonth.findPaidBills(vo);
        if (listBillofmonth.size() == 1) {
            int notPaidAmount = Integer.parseInt(listBillofmonth.get(0).getAmt()) - Integer.parseInt(req.getPayAmt());
            vo.setNotPaidAmount(String.valueOf(notPaidAmount));

            int updateCount = daoBillofmonth.updatePayDate(vo);
            if(updateCount !=1)
                ResTool.commonThrow(res, SC0107001RC.T171C.getCode(), SC0107001RC.T171C.getContent());
            sendMail(req, listBillofmonth.get(0));
        } else
            ResTool.commonThrow(res, SC0107001RC.T171D.getCode(), SC0107001RC.T171D.getContent());
        ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.DATABASE.getValue());

        ResTool.setRes(res, SC0107001RC.T1710.getCode(), SC0107001RC.T1710.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        
		ExecutionTimer.endStage(ExecutionTimer.ExecutionModule.APPLICATION.getValue());
        ExecutionTimer.exportTimings(this.getClass().getSimpleName() + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
		return Response.status(Response.Status.CREATED).entity(res).build();
    }
    private void sendMail(SC0107001Req req,BillofmonthVO key) throws Exception {
        MailVO vo = new MailVO();
        CuscreditVO voCuscredit = new CuscreditDAO().selectKey(
                req.getCid(), req.getCardType());
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("信用卡繳費通知");
        vo.setContent("<h1>收到您的信用卡費</h1>" +
                "帳單月份：" + key.getBillMonth() +"<br>" +
                "當月帳單應繳金額：" + key.getAmt() +"<br>" +
                "當月繳款金額：" + key.getPaidAmount() +"<br>" +
                "剩餘未繳金額：" + key.getNotPaidAmount() +"<br>" +
                "繳款時間：" + key.getPayDate() +"<br>"
        );
        new JavaMail().sendMail(vo);
    }
    private BillofmonthVO setUpdatePayDate(SC0107001Req req){
        BillofmonthVO vo = new BillofmonthVO();
        vo.setCid(req.getCid());
        vo.setCardType(req.getCardType());
        vo.setPayDate(DateTool.getDateTime());
        vo.setPaidAmount(req.getPayAmt());
        vo.setBillMonth(req.getPayDate());
        return vo;
    }
}
