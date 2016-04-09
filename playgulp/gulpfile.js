var gulp = require('gulp');
var through = require('through2');

function getFiles() {
    var count = 0;
    return through.obj(function(chunk, encoding, done){
        console.log('called by ', chunk.path);
        console.log('file content ', chunk.contents.toString());
        console.log('number = ', count++);
        done();
    });
};

gulp.task('default', () => {
    return gulp.src("src/*").pipe(getFiles());
});
