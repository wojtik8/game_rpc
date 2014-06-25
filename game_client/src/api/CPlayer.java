/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import rpc.Player;

/**
 *
 * @author wojtek
 */
public class CPlayer implements IPlayer{

    TMultiplexedProtocol protocol;
    Player.Client client;
    
    CPlayer(int clientid) {
        System.out.println("CPlayer id: "+clientid );
        TMultiplexedProtocol protocol = new TMultiplexedProtocol(CGame.protocol, "Player");
        client = new Player.Client(protocol);
    }
    
    public int logout() {
        try {
            return client.logout();
        } catch (TException ex) {
            Logger.getLogger(CPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
