package com.rapid7.communityid;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Objects;


/**
 * A class for holding info about the source or destination of a network flow.
 * Contains an InetAddress with the IP, and a port.
 */
class IpPortPair implements Comparable<IpPortPair> {

    private final InetAddress ipAddress;
    private final int port;

    /**
     * Constructor for an IpPortPair.
     * Takes an IP Address and a port
     */
    IpPortPair(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    InetAddress getIpAddress() {
        return ipAddress;
    }

    int getPort() {
        return port;
    }

    /**
     * CompareTo method for comparing one IpPortPair object to  another.
     * Uses the toBigInt method to convert and compare the addresses,
     * and if equal, uses ports. Lower gets sorted first.
     *
     * @param pair the other IP address and port to be compared.
     * @return The integer value of the comparison. A negative integer, positive integer, or 0.
     */
    @Override
    public int compareTo(IpPortPair pair) {

        BigInteger firstInt = toBigInt(this.getIpAddress());
        BigInteger secondInt = toBigInt(pair.getIpAddress());

        int compare = firstInt.compareTo(secondInt);

        return (compare == 0 ? Integer.compare(this.getPort(), pair.getPort()) : compare);

    }

    /**
     * Equals method for IpPortPair objects.
     * Determines if two IpPortPair objects are identical.
     *
     * @param o The other IpPortPair object to be compared to this.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        IpPortPair that = (IpPortPair) o;
        return port == that.port &&
                Objects.equals(ipAddress, that.ipAddress);
    }

    /**
     * toString method for turning the object to a string.
     */
    @Override
    public String toString() {
        return "IpPortPair{" +
                "ipAddress=" + ipAddress +
                ", port=" + port +
                '}';
    }

    /**
     * Creates a hashcode using the IP and port.
     *
     */
    @Override
    public int hashCode(){
        return Objects.hash(ipAddress, port);
    }

    /**
     * Turns an InetAddress into a BigInteger in order to compare and sort.
     *
     * @param inetAddress The InetAddress to be turned into a BigInteger.
     */
    static BigInteger toBigInt(InetAddress inetAddress){

        return new BigInteger(1, inetAddress.getAddress());

    }

}


