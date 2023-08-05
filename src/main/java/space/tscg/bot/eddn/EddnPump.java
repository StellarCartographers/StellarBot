package space.tscg.bot.eddn;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;


/**
 * Subscribe to zmq relay from EDDN
 */
public class EddnPump extends Thread {

    public static final String SCHEMA_KEY = "$schemaRef";
    public static final String RELAY = "tcp://eddn.edcd.io:9500";

    public void msg(String msg) {
        try
        {
            final Writer fileWriter = new FileWriter("output.txt", true);
            fileWriter.write(msg + "\n");
            fileWriter.close();
        } catch (IOException e)
        {
        }
    }

    @Override
    public void run() {
        pump();
    }

    public synchronized void pump() {
        try (ZContext ctx = new ZContext())
        {
            ZMQ.Socket client = ctx.createSocket(ZMQ.SUB);
            client.subscribe("".getBytes());
            client.setReceiveTimeOut(30000);

            client.connect(RELAY);
            msg("EDDN Relay connected");
            ZMQ.Poller poller = ctx.createPoller(2);
            poller.register(client, ZMQ.Poller.POLLIN);
            byte[] output = new byte[256 * 1024];
            while (true) {
                int poll = poller.poll(10);
                if (poll == ZMQ.Poller.POLLIN) {
                    //ZMQ.PollItem item = poller.getItem(poll);

                    if (poller.pollin(0)) {
                        byte[] recv = client.recv(ZMQ.NOBLOCK);
                        if (recv.length > 0) {
                            // decompress
                            Inflater inflater = new Inflater();
                            inflater.setInput(recv);
                            try {
                                int outlen = inflater.inflate(output);
                                String outputString = new String(output, 0, outlen, "UTF-8");
                                // outputString contains a json message

                                if (outputString.contains(SCHEMA_KEY)) {
                                    msg(outputString);
                                }

                            } catch (DataFormatException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        File f = new File("output.txt");
        if(f.exists())
            f.delete();
        new EddnPump().pump();
    }
}
