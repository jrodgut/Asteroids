var mongo = require('mongoskin');

var database_url = 'localhost:27017/ranking_asteroids';
var punctuation_collection = 'punctuations';

var collection = mongo.db(database_url).collection(punctuation_collection);

/*
* It saves a new position in the MongoDB database
*/
exports.save = function(punctuation, callback){
	collection.save(punctuation, callback);
};

/*
* It finds the ranking (positions ordered by points in descending way)
*/
exports.findRanking = function(_skip, _limit, callback){
	collection.find().sort({points : -1}).skip(_skip).limit(_limit).toArray(function(err, punctuations){
		callback(err, punctuations);
	});
};

/*
* It counts the number of positions registered so far
*/
exports.count = function(callback){
	collection.count(function(err, count){
		callback(err, count);
	});
};