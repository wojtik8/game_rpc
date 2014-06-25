/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import rpc.Game;

/**
 *
 * @author wojtek
 */
public class CGame implements IGame{
    private Game.Client client;
    static TTransport transport;
    static TProtocol protocol;
    
    public static IGame connectToServer(String addr, int port) {
        transport = new TSocket(addr, port);
        try {
            transport.open();
        } catch (TTransportException ex) {
            Logger.getLogger(CGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        protocol = new  TBinaryProtocol(transport);
        System.out.println("connected to server: " +addr + ":" + port);
        CGame instance = new CGame();
        instance.client = new Game.Client(new TMultiplexedProtocol(protocol, "Game"));
        return instance;
    }

    @Override
    public IPlayer login(String name, String password) {
        System.out.println("login user: " +name + " pwd: " + password);
        int clientid;
        try {
            clientid = client.login(name, password);
            return new CPlayer(clientid);
        } catch (TException ex) {
            Logger.getLogger(CGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public void disconnectFromServer() {
        System.out.println("disconnecting from server");
        transport.close();
    }

    @Override
    public int register(String name, String password) {
        try {
            client.registerCredentials(name, password);
        } catch (TException ex) {
            Logger.getLogger(CGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public void ping() {
        try {
            client.ping();
        } catch (TException ex) {
            Logger.getLogger(CGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
