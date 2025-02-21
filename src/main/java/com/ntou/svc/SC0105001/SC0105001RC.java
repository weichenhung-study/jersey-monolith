package com.ntou.svc.SC0105001;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SC0105001RC {
      T1510("T1510" , "成功")
    , T151A("T151A" , "驗證有誤")
    , T151B("T151B" , "失敗")
    , T151C("T151C" , "帳單產出失敗，已有出帳帳單，請洽客服更正帳單內容")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
