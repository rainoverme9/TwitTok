package com.example.twittwok.utils.ReuqestUtils.GetRequestClass;

public class Twok {
    private String name, text, uid, bgcol, fontcol;
    private int pversion, tid, fontsize, fonttype, halign, valign;
    private Double lat, lon;

    private String picture;

    public Twok(String  name, String text, String uid,String bgcol, String fontcol, Integer pversion, int tid, int fontsize, int fonttype, int halign, int valign, Double lat, Double lon) {
        this.name = name;
        this.pversion = pversion;
        this.uid = uid;
        this.tid = tid;

        this.text = text;
        this.bgcol = bgcol;
        this.fontcol = fontcol;
        this.fontsize = fontsize;
        this.fonttype = fonttype;
        this.halign = halign;
        this.valign = valign;
        this.lat = lat;
        this.lon = lon;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {return name;}

    public String getText() {return text;}

    public String getBgcol() {return bgcol;}

    public String getFontcol() {return fontcol;}

    public int getPversion() {return pversion;}

    public String getUid() {return uid;}

    public int getTid() {return tid;}

    public int getFontsize() {return fontsize;}

    public int getFonttype() {return fonttype;}

    public int getHalign() {return halign;}

    public int getValign() {return valign;}

    public Double getLat() {return lat;}

    public Double getLon() {return lon;}

    @Override
    public String toString() {
        return "Twok{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", bgcol='" + bgcol + '\'' +
                ", fontcol='" + fontcol + '\'' +
                ", pversion=" + pversion +
                ", uid=" + uid +
                ", tid=" + tid +
                ", fontsize=" + fontsize +
                ", fonttype=" + fonttype +
                ", halign=" + halign +
                ", valign=" + valign +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}

