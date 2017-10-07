import a.namespace.goes.here.IsAliveService; 


class Server implements IsAliveService.Iface {
  public boolean isAlive() {
    System.out.println("This is the server. We just got asked if we are alive.");
    return true;
  }
}
