var javaGui = new JavaImporter(javax.swing, java.awt, java.awt.event);
with(javaGui){
    var button = new JButton("My Button");
    button.addActionListener(new ActionListener({
        actionPerformed: function(e){
            print("Button clicked");
        }
    }));
    var frame = new JFrame("GUI Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(button, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
}