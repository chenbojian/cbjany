var http = require('http');
var url = require('url');

var server = http.createServer((req, res) => {
    res.writeHead(200, {'content-type': 'application/json'});
    var urlObj = url.parse(req.url, true);
    var pathname = urlObj.pathname;
    var query = urlObj.query;
    var date = new Date(query.iso);
    if(pathname === '/api/parsetime') {
        res.end(JSON.stringify({hour: date.getHours(), minute: date.getMinutes(), second: date.getSeconds()}));
    } else {
        res.end(JSON.stringify({unixtime: date.getTime()}));
    }

});
server.listen(process.argv[2]);
