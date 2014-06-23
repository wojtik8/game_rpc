var thrift = require("thrift");
var Player = require("./gen-nodejs/Player");
var Game = require("./gen-nodejs/Game");
var gametypes = require("./gen-nodejs/game_types");
var playertypes = require("./gen-nodejs/player_types");

var SERVER_PORT = 8200;

var processor =  new thrift.MultiplexedProcessor();

var GameHandler = {
	ping: function(result) {
		console.log("ping()");
		result(null,1);
	},

	login: function(name, password, result) {
		console.log("login: ", name, " password: ", password);
		result(null, 11);
	},

	registerCredentials: function(name, password, result) {
		console.log("register login: ", name, " password: ", password);
		var val = 12;
		result(null, val);
	},
};

var PlayerHandler = {
	logout: function(result) {
		console.log("logout");
		result(null,1);
	},

};

processor.registerProcessor("Game",new Game.Processor(GameHandler));
processor.registerProcessor("Player",new Player.Processor(PlayerHandler));

var server = thrift.createMultiplexServer(processor, {tls: false});
server.listen(SERVER_PORT);

