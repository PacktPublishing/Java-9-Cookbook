module com.packt{
  //depends on the java.xml.bind module
  requires java.xml.bind;

  //need this for Messages class to be available to java.xml.bind
  exports  com.packt to java.xml.bind;
}
