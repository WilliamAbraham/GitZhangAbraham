
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Index
{
    private HashMap <String, String> ind;

    public Index ()
    {
        //some constructor ig
        ind = new HashMap <String, String> ();

    }

    //initializes a project
    public void init () throws IOException
    {
        //new code
        File f = new File ("objects");

        //if directory doesn't exist, make a new one
        if (!f.exists())
        {
            File dir = new File ("objects");
            dir.mkdirs();
        }

        File in = new File ("index");

        //if index doesn't exist, make a new one
        if (!in.exists())
        {
            //in = new File ("objects", "index");

            in = new File ("index");
            in.createNewFile();
        }
    }

    public void addBlob (String fileName) throws IOException
    {
        Blob b = new Blob (fileName);
        //blobify writes the blob to objects
        b.blobify ();
        String ogName = b.getName ();
        String sha1 = b.getSHA1(fileName);

        ind.put (ogName, sha1);

        PrintWriter pw = new PrintWriter ("index");

        //updates the entire index... a little excessive, but it works
        for (HashMap.Entry <String, String> entry : ind.entrySet ())
            {
                pw.println (entry.getKey () + " : " + entry.getValue ());
            }

        //pw.println (ogName + " : " + sha1);

        //reader.close();
        pw.close();

    }

    public void addToPath(String fileName, String path) throws IOException
    {
        Blob b = new Blob (fileName);
        //blobify writes the blob to objects
        b.blobify ();
        String ogName = b.getName ();
        String sha1 = b.getSHA1(fileName);

        ind.put (ogName, sha1);

        PrintWriter pw = new PrintWriter (path);

        //updates the entire index... a little excessive, but it works
        for (HashMap.Entry <String, String> entry : ind.entrySet ())
            {
                pw.println (entry.getKey () + " : " + entry.getValue ());
            }

        //pw.println (ogName + " : " + sha1);

        //reader.close();
        pw.close();

    }

    public void removeBlob (String fileName) throws FileNotFoundException
    {
        ind.remove (fileName);

        PrintWriter pw = new PrintWriter ("index");
        //updates the entire index... a little excessive, but it works
        for (HashMap.Entry <String, String> entry : ind.entrySet ())
            {
                pw.println (entry.getKey () + " : " + entry.getValue ());
            }
        pw.close ();

    }

    public static void main (String [] args) throws IOException
    {
        Index testList = new Index ();
        testList.init ();
        testList.addBlob ("test.txt");
        testList.addBlob ("test1.txt");
        testList.removeBlob ("test.txt");
        Tree test = new Tree();
        test.add("blob : 94e66df8cd09d410c62d9e0dc59d3a884e458e05 : test.txt");
        test.add("blob : 5d44ddd39d2b59d4cc65fb905841f7d3fbd152ce : test1.txt");
        test.remove("test.txt");
        test.write();
    }

}