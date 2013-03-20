var punctuation = require('../models/Punctuation'),
	punctuationRepository = require('../repositories/punctuation'),
	logger = require('../libs/winston');

const ranking_size = 10;

/*
* Handler method used to put back the information
* sent to be shown in the page when an error occurs 
*/
var putErrorData = function(req, res){
	var errors = {};
	if('iform' in req){
		errors = req.iform.errors;
	}
	return {title: 'Nuevo Post', body: req.body, errors: errors};
};

/*
* It shows the form for a new punctuation
*/
exports.showForm = function(req, res){
	res.render('new-punctuation', putErrorData(req, res));
}

/*
* It saves a new punctuation in the database
*/
exports.save = function(req, res){
	logger.info('Saving punctuation');
	if(req.iform.errors) {		
		res.render('new-punctuation', putErrorData(req, res));
	}else{
		//Get parameters from POST
		var user = req.body.user;
		var points = parseInt(req.body.points,10);

		var p = new punctuation.Punctuation(user, points, new Date());

		var callback = function(){
			res.redirect('/');
		};

		punctuationRepository.save(p, callback)

		
	}	
}

/*
* It shows the list of punctuations for the ranking
*/
exports.list = function(req, res){

	logger.info('Retrieving ranking');
	var data = {
			'title' : 'Ranking de Asteroides'
		};

	var ranking = [];

	function findCallback(err, punctuations){
		if(err){
			logger.error('finding ranking error:' + err);
		}

		logger.info('punctuations: ' + punctuations);
		
		var ranking_length = punctuations.length;

		logger.info('Ranking window size:', ranking_length);

		for(var i = 0; i< ranking_length; i++){
			var p = punctuations[i];
			var user = p.user;
			var points = p.points;
			var creationTime = p.creationTime;
			var date = new Date(creationTime);
			var rank = new punctuation.Punctuation(user, points, date);
			ranking.push(rank);
		}

		data['punctuations'] = ranking;
		
		punctuationRepository.count(countCallback);
	}

	function countCallback(err, count){
		if(err){
			logger.error('counting ranking error:' + err);
		}		

		data['count'] = count;
		render();
	}

	function render(){
		res.render('ranking', data);
	}


	punctuationRepository.findRanking(0, ranking_size, findCallback);

};