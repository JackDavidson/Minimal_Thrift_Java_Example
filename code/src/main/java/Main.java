import a.namespace.goes.here.IsAliveService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.*;

public class Main {
  static int port = 7809;

  public static void main(String[] args) throws TException, InterruptedException {

    // first, we need the instance of our server. this object will handle the calls. You should make this object
    // (in our case Server()) thread-safe. multiple threads that thrift runs may make calls to it
    IsAliveService.Processor server = new IsAliveService.Processor(new Server());

    // we need to instantiate a server socket. This will tell thrift what port to listen on.
    TServerTransport transport = new TServerSocket(port);

    TThreadPoolServer threadPoolServer = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(server));

    // start serving on another thread.
    Thread serverThread = new Thread(() -> {
      threadPoolServer.serve();
      System.out.println("the server has stopped.");
    });
    System.out.println("Starting the server.");
    serverThread.start();
    // wait for the server to start up.
    Thread.sleep(200);

    // lets get a connection to the server
    performRequestToServer();
    System.out.println("Shutting down the server.");
    threadPoolServer.stop();
  }

  private static void performRequestToServer() throws TException {

    TTransport transport;

    transport = new TSocket("localhost", port);
    System.out.println("This is the client. Connecting to the server.");
    transport.open();

    TProtocol protocol = new TBinaryProtocol(transport);
    IsAliveService.Client client = new IsAliveService.Client(protocol);


    System.out.println("This is the client. Asking if the server is alive.");
    System.out.println("This is the client. Is the server alive: " + client.isAlive());

    // if we don't close this, the server won't be able to cleanly shut down.
    // (it will only shut down upon subsequent calls)
    transport.close();
  }
}