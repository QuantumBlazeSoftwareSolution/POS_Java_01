package com.qb.app.model;

import com.google.gson.Gson;

public class ConfigManager {
    private static final String CONFIG_FILE = "system configuration.enc";

    public static Config loadConfig() throws Exception {
        String decryptedJson = AESUtil.loadDecryptedFile(CONFIG_FILE);
        return new Gson().fromJson(decryptedJson, Config.class);
    }

    public static void saveConfig(Config config) throws Exception {
        String json = new Gson().toJson(config);
        AESUtil.saveEncryptedFile(CONFIG_FILE, json);
    }
}
