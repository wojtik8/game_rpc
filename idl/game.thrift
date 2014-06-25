namespace java rpc

exception RemoteException {
	1: i32 what,
	2: string why
}

service Game {

	i32 login(1:string name, 2:string password) throws (1:RemoteException e),
	i32 registerCredentials(1:string name, 2:string password) throws (1:RemoteException e),
   	i32 ping()
}

