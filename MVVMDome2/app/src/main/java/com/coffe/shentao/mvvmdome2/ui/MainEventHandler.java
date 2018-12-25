package com.coffe.shentao.mvvmdome2.ui;

public class MainEventHandler {
    private MainActivity mainActivity;
    MainEventHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    /*
     * 这个方法由xml中的app:onInputFinish="@{(text)->eventHandler.onTextSubmit(text)}"调用。
     */
    public void onTextSubmit(String text) {
        mainActivity.onSearchUser(text);
    }

}
