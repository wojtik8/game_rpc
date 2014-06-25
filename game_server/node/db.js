var mysql = require('mysql');

var MYSQL_HOST = 'localhost';
var MYSQL_USERNAME = 'root';
var MYSQL_PASSWORD = '1p0qqpalzm123';
var DB_NAME = 'node_db';

var mysql_config = {
	host     : MYSQL_HOST,
	user: MYSQL_USERNAME,
	password: MYSQL_PASSWORD		
};

// init;
var connection = mysql.createConnection(mysql_config);

function errFunc(err) {
	if (err) { 
		console.log(err);
		throw err; 
	}
}

function resetDatabase(aconnection) {
	console.log('resetting db');
	aconnection.query('DROP DATABASE IF EXISTS '+DB_NAME,  errFunc);		// destroy old db
	aconnection.query('CREATE DATABASE '+DB_NAME, errFunc);				// create database
	aconnection.query('USE '+DB_NAME);
	var sql = ""+
	"CREATE TABLE Users("+
	" id INT unsigned NOT null auto_increment,"+
	" name VARCHAR(50) NOT null default 'unknown',"+
	" exp INT unsigned NOT null default 0,"+
	" password CHAR(60) NOT null,"+
	" primary key (id)"+
	");";
	
	aconnection.query(sql, errFunc);// create table
}

connection.query('USE '+DB_NAME);
//resetDatabase(connection);


function handleDisconnect(aconnection) {
	aconnection.on('error', function(err) {
		if (!err.fatal) {
			return;
		}

		if (err.code !== 'PROTOCOL_CONNECTION_LOST') {
			throw err;
		}

		console.log('Re-connecting lost connection');

		aconnection = mysql.createConnection(aconnection.config);
		handleDisconnect(aconnection);
		aconnection.connect(function(err) {
	        if (err) {
	            console.log("SQL CONNECT ERROR: " + err);
	        } else {
	            console.log("SQL CONNECT SUCCESSFUL.");
	            aconnection.query('USE '+DB_NAME);
	            connection = aconnection;
	        }
	    });
	});
}

handleDisconnect(connection);

exports.add_user = function(data, errFunc) {
	connection.query("SELECT COUNT(*) num FROM Users WHERE name = '"+data.name+"'", function(err, results, fields) {
		if(results[0].num > 0) {
			errFunc("User exists");
			return;
		}
		else {
			connection.query("INSERT INTO Users (name, password) VALUES (?,?)", [data.name, data.password], function(err, info) {
				console.log('User '+data.name+' has password '+data.password); 
			});
		}
	});	  

};

exports.get_user = function(data, callback) {
	connection.query("SELECT * FROM Users WHERE name = '"+data.name+"'", function(err, results, fields) {
		if(results.length == 0) {
			callback(undefined, "User doesn't exist");
		}
		else {
			callback(results[0], undefined);
		}
	});
};

exports.list_users = function(callback) {
	connection.query("SELECT name,exp FROM Users", function(err, results, fields) {
		if(err) {
			callback(undefined, err);
		}
		else {
			callback(results, undefined);
		}
	});
};

exports.login_user = function(data) {
	connection.query("UPDATE Users SET exp = exp + 1 WHERE id = ?",[data.id], function(err) {
	});
};
