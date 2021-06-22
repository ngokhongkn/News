package com.coccoc.news.model;

import com.coccoc.news.base.views.recycler.RecyclerViewType;

import java.util.Objects;

public class Style {
    private String name;
    private @RecyclerViewType int viewType;
    private int drawableID;

    public Style(String name, @RecyclerViewType int viewType, int drawableID) {
        this.name = name;
        this.viewType = viewType;
        this.drawableID = drawableID;
    }

    public String getName() {
        return name;
    }

    public int getViewType() {
        return viewType;
    }

    public int getDrawableId() {
        return drawableID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return viewType == style.viewType &&
                Objects.equals(name, style.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, viewType);
    }
}
