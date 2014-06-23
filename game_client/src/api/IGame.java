/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

/**
 *
 * @author wojtek
 */
public interface IGame {
    //public IGame connectToServer(String addr, int port);
    public IPlayer login(String name, String password);
    public void disconnectFromServer();
}
