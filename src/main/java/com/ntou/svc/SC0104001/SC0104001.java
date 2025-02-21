package com.ntou.svc.SC0104001;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import com.ntou.tool.DateTool;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.UUID;

/** 使用信用卡購物 */
@Log4j2
@NoArgsConstructor
public class SC0104001 {
    public Response doAPI(SC0104001Req req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0104001Res res = new SC0104001Res();

        if(!req.checkReq())
            ResTool.regularThrow(res, SC0104001RC.T141A.getCode(), SC0104001RC.T141A.getContent(), req.getErrMsg());

        CuscreditDAO daoCuscredit = new CuscreditDAO();
        CuscreditVO voCuscredit = daoCuscredit.selectCardHolderActivated(
                req.getCid(), req.getCardType(), req.getCardNum());

        //check客戶是否存在且開卡完成
        if(voCuscredit==null)
            ResTool.commonThrow(res, SC0104001RC.T141D.getCode(), SC0104001RC.T141D.getContent());

        int insertResult = new BillrecordDAO().insertCusDateBill(voBillrecordInsert(req));
        if(insertResult !=1)
            ResTool.commonThrow(res, SC0104001RC.T141C.getCode(), SC0104001RC.T141C.getContent());

        MailVO vo = new MailVO();
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("您今天有一筆信用卡消費");
        vo.setContent("<h1>您消費紀錄如下</h1>" +
                "<h2>"
                + "消費時間：" + req.getBuyDate()     +"<br>"
                + "消費店家：" + req.getShopId()      +"<br>"
                + "消費幣別：" + req.getBuyCurrency() +"<br>"
                + "消費金額：" + req.getBuyAmount()   +"<br>"
                +"</h2>"
        );
        new JavaMail().sendMail(vo);

        ResTool.setRes(res, SC0104001RC.T1410.getCode(), SC0104001RC.T1410.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.CREATED).entity(res).build();
    }

    private BillrecordVO voBillrecordInsert(SC0104001Req req){
        BillrecordVO vo = new BillrecordVO();
        vo.setUuid				(UUID.randomUUID().toString());//交易編號UUID
        vo.setBuyChannel		(req.getBuyChannel		());//消費通路(00:實體, 01:線上)
        vo.setBuyDate			(req.getBuyDate			());//消費時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setReqPaymentDate	(req.getReqPaymentDate	());//請款時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setCardType			(req.getCardType		());//卡別
        vo.setShopId			(req.getShopId			());//消費店家(統編)
        vo.setCid				(req.getCid				());//消費者(身分證)
        vo.setBuyCurrency		(req.getBuyCurrency		());//消費幣別
        vo.setBuyAmount			(req.getBuyAmount		());//消費金額
        vo.setDisputedFlag		(req.getDisputedFlag	());//爭議款項註記(00:正常,01異常)
        vo.setStatus			(req.getStatus			());//狀態(00:正常,99:註銷)
        vo.setActuallyDate		(DateTool.getDateTime());//交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setRemark			(req.getRemark			());//備註
        vo.setIssuingBank		(req.getIssuingBank		());//發卡銀行(swiftCode)
        vo.setCardNum			(req.getCardNum			());//卡號
        vo.setSecurityCode		(req.getSecurityCode	());//安全碼
        return vo;
    }
}
