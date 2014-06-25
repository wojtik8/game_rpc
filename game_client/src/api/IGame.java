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

    /**
     *
     * @param name
     * @param password
     * @return
     */
    public IPlayer login(String name, String password);

    /**
     *
     * @param name
     * @param password
     * @return
     */
    public int register(String name, String password);

    /**
     *
     */
    public void ping();
    /**
     *
     */
    public void disconnectFromServer();
}
