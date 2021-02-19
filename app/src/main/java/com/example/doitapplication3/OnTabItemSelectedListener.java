package com.example.doitapplication3;

import org.jetbrains.annotations.NotNull;

public interface OnTabItemSelectedListener {
    public void onTabSelected(int position);

    void onDenied(int requestCode, @NotNull String[] permissions);

    void onGranted(int requestCode, @NotNull String[] permissions);

    //public void showFragment2(Note item);
    public void showFragment2(Note item);
}
