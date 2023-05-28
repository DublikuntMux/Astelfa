package com.dublikunt.astelfa;

import com.dublikunt.astelfa.helper.LogType;
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
    private transient File file;

    private AstelfaConfig() {
    }

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
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
