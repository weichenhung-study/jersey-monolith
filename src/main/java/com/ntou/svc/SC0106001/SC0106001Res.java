package com.ntou.svc.SC0106001;

import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.spec.SvcRes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SC0106001Res extends SvcRes {
    private ArrayList<BillrecordVO> result;
}