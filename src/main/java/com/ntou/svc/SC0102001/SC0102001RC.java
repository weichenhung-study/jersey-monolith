package com.ntou.svc.SC0102001;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SC0102001RC {
      T1210("T1210" , "成功")
    , T121A("T121A" , "驗證有誤")
    , T121B("T121B" , "失敗")
    , T121C("T121C" , "新增失敗")
    , T121D("T121D" , "查詢失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
