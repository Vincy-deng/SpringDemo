package com.test.vo;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Objects;

@Proxy(lazy = false)
@Entity
@javax.persistence.Table(name = "t_channels", schema = "dbo", catalog = "DBApsuite")
public class TChannelsEntity {

    private int lsh;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @javax.persistence.Column(name = "lsh")
    public int getLsh() {
        return lsh;
    }

    public void setLsh(int lsh) {
        this.lsh = lsh;
    }

    private String computer;

    @Basic
    @javax.persistence.Column(name = "computer")
    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    private String extension;

    @Basic
    @javax.persistence.Column(name = "extension")
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    private Integer enable;

    @Basic
    @javax.persistence.Column(name = "enable")
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    private String cardType;

    @Basic
    @javax.persistence.Column(name = "cardtype")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    private int chNoInCard;

    @Basic
    @javax.persistence.Column(name = "chnoincard")
    public int getChNoInCard() {
        return chNoInCard;
    }

    public void setChNoInCard(int chNoInCard) {
        this.chNoInCard = chNoInCard;
    }

    private String channelType;

    @Basic
    @javax.persistence.Column(name = "channeltype")
    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    private String channelIpAddr;

    @Basic
    @javax.persistence.Column(name = "channelipaddr")
    public String getChannelIpAddr() {
        return channelIpAddr;
    }

    public void setChannelIpAddr(String channelIpAddr) {
        this.channelIpAddr = channelIpAddr;
    }

    private String channelMac;

    @Basic
    @javax.persistence.Column(name = "channelmac")
    public String getChannelMac() {
        return channelMac;
    }

    public void setChannelMac(String channelMac) {
        this.channelMac = channelMac;
    }

    private Integer channelPacketSize;

    @Basic
    @javax.persistence.Column(name = "channelpacketsize")
    public Integer getChannelPacketSize() {
        return channelPacketSize;
    }

    public void setChannelPacketSize(Integer channelPacketSize) {
        this.channelPacketSize = channelPacketSize;
    }

    private String channelVirtualExt;

    @Basic
    @javax.persistence.Column(name = "channelvirtualext")
    public String getChannelVirtualExt() {
        return channelVirtualExt;
    }

    public void setChannelVirtualExt(String channelVirtualExt) {
        this.channelVirtualExt = channelVirtualExt;
    }

    private String channelSbcServer;

    @Basic
    @javax.persistence.Column(name = "channelsbcserver")
    public String getChannelSbcServer() {
        return channelSbcServer;
    }

    public void setChannelSbcServer(String channelSbcServer) {
        this.channelSbcServer = channelSbcServer;
    }

    private String ctiAppIpAddr;

    @Basic
    @javax.persistence.Column(name = "ctiappipaddr")
    public String getCtiAppIpAddr() {
        return ctiAppIpAddr;
    }

    public void setCtiAppIpAddr(String ctiAppIpAddr) {
        this.ctiAppIpAddr = ctiAppIpAddr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TChannelsEntity that = (TChannelsEntity) o;
        return lsh == that.lsh &&
                chNoInCard == that.chNoInCard &&
                Objects.equals(computer, that.computer) &&
                Objects.equals(extension, that.extension) &&
                Objects.equals(enable, that.enable) &&
                Objects.equals(cardType, that.cardType) &&
                Objects.equals(channelType, that.channelType) &&
                Objects.equals(channelIpAddr, that.channelIpAddr) &&
                Objects.equals(channelMac, that.channelMac) &&
                Objects.equals(channelPacketSize, that.channelPacketSize) &&
                Objects.equals(channelVirtualExt, that.channelVirtualExt) &&
                Objects.equals(channelSbcServer, that.channelSbcServer) &&
                Objects.equals(ctiAppIpAddr, that.ctiAppIpAddr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lsh, computer, extension, enable, cardType, chNoInCard, channelType, channelIpAddr, channelMac, channelPacketSize, channelVirtualExt, channelSbcServer, ctiAppIpAddr);
    }
}
