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

var defers = [Promise.defer(), Promise.defer(), Promise.defer()];
http.get(process.argv[2], function(response) {
    response.setEncoding('utf-8');
    response.pipe(bl(function (err, data) {
        if(err)
            return console.error(err);
        defers[0].resolve(data.toString());
    }));
});

http.get(process.argv[3], function(response) {
    response.setEncoding('utf-8');
    response.pipe(bl(function (err, data) {
        if(err)
            return console.error(err);
        defers[1].resolve(data.toString());
    }));
});

http.get(process.argv[4], function(response) {
    response.setEncoding('utf-8');
    response.pipe(bl(function (err, data) {
        if(err)
            return console.error(err);
        defers[2].resolve(data.toString());
    }));
});

Promise.all(defers.map(d => d.promise)).then( datas => {
    datas.forEach(d => console.log(d));
});
