var net = require('net');
var strftime = require('strftime');

function getDate(){
    return strftime("%Y-%m-%d %H:%M", new Date);
};
var server = net.createServer(function (socket) {
    socket.end(getDate());
});
server.listen(process.argv[2]);
