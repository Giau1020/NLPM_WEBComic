package com.example.demo.DTO;

public class UpdatePassDTO {
    private String Username;
    private String oldPass;
    private String newPass;

    public String getUsername() {
        return Username;
    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
