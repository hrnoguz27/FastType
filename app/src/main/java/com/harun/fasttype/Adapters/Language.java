package com.harun.fasttype.Adapters;

public class Language {

    private int langId;
    private int imageId;
    private String language;

    public Language(int imageId, String language,int langId) {
        this.imageId = imageId;
        this.language = language;
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
