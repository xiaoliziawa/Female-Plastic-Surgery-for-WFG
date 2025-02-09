package com.rinko1231.femaleplasticsurgery;

import com.rinko1231.femaleplasticsurgery.config.SurgeryConfig;
import net.fabricmc.api.ModInitializer;

public class Femaleplasticsurgery implements ModInitializer {
    public static final String MOD_ID = "femaleplasticsurgery";

    @Override
    public void onInitialize() {
        SurgeryConfig.getInstance();
    }
}
