package com.easynull.luxium.api.chronicles;

import com.easynull.luxium.api.chronicles.pages.PageText;

import java.util.ArrayList;
import java.util.List;

public class Head {
    public static List<Page> pages = new ArrayList<>();
    public static List<Chapters> chapters = new ArrayList<>();
    public static Page PISA;
    public static Chapters JOPA;

    public static void init(){
        PISA = new PageText();
    }
}
