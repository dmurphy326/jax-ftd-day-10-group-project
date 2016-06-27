import net from 'net'
import vorpal from 'vorpal'

const cli = vorpal()
// cli config
cli
  .delimiter('Enter connect yourName portNo:') // prompt

// connect mode
let server

cli
  .mode('connect [host] <Username> <port>')// < required argument > ,[ optional argument]
.delimiter(':')
  .init(function (args, callback) { // args takes host and port
    server = net.createConnection(args, () => { // creates a new socket
      const address = server.address()
      this.log(`connected to server ${address.address}:${address.port}`)
      this.delimiter(`${args.Username}`)
      this.log("Enter your name first to start the conversation ")
    // this.log to get into log in the init function
      callback()
    })
// cli
//   .mode('<user>')
//   .demlimiter('${user}:')
//   .init(function(args,callback)
// {
//   this.log('welocme ${user}')
//   callback()
// })
  server.on('data', (data) => { // listeners for data
      this.log(data.toString())
    })

    server.on('end', () => {
      this.log('disconnected from server :(')
    })
  })
  .action(function (command, callback) {
    if (command === 'exit') {
      server.end()
      callback()
    } else {
      server.write(command + '\n')
      callback()
    }
  })

export default cli
