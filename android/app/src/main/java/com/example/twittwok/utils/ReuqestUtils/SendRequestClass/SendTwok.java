package com.example.twittwok.utils.ReuqestUtils.SendRequestClass;

public class SendTwok {
    private String sid, text, bgcol, fontcol;
    private Integer fontsize, fonttype, halign, valign;
    private Double lat, lon;

    public SendTwok(String text, String bgcol, String fontcol, int fontsize, int fonttype, int halign, int valign, Double lat, Double lon) {
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

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public String getBgcol() {return bgcol;}

    public void setBgcol(String bgcol) {this.bgcol = bgcol;}

    public String getFontcol() {return fontcol;}

    public void setFontcol(String fontcol) {this.fontcol = fontcol;}

    public Integer getFontsize() {return fontsize;}

    public void setFontsize(Integer fontsize) {this.fontsize = fontsize;}

    public Integer getFonttype() {return fonttype;}

    public void setFonttype(Integer fonttype) {this.fonttype = fonttype;}

    public Integer getHalign() {return halign;}

    public void setHalign(Integer halign) {this.halign = halign;}

    public Integer getValign() {return valign;}

    public void setValign(Integer valign) {this.valign = valign;}

    public Double getLat() {return lat;}

    public void setLat(Double lat) {this.lat = lat;}

    public Double getLon() {return lon;}

    public void setLon(Double lon) {this.lon = lon;}

    @Override
    public String toString() {
        return "SendTwok{" +
                "text='" + text + '\'' +
                ", bgcol='" + bgcol + '\'' +
                ", fontcol='" + fontcol + '\'' +
                ", fontsize=" + fontsize +
                ", fonttype=" + fonttype +
                ", halign=" + halign +
                ", valign=" + valign +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
