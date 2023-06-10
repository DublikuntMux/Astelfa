package com.dublikunt.astelfa;

import com.dublikunt.astelfa.helper.common.LogType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

public class AstelfaConfig {
    public LogType logType = LogType.INFO;
    public int loseChange = 30;
    public int hungryChange = 5;
    public int switchChange = 20;
    public int illusionChange = 1_000;
    public int climbingChange = 100;

    private transient File file;

    public static AstelfaConfig load() {
        File file = new File(
                FabricLoader.getInstance().getConfigDir().toString(),
                Astelfa.MOD_ID + ".json"
        );

        AstelfaConfig config = new AstelfaConfig();

        if (file.exists()) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Reader reader = Files.newBufferedReader(file.toPath());
                config = gson.fromJson(reader, AstelfaConfig.class);
                config.file = file;
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            config.file = file;
            config.save();
        }
        return config;
    }

    public void save() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this, AstelfaConfig.class);
            FileWriter newFile = new FileWriter(file.getAbsolutePath());
            newFile.write(json);
            newFile.flush();
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
