var fs = require('fs');

module.exports = filterdir;

function filterdir(dir, suffix, cb) {
    fs.readdir(dir, (err, files) => {
        if(err){
            return cb(err);
        }
        cb(null, files.filter( f => f.endsWith(`.${suffix}`)));
    });
};

