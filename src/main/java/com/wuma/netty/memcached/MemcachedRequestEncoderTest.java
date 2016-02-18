package com.wuma.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liwujun
 * on 2016/2/18 at 17:25
 */
public class MemcachedRequestEncoderTest {
    @Test
    public void testMemcachedRequestEncoder() {
        MemcachedRequest request = new MemcachedRequest(Opcode.SET, "k1", "v1");
        EmbeddedChannel channel = new EmbeddedChannel(
                new MemcachedRequestEncoder());
        Assert.assertTrue(channel.writeOutbound(request));
        ByteBuf encoded = (ByteBuf) channel.readOutbound();
        Assert.assertNotNull(encoded);
        Assert.assertEquals(request.getMagic(), encoded.readInt());
        Assert.assertEquals(request.getOpCode(), encoded.readByte());
        Assert.assertEquals(2, encoded.readShort());
        Assert.assertEquals((byte) 0x08, encoded.readByte());
        Assert.assertEquals((byte) 0, encoded.readByte());
        Assert.assertEquals(0, encoded.readShort());
        Assert.assertEquals(2 + 2 + 8, encoded.readInt());
        Assert.assertEquals(request.getId(), encoded.readInt());
        Assert.assertEquals(request.getCas(), encoded.readLong());
        Assert.assertEquals(request.getFlags(), encoded.readInt());
    }
}
