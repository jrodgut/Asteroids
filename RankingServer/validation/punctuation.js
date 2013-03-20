var iform = require('iform');

exports.validate = iform({
        user: {
            required : true,
            len : [1, 80]
        },
        points: {
            required : true,
            type: 'int'
        }
});