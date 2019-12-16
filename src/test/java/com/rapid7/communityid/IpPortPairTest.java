package com.rapid7.communityid;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class IpPortPairTest {

    @Test
    public void testCompareToFirstIsGreater() throws UnknownHostException {

        IpPortPair first = new IpPortPair(InetAddress.getByName("10.3.20.8"),  24);
        IpPortPair second = new IpPortPair(InetAddress.getByName("127.0.0.1"),  44);

        int result = second.compareTo(first);

        assertEquals(1, result);

    }

    @Test
    public void testCompareToSecondIsGreater() throws UnknownHostException {

        IpPortPair first = new IpPortPair(InetAddress.getByName("10.3.20.8"), 24);
        IpPortPair second = new IpPortPair(InetAddress.getByName("127.0.0.1"),  44);

        int result = first.compareTo(second);

        assertEquals(-1, result);

    }

    @Test
    public void testCompareToEqualSamePort() throws UnknownHostException {

        IpPortPair first = new IpPortPair(InetAddress.getByName("10.3.20.8"),  24);
        IpPortPair second = new IpPortPair(InetAddress.getByName("10.3.20.8"),  24);

        int result = first.compareTo(second);

        assertEquals(0, result);

    }

    @Test
    public void testCompareToEqualDifferentPort() throws UnknownHostException {

        IpPortPair first = new IpPortPair(InetAddress.getByName("10.3.20.8"),  20);
        IpPortPair second = new IpPortPair(InetAddress.getByName("10.3.20.8"),  24);

        int result = first.compareTo(second);

        assertEquals(-1, result);
    }

    @Test
    public void testBigIntegerIPV4() throws UnknownHostException {
        IpPortPair pair = new IpPortPair(InetAddress.getByName("192.168.170.8"),  7);

        BigInteger test = pair.toBigInt(pair.getIpAddress());

        assertEquals(BigInteger.valueOf(3232279048L), test);

    }

    @Test
    public void testBigIntegerIPV6() throws UnknownHostException {
        IpPortPair pair = new IpPortPair(InetAddress.getByName("fe80::200:86ff:fe05:80da"),  7);

        BigInteger test = pair.toBigInt(pair.getIpAddress());

        String actual = "338288524927261089654163160463460106458";

        assertEquals(actual, test.toString());

    }

}
