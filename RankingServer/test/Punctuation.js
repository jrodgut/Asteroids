var assert = require("assert"),
	punctuation = require('../models/Punctuation')
	should = require("should");


describe('Punctuation', function(){

	beforeEach(function(){
      this.p = new punctuation.Punctuation('Pepe', 12, new Date());
    });

	describe('creationTime', function(){
	    it('should return a number', function(){
	      assert.ok(!isNaN(this.p.creationTime));
	    });
	});

	describe('getCreationDate', function(){
	    it('should not return null', function(){
	      assert.ok(this.p.getCreationDate() != null);
	    });
	});

});
