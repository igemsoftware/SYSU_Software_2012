package eugene;

import java.io.*;

/** LogFile represents a transaction log of everything written to stdout and stderr.
 *  This class provides a simple and handy mechanism to capture standard output to a
 *  text file.
 *  There are two static methods - start() and stop().
 *  Calling start() creates a new log file or empties an existing log file.
 *  It copies into the log file characters printed to standard output and standard error.
 *  Calling stop() closes the log file and restores the behavior of standard output
 *  and standard error (that is, their behavior before start() was called)
 *  <br>
 *  A LogFile object is a PrintStream object that acts like a unix "tee".
 *  Any characters it receives are forwarded to two places: the log file
 *  and the underlying print streams (stdout and stderr).
 *  <br>
 *  Although both standard output and standard error are written into the same logfile,
 *  there is no need to synchronize this operation. The reason is that the logfile output
 *  stream is itself a print stream and write operations are synchronized.
 *  <br>
 *  Reference:
 *  <a href="http://java.sun.com/developer/TechTips/1999/tt1021.html">JDC Tech Tips: October 21, 1999</a>
 *  <br>
 *  Modifed by J. Dalbey 12/2003.
 */

public class LogFile extends PrintStream
{
    static OutputStream logfile;
    static PrintStream oldStdout;
    static PrintStream oldStderr;

    LogFile(PrintStream ps)
    {
        super(ps);
    }

    /** Starts copying stdout and stderr to a specified file.
      * @param fileName is the name of the desired logfile.
      */
    public static void start(String fileName) throws IOException
    {
        // First save the current standard output and standard error print streams.
        // These print streams will be restored when stop() is called.
        // Next, the log file is opened. If the log file does not exist, it's created.
        // Otherwise, the log file is emptied.
        // Finally, System.setOut() and System.setErr() are called to replace the
        // standard output and standard error print streams with LogFile print streams.

        // Save current settings for later restoring.
        oldStdout = System.out;
        oldStderr = System.err;

        // Create/Open logfile.
        logfile = new PrintStream(
                      new BufferedOutputStream(
                          new FileOutputStream(fileName)));

        // Indicate that output is to be redirected.
        System.setOut(new LogFile(System.out));
        System.setErr(new LogFile(System.err));
    }


    /**
     *  Ceases logging and restores the original settings.
     */
    public static void stop()
    {
        // Restore the original standard output and standard error.
        // Then close the log file.


        System.setOut(oldStdout);
        System.setErr(oldStderr);
        try
        {
            logfile.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** Overriding the write() method of PrintStream.
        To implement the desired "tee" behavior, the program needs to override
        the two forms of the write method. These overrides simply write the characters
        into the logfile and then to the underlying print stream
        (by calling super.write()). The write() methods do not throw exceptions.
        Instead, they set a flag in the print stream if some problem occurs.
        They set the flag by calling setError(). If the client of the print stream
        wants to check if an error occurred, it can call checkError().
     */
    public void write(int b)
    {
        try
        {
            logfile.write(b);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setError();
        }
        super.write(b);
    }

    /** Overriding the write() method of PrintStream */
    public void write(byte buf[], int off, int len)
    {
        try
        {
            logfile.write(buf, off, len);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setError();
        }
        super.write(buf, off, len);
    }
}