package com.czapl;

import api.CGame;
import api.IGame;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import rpc.Game;
import rpc.Player;

public class GameClient {
    
    public static void main(String[] args) throws IOException {
        
        
      IGame game = CGame.connectToServer("localhost", 8200);

      game.login("dupa", "dupka");
      game.disconnectFromServer();
//      TMultiplexedProtocol mpPlayer = new TMultiplexedProtocol(protocol, "Player");
//      Player.Client playerClient = new Player.Client(mpPlayer);
//      int retval;
//      
//      System.out.println("retval login: "+retval);
//      retval = gameClient.registerCredentials("dupa", "dupka");
//      System.out.println("retval register: "+retval);
//      retval = gameClient.ping();
//      System.out.println("player logout");
//      retval=playerClient.logout();
//      System.out.println("player logout retval "+retval);
      
   
    }

    public GameClient(String hostName, int portNumber) {

        
    }
    

}
