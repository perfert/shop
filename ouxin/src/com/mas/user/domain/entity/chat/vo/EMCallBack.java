package com.mas.user.domain.entity.chat.vo;

public interface EMCallBack {
    void onSuccess();

    void onError(int var1, String var2);

    void onProgress(int var1, String var2);
}
