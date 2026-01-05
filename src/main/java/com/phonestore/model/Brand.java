package com.phonestore.model;

public class Brand {
    private int id;
    private String name;
    private String slug;
    private String logoUrl;

    public Brand() {
    }

    public Brand(int id) {
        this.id = id;
    }

    public Brand(int id, String name, String slug, String logoUrl) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.logoUrl = logoUrl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}