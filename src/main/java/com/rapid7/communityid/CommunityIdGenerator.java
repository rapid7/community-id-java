/***************************************************************************
 * COPYRIGHT (C) 2012-2019, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.communityid;


import static java.util.Objects.requireNonNull;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;


/**
 * Class which hashes and builds a community ID based on the source and destination from network flow data.
 */
public class CommunityIdGenerator {
    private static final byte PADDING_BYTE = 0;

    private final int seed;
    private final boolean base64;


    /**
     * Default constructor for the communityId Generator. Sets seed to 0 and base64 encoding to true.
     */
    public CommunityIdGenerator() {
        this(0, true);
    }

    /**
     * Constructor for the communityId Generator. Set the seed and whether or not to base64 encode the result.
     *
     * The community ID String returned will be either hex encoded or base64 encoded depending
     * on the generators configuration. Setting the second parameter to true turns on base64 encoding.
     *
     * Only the first two bytes of the seed are used in the creation of the ID.
     *
     * @param seed a seed value to be packed into the encoded community ID string.
     *             (only the first two bytes will be added)
     * @param base64 a boolean value to indicate whether the string value should be base64 encoded.
     */
    public CommunityIdGenerator(int seed, boolean base64) {
        this.seed = seed;
        this.base64 = base64;
    }

    /**
     * Creates a community ID given flow information.
     *
     * @param protocol the network protocol i.e. TCP, UDP, STCP.
     * @param sourceAddress the InetAddress containing the source IP address.
     * @param sourcePort the Integer value of the source port.
     * @param destinationAddress the InetAddress containing the destination IP address.
     * @param destPort the Integer value of the destination port.
     * @return a string representing the community Id.
     * @throws IllegalArgumentException if ports are negative values.
     */
    public String generateCommunityId(Protocol protocol, InetAddress sourceAddress, int sourcePort,
                               InetAddress destinationAddress, int destPort) {

        requireNonNull(protocol);
        requireNonNull(sourceAddress);
        requireNonNull(destinationAddress);

        if (sourcePort < 0 || destPort < 0) {
            throw new IllegalArgumentException("Port number must be a positive Integer");
        }

        IpPortPair sourcePair = new IpPortPair(sourceAddress, sourcePort);
        IpPortPair destPair = new IpPortPair(destinationAddress, destPort);

        int compare = sourcePair.compareTo(destPair);

        IpPortPair firstPair;
        IpPortPair secondPair;

        if (compare > 0) {
            firstPair = destPair;
            secondPair = sourcePair;
        } else {
            firstPair = sourcePair;
            secondPair = destPair;
        }

        MessageDigest md = createDigest();

        md.update(intToTwoByteArray(seed));
        md.update(firstPair.getIpAddress().getAddress());
        md.update(secondPair.getIpAddress().getAddress());
        md.update(protocol.getProtocolNumber());
        md.update(PADDING_BYTE);
        md.update(intToTwoByteArray(firstPair.getPort()));
        md.update(intToTwoByteArray(secondPair.getPort()));

        byte[] messageDigest = md.digest();

        if (base64) {
            return "1:" + Base64.getEncoder().encodeToString(messageDigest);
        }

        return "1:" + bytesToHex(messageDigest);

    }

    /**
     * Produces a message digest or wraps the NoSuchAlgorithmException in a RuntimeException.
     *
     * @return a MessageDigest object or throws a RuntimeException.
     */
    private MessageDigest createDigest() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not produce a Message Digest", e);
        }
        return md;
    }

    /**
     * Formats an array of bytes to the proper string.
     *
     * @param bytes the byte array to be turned into the proper hex string.
     * @return The hex String created from the array of bytes.
     */
    private String bytesToHex(final byte[] bytes) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }

    /**
     * Converts an integer into a two byte array.
     *
     * @param value the Integer to be converted into a two byte array.
     * @return The two-byte array created from the integer value.
     */
    private byte[] intToTwoByteArray(int value) {
        byte[] bytes = new byte[2];

        bytes[0] = (byte) (value >>> 8);
        bytes[1] = (byte) value;

        return bytes;
    }



}
