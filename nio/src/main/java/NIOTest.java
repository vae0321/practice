import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pansifan on 16/7/2.
 */
public class NIOTest {

    public static void main(String[] args) {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
//            channel.accept();
            channel.socket().bind(new InetSocketAddress(9090));
            channel.configureBlocking(false);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int selectKeys = selector.select();

                if(selectKeys>0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        key.selector();

                        if(key.isAcceptable()) {
                            Socket socket = ((ServerSocketChannel) key.channel()).accept().socket();
                            SocketChannel socketChannel = socket.getChannel();

                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.read(buffer);

                            buffer.array();

                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
