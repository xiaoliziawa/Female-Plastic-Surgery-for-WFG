package com.rinko1231.femaleplasticsurgery.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SurgeryConfig {
    private static final String CONFIG_FILE = "femaleplasticsurgery.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static SurgeryConfig INSTANCE;

    private double bustSizeMax = 1.0;
    private double bustOffsetYMin = -1.5;
    private double bustOffsetZMin = -1.5;
    private double bustOffsetZMax = 0.0;
    private double bounceMultiplierMax = 1.0;
    private double floppyMultiplierMax = 1.5;

    private static final double BUST_SIZE_MIN = 0.8;
    private static final double BUST_SIZE_MAX = 4.0;
    private static final double BUST_OFFSET_Y_MIN_LIMIT = -4.0;
    private static final double BUST_OFFSET_Y_MAX_LIMIT = -1.0;
    private static final double BUST_OFFSET_Z_MIN_LIMIT = -4.0;
    private static final double BUST_OFFSET_Z_MAX_LIMIT = 4.0;
    private static final double BOUNCE_MULTIPLIER_MIN = 0.5;
    private static final double BOUNCE_MULTIPLIER_MAX = 3.0;
    private static final double FLOPPY_MULTIPLIER_MIN = 1.0;
    private static final double FLOPPY_MULTIPLIER_MAX = 3.0;

    public static SurgeryConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = loadConfig();
        }
        return INSTANCE;
    }

    private static SurgeryConfig loadConfig() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configPath = configDir.resolve(CONFIG_FILE);
        
        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                return GSON.fromJson(reader, SurgeryConfig.class);
            } catch (IOException e) {
                System.err.println("Error loading config: " + e.getMessage());
            }
        }
        
        SurgeryConfig config = new SurgeryConfig();
        config.saveConfig();
        return config;
    }

    public void saveConfig() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configPath = configDir.resolve(CONFIG_FILE);
        
        try {
            Files.createDirectories(configDir);
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

    public double getBustSizeMax() {
        return bustSizeMax;
    }

    public void setBustSizeMax(double value) {
        this.bustSizeMax = clamp(value, BUST_SIZE_MIN, BUST_SIZE_MAX);
        saveConfig();
    }

    public double getBustOffsetYMin() {
        return bustOffsetYMin;
    }

    public void setBustOffsetYMin(double value) {
        this.bustOffsetYMin = clamp(value, BUST_OFFSET_Y_MIN_LIMIT, BUST_OFFSET_Y_MAX_LIMIT);
        saveConfig();
    }

    public double getBustOffsetZMin() {
        return bustOffsetZMin;
    }

    public void setBustOffsetZMin(double value) {
        this.bustOffsetZMin = clamp(value, BUST_OFFSET_Z_MIN_LIMIT, BUST_OFFSET_Z_MAX_LIMIT);
        saveConfig();
    }

    public double getBustOffsetZMax() {
        return bustOffsetZMax;
    }

    public void setBustOffsetZMax(double value) {
        this.bustOffsetZMax = clamp(value, 0.0, BUST_OFFSET_Z_MAX_LIMIT);
        saveConfig();
    }

    public double getBounceMultiplierMax() {
        return bounceMultiplierMax;
    }

    public void setBounceMultiplierMax(double value) {
        this.bounceMultiplierMax = clamp(value, BOUNCE_MULTIPLIER_MIN, BOUNCE_MULTIPLIER_MAX);
        saveConfig();
    }

    public double getFloppyMultiplierMax() {
        return floppyMultiplierMax;
    }

    public void setFloppyMultiplierMax(double value) {
        this.floppyMultiplierMax = clamp(value, FLOPPY_MULTIPLIER_MIN, FLOPPY_MULTIPLIER_MAX);
        saveConfig();
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
