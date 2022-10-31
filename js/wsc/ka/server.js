const http=require('http');

const hostname='127.0.0.1';
const port=3000;

const server=http.createServer((rq,rs) => {
  console.log('\n---- <request> ----');
  console.log(rq.method);
  console.log(rq.headers);
  rq.pipe(process.stdout);
  rs.statusCode=200;
  rs.setHeader('Content-Type', 'text/plain');
  rs.end('hello world\n');
});

server.listen(port,hostname,() => {
  console.log(`server running at http://${hostname}:${port}/`);
});

