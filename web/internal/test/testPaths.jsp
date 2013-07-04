<%@page import="java.io.*" %>

<%
File f = new File("/home/hasteer1/jvm/apache-tomcat-6.0.14/domains/hasteer.com/ROOT/images");
if(f.exists()) {
  System.out.println("file exists");
  if(f.isDirectory()) {
     System.out.println("file is a directory");
     File[] files = f.listFiles();
     for(File fi : files) {
        System.out.println(fi.getName());
     }
  }
  System.out.println("abs path " + f.getAbsolutePath());
  System.out.println("path: " + f.getPath());
  System.out.println("length: " + f.length());
  System.out.println("uri: " + f.toURI().toString());
  System.out.println("executable: " + f.canExecute());
  System.out.println("writable " + f.canWrite());
  System.out.println("readable " + f.canRead());
}

String testText = "This is a test text";
File testFile = new File(f, "test.txt");
System.out.println("test file uri: " + testFile.toURI().toString());
FileOutputStream fos = new FileOutputStream(testFile);
fos.write(testText.getBytes());
fos.close();
%>