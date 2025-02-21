package com.ntou.svc.SC0106002;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SC0106002RC {
      T1620("T1620" , "成功")
    , T162A("T162A" , "驗證有誤")
    , T162B("T162B" , "失敗")
    , T162C("T162C" , "更新註記失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
