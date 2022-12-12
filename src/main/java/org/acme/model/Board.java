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

    //private Context pi4j;
    private boolean isReady = false;
    private DigitalOutput[] pins = new DigitalOutput[4];

    private static final Logger logger = Logger.getLogger("RASPI");

    public void init(){
        if(isReady == false){
            logger.info("[RASPI Board] Board initializing...");
            var pi4j = Pi4J.newAutoContext();
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

                var config = DigitalOutput.newConfigBuilder(pi4j)
                                    .id("PIN_00")
                                    .name("Switch termo salone")
                                    .address(1)
                                    .shutdown(DigitalState.LOW)
                                    .initial(DigitalState.LOW)
                                    .provider("pigpio-digital-output");
                pins[0] = pi4j.create(config);
                logger.info("[RASPI Board] OUTPUT: " + pins[0].description() + " - " + pins[0].state().value());

                config.id("PIN_01").name("This switch is not currently used").address(3);
                pins[1] = pi4j.create(config);

                config.id("PIN_02").name("Switch kebabbaro").address(5);
                pins[2] = pi4j.create(config);

                config.id("PIN_03").name("Switch lucine terrazzo").address(7);
                pins[3] = pi4j.create(config);

                logger.info("[RASPI Board] OUTPUT: " + pins[1].description() + "    " + pins[1].state().value());
                logger.info("[RASPI Board] OUTPUT: " + pins[2].description() + "    " + pins[2].state().value());
                logger.info("[RASPI Board] OUTPUT: " + pins[3].description() + "    " + pins[3].state().value());

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