var Punctuation = function(_user, _points, _date){

    this.user = _user;

    this.points = _points;

    this.creationTime = _date.getTime();
};

Punctuation.prototype.getCreationDate = function(){
    return new Date(this.creationTime);
};

exports.Punctuation = Punctuation;