"use strict";
var m = require('./filterls.js');

var dir = process.argv[2];
var suffix = process.argv[3];
m(dir, suffix, (err, files) => {
    if(err) {
        return err;
    }
    files.forEach(f => console.log(f));
});
