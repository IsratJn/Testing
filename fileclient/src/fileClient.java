import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class fileClient {
    static final int LISTENING_PORT = 3210;

    private static boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }


    public static void main(String[] args) {

        String computer;          // Name or IP address of server.
        final Socket[] connection = new Socket[1];        // Socket for communicating with that computer.
        final PrintWriter[] outgoing = new PrintWriter[1];     // Stream for sending a command to the server.
        final BufferedReader[] incoming = new BufferedReader[1];


        computer = "127.0.0.1";

        String commandIndex = "INDEX";
        String commandGet = "GET ";
        String commandSend = "SEND ";

        // Set the frame to house everything.
        JFrame jFrame = new JFrame("WittCode's Client");
        // Set the size of the frame.
        jFrame.setSize(450, 600);
        // Make the layout to be box layout that places its children on top of each other.
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        // Make it so when the frame is closed the program exits successfully.
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title above panel.
        JLabel jlTitle = new JLabel("WittCode's File Sender");
        // Change the font family, size, and style.
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        // Add a border around the label for spacing.
        jlTitle.setBorder(new EmptyBorder(20,0,50,0));
        // Make it so the title is centered horizontally.
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JTextField t1;
        t1 = new JTextField("");
        t1.setLocation(50,100);

//        t2= new JTextField("Not Connected");
//        t2.setBounds(50,100, 50,20);

        JLabel t2 = new JLabel("Not Connected");
        t2.setBackground(Color.GRAY);

        JButton b1 = new JButton("Connect");
        b1.setSize(20,20);
        final Boolean[] connected = {false};
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(available(parseInt(t1.getText())))
                {
                    t2.setText("Port is not open");
                }
                else
                {
                    t2.setText("Connected to the port");
//                    System.out.println("dhukse");
//                    connected[0] = true;

                }
            }
        });



        // Label that has the file name.
        JLabel jlFileName = new JLabel("Choose a file to send.");
        // Change the font.
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        // Make a border for spacing.
        jlFileName.setBorder(new EmptyBorder(100, 0, 0, 0));
        // Center the label on the x axis (horizontally).
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel that contains the buttons.
        JPanel jpButton = new JPanel();
        // Border for panel that houses buttons.
        jpButton.setBorder(new EmptyBorder(20, 0, 100, 0));
        // Create send file button.
        JButton jbSendFile = new JButton("Send File");
        // Set preferred size works for layout containers.
        jbSendFile.setPreferredSize(new Dimension(150, 40));
        // Change the font style, type, and size for the button.
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));
        // Make the second button to choose a file.
        JButton jbChooseFile = new JButton("Choose File");
        // Set the size which must be preferred size for within a container.
        jbChooseFile.setPreferredSize(new Dimension(150, 40));
        // Set the font for the button.
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jshowfile = new JButton("Server Files List") ;
        jshowfile.setPreferredSize(new Dimension(150, 40));
        jshowfile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jDownloadfile = new JButton("Download") ;
        jDownloadfile.setPreferredSize(new Dimension(150, 40));
        jDownloadfile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jDeletefile = new JButton("Download") ;
        jDeletefile.setPreferredSize(new Dimension(150, 40));
        jDeletefile.setFont(new Font("Arial", Font.BOLD, 20));


        // Add the buttons to the panel.
        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);
        jpButton.add(jshowfile);
        jpButton.add(jDownloadfile);
        jpButton.add(jDeletefile);

        final File[] filetosend = new File[1];
        final File[] filetoreceive = new File[1];

        jbChooseFile.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             File file = new File("");
             String currentDirectoryPath = file.getAbsolutePath();
             // Create a file chooser to open the dialog to choose a file.
             JFileChooser jFileChooser = new JFileChooser(currentDirectoryPath);
             // jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
             // Set the title of the dialog.
             jFileChooser.setDialogTitle("Choose a file to send.");
             // Show the dialog and if a file is chosen from the file chooser execute the following statements.
             if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                 // Get the selected file.
                 filetosend[0] = jFileChooser.getSelectedFile();
                 // Change the text of the java swing label to have the file name.
                 jlFileName.setText("The file you want to send is: " + filetosend[0].getName());

                 }
             }
        });


        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If a file has not yet been selected then display this message.
                if (filetosend[0] == null) {
                    jlFileName.setText("Please choose a file to send first!");
                    // If a file has been selected then do the following.
                } else {
                    try {
                        // Create an input stream into the file you want to send;
                        connection[0] = new Socket(computer,parseInt(t1.getText()));
                        incoming[0] = new BufferedReader(
                                new InputStreamReader(connection[0].getInputStream()));
                        outgoing[0] = new PrintWriter(connection[0].getOutputStream());

                        outgoing[0].println(commandSend);
                        BufferedReader fileSend = new BufferedReader(new FileReader(filetosend[0]));
                        outgoing[0].println(filetosend[0].getName());
                        //System.out.println(fileSend);
                        while (true) {
                            // Read and send lines from the file until
                            // an end-of-file is encountered.
                            String line = fileSend.readLine();
                            if (line == null)
                                break;
                            outgoing[0].println(line);
                            //System.out.println(line);
                        }
                        outgoing[0].flush();
                        outgoing[0].close();
                        if (outgoing[0].checkError())
                            System.out.println("Oh noooo");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        jshowfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    connection[0] = new Socket(computer, parseInt(t1.getText()));
                    incoming[0] = new BufferedReader(
                            new InputStreamReader(connection[0].getInputStream()));
                    outgoing[0] = new PrintWriter(connection[0].getOutputStream());
                    outgoing[0].println(commandIndex);
                    outgoing[0].flush();
                    File filefordirectory = new File("ServerFiles");
                    filefordirectory.mkdir();
                    String directory = filefordirectory.getAbsolutePath();
                        while (true) {
                            String line = incoming[0].readLine();
                            if (line == null)
                                break;

                            File filelist = new File(directory,line);
                            filelist.createNewFile();
                        }
                    outgoing[0].close();

                    JFileChooser jFileChooser = new JFileChooser(directory);
                    jFileChooser.setDialogTitle("Choose a file to Download.");
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        filetoreceive[0] = jFileChooser.getSelectedFile();
                        jlFileName.setText("The file you want to receive is: " + filetoreceive[0].getName());
                    }
                    File[] files= filefordirectory.listFiles();
                    for(File file : files) {
                        System.out.println(file + " deleted.");
                        file.delete();
                    }
                    filefordirectory.delete();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        jDownloadfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    connection[0] = new Socket(computer, parseInt(t1.getText()));
                    incoming[0] = new BufferedReader(
                            new InputStreamReader(connection[0].getInputStream()));
                    outgoing[0] = new PrintWriter(connection[0].getOutputStream());
                    outgoing[0].println(commandGet + filetoreceive[0].getName());
                    outgoing[0].flush();
                    String message = incoming[0].readLine();
                        System.out.println(message);
                        if (!message.equalsIgnoreCase("OK")) {
                            System.out.println("File not found on server.");
                            System.out.println("Message from server: " + message);
                            return;
                        }

                        File fileto = new File("");
                        String path = fileto.getAbsolutePath()+"/DownloadedFiles";
                        System.out.println(path);
                        PrintWriter fileOut;  // For writing the received data to a file.
                        String filename = incoming[0].readLine();

                        File downoladedfile = new File(path,filename);

                        fileOut = new PrintWriter(new FileWriter(downoladedfile));
                        while (true) {
                            // Copy lines from incoming to the file until
                            // the end of the incoming stream is encountered.
                            String line = incoming[0].readLine();
                            if (line == null)
                                break;
                            fileOut.println(line);
                            System.out.println(line);
                        }

                        if (fileOut.checkError()) {
                            System.out.println("Some error occurred while writing the file.");
                            System.out.println("Output file might be empty or incomplete.");
                        }
                    outgoing[0].close();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        jDeletefile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    connection[0] = new Socket(computer, parseInt(t1.getText()));
                    incoming[0] = new BufferedReader(
                            new InputStreamReader(connection[0].getInputStream()));
                    outgoing[0] = new PrintWriter(connection[0].getOutputStream());
                    outgoing[0].println("DElETE " + filetoreceive[0].getName());
                    outgoing[0].flush();
                    outgoing[0].close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            }
        });




        jFrame.add(jlTitle);
        jFrame.add(t1) ;
        jFrame.add(b1);
        jFrame.add(t2);
        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.setVisible(true);



