package com.elfec.widgets.recyclerviewchoicemode;

public interface SelectableHolder {
    void setSelectable(boolean selectable);
    boolean isSelectable();
    void setActivated(boolean activated);
    boolean isActivated();
    int getPosition();
    long getItemId();
}
