var fs = require('fs');

fs.readFile(process.argv[2], (err, data) => {
    var line = data.toString().split('\n').length - 1;
    console.log(line);
});
