package com.example.twittwok.ui.newTwok;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twittwok.databinding.FragmentNewtwokBinding;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.example.twittwok.utils.ReuqestUtils.SendRequestClass.SendTwok;

public class NewTwokViewModel extends ViewModel {
    private MutableLiveData<SendTwok> twok;
    private MutableLiveData<Boolean> permesso;

    public MutableLiveData<SendTwok> getAttributesTwok() {
        if (twok == null) {
            twok = new MutableLiveData<>(new SendTwok("", "FFFFFF", "000000", 0, 0, 0, 0, null, null));
        }
        return twok;
    }

    public MutableLiveData<Boolean> getRequestPermission() {
        if (permesso == null) {
            permesso = new MutableLiveData<Boolean>();
            permesso.setValue(false);
        }
        return permesso;
    }
    public MutableLiveData<Boolean> getPermesso() {
        return permesso;
    }

    public void setPermesso(Boolean permissionLocation) {
        permesso.setValue(permissionLocation);
    }

    public String getText() {
        return twok.getValue().getText();
    }

    public void setText(String text) {
        twok.getValue().setText(text);
        twok.setValue(twok.getValue());
    }

    public String getBgcol() {
        return twok.getValue().getBgcol();
    }

    public void setBgcol(String bgcol) {
        twok.getValue().setBgcol(bgcol);
        twok.setValue(twok.getValue());
    }

    public String getFontcol() {
        return twok.getValue().getFontcol();
    }

    public void setFontcol(String fontcol) {
        twok.getValue().setFontcol(fontcol);
        twok.setValue(twok.getValue());
    }

    public int getFontsize() {
        return twok.getValue().getFontsize();
    }

    public void setFontsize(int fontsize) {
        twok.getValue().setFontsize(fontsize);
        twok.setValue(twok.getValue());
    }

    public int getFonttype() {
        return twok.getValue().getFonttype();
    }

    public void setFonttype(int fonttype) {
        twok.getValue().setFonttype(fonttype);
        twok.setValue(twok.getValue());
    }

    public int getHalign() {
        return twok.getValue().getHalign();
    }

    public void setHalign(int halign) {
        twok.getValue().setHalign(halign);
        twok.setValue(twok.getValue());
    }

    public int getValign() {
        return twok.getValue().getValign();
    }

    public void setValign(int valign) {
        twok.getValue().setValign(valign);
        twok.setValue(twok.getValue());
    }

    public Double getLat() {
        return twok.getValue().getLat();
    }

    public void setLat(Double lat) {
        twok.getValue().setLat(lat);
        twok.setValue(twok.getValue());
    }

    public Double getLon() {
        return twok.getValue().getLon();
    }

    public void setLon(Double lon) {
        twok.getValue().setLon(lon);
        twok.setValue(twok.getValue());
    }

    @Override
    public String toString() {
        return "NewTwokViewModel{" +
                "twok=" + twok +
                '}';
    }
}
