var http = require('http');
var stream = require('stream');
var inherits = require('util').inherits;

function bl(cb){
    if(!(this instanceof bl)) return new bl(cb);
    this.on('finish', function() {cb(null, this.getBody())});
    stream.Writable.call(this);
    this.body = [];
}
inherits(bl, stream.Writable);
bl.prototype._write = function (chunk, enc, next) {
    this.body[this.body.length] = chunk;
    next();
}

bl.prototype.getBody = function () {
    return Buffer.concat(this.body);
}

http.get(process.argv[2], function(response) {
    response.setEncoding('utf-8');
    response.pipe(bl(function (err, data) {
        if(err)
            return console.error(err);
        console.log(data.toString().length);
        console.log(data.toString());
    }));
});
