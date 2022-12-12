package org.acme.mqtt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

import org.acme.model.Board;

@ApplicationScoped
public class Raspi {

    @Inject
    @Named("board")
    Board board;

    @Channel("topic-raspi00")
    Emitter<String> statRequestEmitter0; 
    @Channel("topic-raspi01")
    Emitter<String> statRequestEmitter1; 
    @Channel("topic-raspi02")
    Emitter<String> statRequestEmitter2; 
    @Channel("topic-raspi03")
    Emitter<String> statRequestEmitter3; 

    public String process(int pin, String msg) {
        String status = "OFF";
        if(msg.equalsIgnoreCase("on")){
            board.on(pin);
            status = "ON";
        }
        if(msg.equalsIgnoreCase("off")){
            board.off(pin);
        }
        return status;
    }

    @Incoming("raspi00")
    public void p00(byte[] message) {
        String msg = new String(message);
        statRequestEmitter0.send(this.process(0, msg));
    }

    @Incoming("raspi01")
    public void p01(byte[] message) {
        String msg = new String(message);
        statRequestEmitter0.send(this.process(1, msg));
    }

    @Incoming("raspi02")
    public void p02(byte[] message) {
        String msg = new String(message);
        statRequestEmitter0.send(this.process(2, msg));
    }

    @Incoming("raspi03")
    public void p03(byte[] message) {
        String msg = new String(message);
        statRequestEmitter0.send(this.process(3, msg));
    }

}