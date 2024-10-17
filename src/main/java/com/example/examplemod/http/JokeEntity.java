package com.example.examplemod.http;


public class JokeEntity {
    private String type;
    private String setup;
    private String punchline;
    private int id;

    // コンストラクタ
    public JokeEntity(String type, String setup, String punchline, int id) {
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
        this.id = id;
    }

    // ゲッターとセッター
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // toString() メソッド（オプション）
    @Override
    public String toString() {
        return "JsonEntity{" +
                "type='" + type + '\'' +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                ", id=" + id +
                '}';
    }
}
