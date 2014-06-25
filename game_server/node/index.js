var thrift = require("thrift");
var Player = require("./gen-nodejs/Player");
var Game = require("./gen-nodejs/Game");
var gametypes = require("./gen-nodejs/game_types");
var playertypes = require("./gen-nodejs/player_types");

var db = require("./db");

var SERVER_PORT = 8200;

var processor =  new thrift.MultiplexedProcessor();

var GameHandler = {
	ping: function(result) {
		console.log("ping()");
		db.list_users(function(entries, err) {
			console.log(entries);
		});
		result(null,1);
	},

	login: function(name, password, result) {
		console.log("login: ", name, " password: ", password);
		var credentials = {
			name: name,
			pwd: password
		};
		db.get_user(credentials, function(entry_data, err) {
			console.log(entry_data);
			db.login_user(entry_data);
			result(null, entry_data.id);
		});
	},

	registerCredentials: function(name, password, result) {
		console.log("register login: ", name, " password: ", password);
		var credentials = {
			name: name,
			password: password
		};
		db.add_user(credentials, function(entry_data, err) {
			console.log(err);
		});
		result(null, 1);
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

