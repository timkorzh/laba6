package CLient;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandSender {
    public CommandSender(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
    private DatagramChannel datagramChannel;

    public void send(String command, SocketAddress a) throws IOException {
        byte[] b = command.getBytes();
        ByteBuffer f;
        for(int i = 0; i < b.length; i += 10) {
            int length = Math.min(10, b.length - i);
            f = ByteBuffer.wrap(b, i, length);
            datagramChannel.send(f, a);
        }
    }


}