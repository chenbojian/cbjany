var fs = require('fs');
var buf = fs.readFileSync(process.argv[2]);
var content = buf.toString();
console.log(content.split('\n').length - 1);
