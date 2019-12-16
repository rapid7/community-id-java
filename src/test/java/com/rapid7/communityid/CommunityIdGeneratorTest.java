/***************************************************************************
 * COPYRIGHT (C) 2012-2019, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.communityid;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;


import org.junit.Test;


/**
 * Tests the community ID generator. It uses the same test values as the python implementation.
 * https://github.com/corelight/pycommunityid.
 */
public class CommunityIdGeneratorTest {

    @Test
    public void testCommunityIdGeneratorSortsCorrectly() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("128.232.110.120"),  34855);
        IpPortPair second = new IpPortPair(InetAddress.getByName("66.35.250.204"),  80);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        String result2 = generator.generateCommunityId(proto, second.getIpAddress(), second.getPort(),
                first.getIpAddress(), first.getPort());

        assertEquals(result, result2);

    }

    @Test
    public void testCommunityIdGeneratorTCP() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("128.232.110.120"),  34855);
        IpPortPair second = new IpPortPair(InetAddress.getByName("66.35.250.204"),  80);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:LQU9qZlK+B5F3KDmev6m5PMibrg=");

    }

    @Test
    public void testCommunityIdGeneratorTCP2() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("66.35.250.204"),  80);
        IpPortPair second = new IpPortPair(InetAddress.getByName("128.232.110.120"),  34855);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:LQU9qZlK+B5F3KDmev6m5PMibrg=");
    }

    @Test
    public void testCommunityIdGeneratorUDP() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.1.52"),  54585);
        IpPortPair second = new IpPortPair(InetAddress.getByName("8.8.8.8"),  53);

        Protocol proto = Protocol.UDP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());


        assertEquals(result, "1:d/FP5EW3wiY1vCndhwleRRKHowQ=");
    }

    @Test
    public void testCommunityIdGeneratorUDPBase64False() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.1.52"),  54585);
        IpPortPair second = new IpPortPair(InetAddress.getByName("8.8.8.8"),  53);

        Protocol proto = Protocol.UDP;

        CommunityIdGenerator generator = new CommunityIdGenerator(0, false);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:77f14fe445b7c22635bc29dd87095e451287a304");
    }

    @Test
    public void testCommunityIdGeneratorUDPSeed1() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.1.52"),  54585);
        IpPortPair second = new IpPortPair(InetAddress.getByName("8.8.8.8"),  53);

        Protocol proto = Protocol.UDP;

        CommunityIdGenerator generator = new CommunityIdGenerator(1, true);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:Q9We8WO3piVF8yEQBNJF4uiSVrI=");
    }

    @Test
    public void testCommunityIdGeneratorSCTP() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.170.8"),  7);
        IpPortPair second = new IpPortPair(InetAddress.getByName("192.168.170.56"),  80);

        Protocol proto = Protocol.SCTP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:jQgCxbku+pNGw8WPbEc/TS/uTpQ=");
    }

    @Test
    public void testCommunityIdGeneratorSCTPBase64False() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.170.8"),  7);
        IpPortPair second = new IpPortPair(InetAddress.getByName("192.168.170.56"),  80);

        Protocol proto = Protocol.SCTP;

        CommunityIdGenerator generator = new CommunityIdGenerator(0, false);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:8d0802c5b92efa9346c3c58f6c473f4d2fee4e94");
    }

    @Test
    public void testCommunityIdGeneratorSCTPSeed1() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.170.8"),  7);
        IpPortPair second = new IpPortPair(InetAddress.getByName("192.168.170.56"),  80);

        Protocol proto = Protocol.SCTP;

        CommunityIdGenerator generator = new CommunityIdGenerator(1, true);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:Y1/0jQg6e+I3ZwZZ9LP65DNbTXU=");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCommunityIdGeneratorNegativePort() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("192.168.170.8"),  -7);
        IpPortPair second = new IpPortPair(InetAddress.getByName("192.168.170.56"),  80);

        Protocol proto = Protocol.SCTP;

        CommunityIdGenerator generator = new CommunityIdGenerator(1, true);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

    }

    @Test
    public void testCommunityIdGeneratorIPV6Default() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("fe80::200:86ff:fe05:80da"),  135);
        IpPortPair second = new IpPortPair(InetAddress.getByName("fe80::260:97ff:fe07:69ea"),  0);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator();

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:+sv1F6JQBglTsE0j26d1G1XhKZM=");
    }

    @Test
    public void testCommunityIdGeneratorIPV6base64False() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("fe80::200:86ff:fe05:80da"),  135);
        IpPortPair second = new IpPortPair(InetAddress.getByName("fe80::260:97ff:fe07:69ea"),  0);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator(0, false);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:facbf517a250060953b04d23dba7751b55e12993");
    }

    @Test
    public void testCommunityIdGeneratorIPV6Seed1() throws UnknownHostException {
        IpPortPair first = new IpPortPair(InetAddress.getByName("fe80::200:86ff:fe05:80da"),  135);
        IpPortPair second = new IpPortPair(InetAddress.getByName("fe80::260:97ff:fe07:69ea"),  0);

        Protocol proto = Protocol.TCP;

        CommunityIdGenerator generator = new CommunityIdGenerator(1, true);

        String result = generator.generateCommunityId(proto, first.getIpAddress(), first.getPort(),
                second.getIpAddress(), second.getPort());

        assertEquals(result, "1:crQhI6lXq1RY9sXjJKbsybx5Q8M=");
    }

}



