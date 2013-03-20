/*
* Definition of the routes
*/

var punctuationController = require('../controllers/punctuation'),
	punctuationValidator = require('../validation/punctuation');
  
var postForm 

exports.assignRoutes = function(app) {
	app.get('/', punctuationController.list);
    app.get('/new-punctuation', punctuationController.showForm);
    app.post('/new-punctuation', punctuationValidator.validate(), punctuationController.save);
}