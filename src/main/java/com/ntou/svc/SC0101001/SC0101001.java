package com.ntou.svc.SC0101001;

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

/** 申請信用卡 */
@Log4j2
@NoArgsConstructor
public class SC0101001 {
    public Response doAPI(SC0101001Req req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        SC0101001Res res = new SC0101001Res();

        if(!req.checkReq())
            ResTool.regularThrow(res, SC0101001RC.T111A.getCode(), SC0101001RC.T111A.getContent(), req.getErrMsg());

        CuscreditDAO daoCuscredit = new CuscreditDAO();
        CuscreditVO cusDateBillList = daoCuscredit.selectKey(
                req.getCid(), req.getCardType());

        if(cusDateBillList!=null)
            ResTool.commonThrow(res, SC0101001RC.T111D.getCode(), SC0101001RC.T111D.getContent());

        int bInsertCusDateBill = daoCuscredit.insert(voCuscreditInsert(req));
        if(bInsertCusDateBill !=1)
            ResTool.commonThrow(res, SC0101001RC.T111C.getCode(), SC0101001RC.T111C.getContent());

        sendMail(req);
        ResTool.setRes(res, SC0101001RC.T1110.getCode(), SC0101001RC.T1110.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.CREATED).entity(res).build();
    }

    private CuscreditVO voCuscreditInsert(SC0101001Req req){
        CuscreditVO vo = new CuscreditVO();
        vo.setChName               (req.getChName               ());
        vo.setEnName               (req.getEnName               ());
        vo.setCid  			       (req.getCid  			    ());
        vo.setCidReissueDate       (req.getCidReissueDate       ());
        vo.setCidReissueLocation   (req.getCidReissueLocation   ());
        vo.setCidReissueStatus     (req.getCidReissueStatus     ());
        vo.setBirthDate            (req.getBirthDate            ());
        vo.setMaritalStatus        (req.getMaritalStatus        ());
        vo.setEducation            (req.getEducation            ());
        vo.setCurrentResidentialAdd(req.getCurrentResidentialAdd());
        vo.setResidentialAdd       (req.getResidentialAdd       ());
        vo.setCellphone            (req.getCellphone            ());
        vo.setEmail                (req.getEmail                ());
        vo.setCompanyName          (req.getCompanyName          ());
        vo.setCompanyIndustry      (req.getCompanyIndustry      ());
        vo.setOccupation           (req.getOccupation           ());
        vo.setDepartment           (req.getDepartment           ());
        vo.setJobTitle             (req.getJobTitle             ());
        vo.setDateOfEmployment     (req.getDateOfEmployment     ());
        vo.setCompanyAddress       (req.getCompanyAddress       ());
        vo.setCompanyPhoneNum      (req.getCompanyPhoneNum      ());
        vo.setAnnualIncome         (req.getAnnualIncome         ());
        vo.setCardApprovalStatus   (CuscreditVO.CardApprovalStatus.PROCESS.getValue());
        vo.setActivationRecord     (CuscreditVO.ActivationRecord.NOTOPEN.getValue());
        vo.setEventCode            (req.getEventCode            ());
        vo.setRegidate 		       (DateTool.getDateTime());
        vo.setIssuing_bank 	       ("803");
        vo.setCardType             (req.getCardType             ());
        vo.setRemark			   (req.getRemark			    ());
        return vo;
    }
    private void sendMail(SC0101001Req req){
        MailVO vo = new MailVO();
        vo.setEmailAddr(req.getEmail());
        vo.setSubject("收到您的信用卡申請");
        vo.setContent("<h1>感謝您申請</h1><h2>因為申請數量踴躍，預計15個工作天審核<br>感謝您的耐心等候</h2>");
        new JavaMail().sendMail(vo);
    }
}
