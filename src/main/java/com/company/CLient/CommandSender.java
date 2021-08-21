package com.company.CLient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
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
public class ReplyReceiver {
        public ReplyReceiver(DatagramChannel datagramChannel) {
            this.datagramChannel = datagramChannel;
        }
        private DatagramChannel datagramChannel;
}
    public String receive() throws IOException {
        String answer = "";
        ByteBuffer f = ByteBuffer.allocate(10);
        SocketAddress s = datagramChannel.receive(f);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        while(!answer.endsWith("\n")) {
            for (int i = 0; i < 10 && s == null; i++) {
                try {
                    Thread.sleep(1000);
                    s = datagramChannel.receive(f);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(s == null) {
                return null;
            }
            out.write(f.array());
            answer = out.toString().replaceAll("\00", "");
            f.clear();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(f.array());
    }
}