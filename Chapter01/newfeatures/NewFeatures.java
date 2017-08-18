public class NewFeatures{
  public static void main(String [] args) { 
    ProcessHandle currentProcess = ProcessHandle.current();
    System.out.println("PID: " + currentProcess.pid());
    ProcessHandle.Info currentProcessInfo = currentProcess.info();
    System.out.println("Info: " + currentProcessInfo);
  }
}
