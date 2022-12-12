package org.acme.model;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jboss.logging.Logger;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.context.Context;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import com.pi4j.provider.Provider;

@Singleton
@Named("board")
public class Board {

    private Context pi4j;
    private boolean isReady = false;
    private DigitalOutput[] pins = new DigitalOutput[4];

    private static final Logger logger = Logger.getLogger("RASPI");

    public void init(){
        if(isReady == false){
            logger.info("[RASPI Board] Board initializing...");
            pi4j = Pi4J.newAutoContext();
            try {                

                Platforms platforms = pi4j.platforms();
                logger.info("[RASPI Board] PLATFORMS: " + platforms.describe().description());

                Platform platform = pi4j.platform();
                logger.info("[RASPI Board] DEFAULT PLATFORM: " + platform.describe().description());

                Providers providers = pi4j.providers();
                logger.info("[RASPI Board] PROVIDERS: " + providers.describe().description());

                Registry registry = pi4j.registry();
                logger.info("[RASPI Board] PROVIDERS: " + registry.describe().description());
                
                logger.info("[RASPI Board] creating pins...");

                pins[0] = pi4j.dout().create(1, "Pin_0");
                logger.info("[RASPI Board] OUTPUT: " + pins[0].description() + " - " + pins[0].state().value());
                pins[1] = pi4j.dout().create(3, "Pin_1");
                logger.info("[RASPI Board] OUTPUT: " + pins[1].description() + " - " + pins[1].state().value());
                pins[2] = pi4j.dout().create(5, "Pin_2");
                logger.info("[RASPI Board] OUTPUT: " + pins[2].description() + " - " + pins[2].state().value());
                pins[3] = pi4j.dout().create(7, "Pin_3");
                logger.info("[RASPI Board] OUTPUT: " + pins[3].description() + " - " + pins[3].state().value());

                logger.info("[RASPI Board] Board initialized");
                isReady = true;
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
                return;
            }
        }
    }

    public void on(int pin){
        if(isReady == false){
            this.init();
        }
        logger.info("[RASPI Board] pin " + pin + " requested on");
        pins[pin].high();
        logger.info("[RASPI Board] " + pins[pin].getName() + " is now " + pins[pin].state().value());
    }
    public void off(int pin){
        if(isReady == false){
            this.init();
        }
        logger.info("[RASPI Board] pin " + pin + " requested off");
        pins[pin].low();
        logger.info("[RASPI Board] " + pins[pin].getName() + " is now " + pins[pin].state().value());
    }

}