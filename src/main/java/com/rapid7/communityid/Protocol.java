package com.rapid7.communityid;

/**
 * Enum class for Network protocol values.
 *
 * Currently supports TCP, UDP, and SCTP.
 */
public enum Protocol {

    TCP((byte) 6),
    UDP((byte) 17),
    SCTP((byte) 132);

    private final byte protocolNumber;

    Protocol(byte protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public byte getProtocolNumber() {
        return protocolNumber;
    }
}

