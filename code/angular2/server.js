
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var server = require('http').createServer(app);
var io = require('socket.io')(server);

var START_PORT = 8000;

// // create application/json parser
// var jsonParser = bodyParser.json()

// // create application/x-www-form-urlencoded parser
// var urlencodedParser = bodyParser.urlencoded({ extended: false })

// let connectedUser = {
//     socketId: null,
//     id: null,
//     username: null
// }

// var users = [];

// function randomIntInc(low, high) {
//     return Math.floor(Math.random() * (high - low + 1) + low);
// }

// io.on('connection', (socket) => {

//     //send socket Id to client
//     app.get('/socketId', jsonParser, (req, res) => {
//         let data = socket.id;
//         res.status(200).json(data);
//     });

//     //recieve data user connected server from client
//     app.post('/infouserconnected', jsonParser, (req, res) => {
//         users.push(req.body);
//     });

//     console.log('User Connected... ' + socket.id);

//     socket.on('disconnect', function () {
//         console.log('User disconnected...' + socket.id);
//     });

//     socket.on('add-message', (message, username) => {
//         io.emit('message', { type: 'new-message', text: message, username: username })
//     });

// });


// //require from client to create a new PORT
// app.post('/requireCreatePort', jsonParser, (req, res) => {
//     console.log("SERVER WILL CREATE A NEW PORT");
//     console.log(req.body);
//     port = randomIntInc(9999, 40000);
//     server.listen(port, () => {
//         console.log('===============START===============')
//         console.log('NEW PORT HAS BEEN CREATED: ' + port);
//         console.log('===============END===============');
//     });
//     res.status(200).json(port);
// });


// io.on('new-connection', (socket) => {
//     socket.on('new-room', (toUserID) =>{
//         var roomID = fromUserID + '_' + socket.id;
//         socket.join(roomID);
//         io.sockets[toUserID].join(roomID);
//         io.sockets.in(roomID).emit('chat', roomID);
//     })
// });

var users = [];

io.on('connection', (socket) =>{
    console.log('a user has connected, socketid = ' + socket.id);

    socket.on('new-user-connected', (username) => {
        users.push({socket: socket, username: username});
    });

    socket.on('new-room', (toUser) =>{
        console.log('============ CREATE NEW ROOM ===========');
        let toSocket = null;
        let fromUser = null
        for (let i = 0; i < users.length; i++) {
            if (toUser == users[i].username) {
                toSocket = users[i].socket;
                break;
            }
        }
        for (var i = 0; i < users.length; i++) {
            if(socket == users[i].socket){
                fromUser = users[i].username;
                break;
            } 
        }
        console.log(toSocket.id);
        console.log(fromUser + ' - ' + toUser)
        let roomName = socket.id + '_' + toSocket.id;
        console.log('new room : ' + roomName);
        socket.join(roomName);
        toSocket.join(roomName);
        io.emit('room', {roomName: roomName, fromUser: fromUser, toUser: toUser});
        socket.on('private-chat', (msg, sender) =>{
            io.to(roomName).emit('message', {roomName: roomName, text: msg, sender: sender});
        });
        
    });

    socket.on('disconnect', () =>{
        for (var i = 0; i < users.length; i++) {
            if(socket.id == users[i].socket.id){
                users.splice(i, 1);
                break;
            }
        }
        console.log('a user has disconnected, socketid = ' + socket.id);
    });

})

server.listen(START_PORT, () => {
    console.log("Server STARTED");
});