//        String computer;          // Name or IP address of server.
//        Socket connection;        // Socket for communicating with that computer.
//        PrintWriter outgoing;     // Stream for sending a command to the server.
//        BufferedReader incoming;  // Stream for reading data from the connection.
        //String commandIndex,commandGet,commandSend;           // Command to send to the server.


      /* Check that the number of command-line arguments is legal.
         If not, print a usage message and end. */

//        if (args.length == 0 || args.length > 3) {
//            System.out.println("Usage:  java FileClient <server>");
//            System.out.println("    or  java FileClient <server> <file>");
//            System.out.println(
//                    "    or  java FileClient <server> <file> <local-file>");
//            return;
//        }

        /* Get the server name and the message to send to the server. */
//
//        computer = "127.0.0.1";
//
//        String commandIndex = "INDEX";
//        String commandGet = "GET ";
//        String commandSend = "SEND ";


//        int ck=0;
//        System.out.println(connected[0]);
//        if(connected[0])
//            {
//                System.out.println(ck);
//                try {
////                    connection[0] = new Socket(computer, parseInt(t2.getText()));
////                    incoming[0] = new BufferedReader(
////                            new InputStreamReader(connection[0].getInputStream()));
////                    outgoing[0] = new PrintWriter(connection[0].getOutputStream());
//                    if (ck == 0) {
//                        outgoing[0].println(commandIndex);
//                        outgoing[0].flush();
//                    } else if (ck == 1) {
//                        outgoing[0].println(commandGet + "datatest1.txt");
//                        outgoing[0].flush();
//                    } else if (ck == 2) {
//
////                        outgoing[0].println(commandSend);
////                        BufferedReader fileSend = new BufferedReader(new FileReader("untitled.txt"));
////                        outgoing[0].println("untitled.txt");
////                        //System.out.println(fileSend);
////                        while (true) {
////                            // Read and send lines from the file until
////                            // an end-of-file is encountered.
////                            String line = fileSend.readLine();
////                            if (line == null)
////                                break;
////                            outgoing[0].println(line);
////                            //System.out.println(line);
////                        }
////                        outgoing[0].flush();
////                        outgoing[0].close();
////                        if (outgoing[0].checkError())
////                            System.out.println("Oh noooo");
//                    }
//
//                    // ESSENTIAL: Make sure command is dispatched to server!
//                } catch (Exception e) {
//                    System.out.println(
//                            "Can't make connection to server at \"" + args[0] + "\".");
//                    System.out.println("Error:  " + e);
//                    return;
//                }
//
//                /* Read and process the server's response to the command. */
//
//                try {
//                    if (ck == 0) {
//                        // The command was "index".  Read and display lines
//                        // from the server until the end-of-stream is reached.
//                        System.out.println("File list from server:");
//                        while (true) {
//                            String line = incoming[0].readLine();
//                            if (line == null)
//                                break;
//                            System.out.println("   " + line);
//                        }
//                    } else if (ck == 1) {
//                        // The command was "get <file-name>".  Read the server's
//                        // response message.  If the message is "OK", get the file.
//                        String message = incoming[0].readLine();
//                        System.out.println(message);
//                        if (!message.equalsIgnoreCase("OK")) {
//                            System.out.println("File not found on server.");
//                            System.out.println("Message from server: " + message);
//                            return;
//                        }
//                        PrintWriter fileOut;  // For writing the received data to a file.
//                        String filename = incoming[0].readLine();
//                        fileOut = new PrintWriter(new FileWriter(filename));
//
//                        while (true) {
//                            // Copy lines from incoming to the file until
//                            // the end of the incoming stream is encountered.
//                            String line = incoming[0].readLine();
//                            if (line == null)
//                                break;
//                            fileOut.println(line);
//                        }
//                        if (fileOut.checkError()) {
//                            System.out.println("Some error occurred while writing the file.");
//                            System.out.println("Output file might be empty or incomplete.");
//                        }
//                    } else if (ck == 3) {
////                File file = new File("Help.txt");
////                String xd = "Help me Allah.";
////
////               // BufferedReader fileSend = new BufferedReader(new FileReader(file));
////
////                outgoing.println("HelpMe");
////                outgoing.flush();
////                outgoing.close();
//
//
//                    }
//                } catch (Exception e) {
//                    System.out.println(
//                            "Sorry, an error occurred while reading data from the server.");
//                    System.out.println("Error: " + e);
//                } finally {
//                    try {
//                        connection[0].close();
//                    } catch (IOException e) {
//                    }
//                }
//
//
//            }

        }


//
//    public static String getFileExtension(String fileName) {
//        // Get the file type by using the last occurence of . (for example aboutMe.txt returns txt).
//        // Will have issues with files like myFile.tar.gz.
//        int i = fileName.lastIndexOf('.');
//        // If there is an extension.
//        if (i > 0) {
//            // Set the extension to the extension of the filename.
//            return fileName.substring(i + 1);
//        } else {
//            return "No extension found.";
//        }
//    }

    /**
     * When the jpanel is clicked a popup shows to say whether the user wants to download
     * the selected document.
     *
     * @return A mouselistener that is used by the jpanel.
     */
//    public static MouseListener getMyMouseListener() {
//        return new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // Get the source of the click which is the JPanel.
//                JPanel jPanel = (JPanel) e.getSource();
//                // Get the ID of the file.
//                int fileId = Integer.parseInt(jPanel.getName());
//                // Loop through the file storage and see which file is the selected one.
//                for (MyFile myFile : myFiles) {
//                    if (myFile.getId() == fileId) {
//                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
//                        jfPreview.setVisible(true);
//                    }
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        };
//    }


}
