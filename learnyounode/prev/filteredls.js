"use strict";
var fs = require('fs');

class Defer {
    constructor() {
        this.promise = new Promise((resolve,reject) => {
            this.resolve = resolve;
            this.reject = reject;
        });
    }
}

var readdir = new Defer();
var dir = process.argv[2];
var suffix = process.argv[3];
fs.readdir(dir, (err, data) => {
    if(!!err){
        readdir.reject(err);
    }else{
        readdir.resolve(data);
    }
});

readdir.promise.then( files => {
    files.filter(f => f.endsWith(`.${suffix}`))
        .forEach(f => console.log(f));
});
