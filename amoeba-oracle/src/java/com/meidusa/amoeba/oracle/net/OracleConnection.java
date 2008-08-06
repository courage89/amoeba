package com.meidusa.amoeba.oracle.net;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.meidusa.amoeba.net.DatabaseConnection;
import com.meidusa.amoeba.net.Sessionable;
import com.meidusa.amoeba.net.io.PacketInputStream;
import com.meidusa.amoeba.net.io.PacketOutputStream;
import com.meidusa.amoeba.oracle.io.OraclePacketInputStream;
import com.meidusa.amoeba.oracle.io.OraclePacketOutputStream;

public abstract class OracleConnection extends DatabaseConnection {

	private int sdu;
	private int tdu;
	public OracleConnection(SocketChannel channel, long createStamp) {
		super(channel, createStamp);
		/**
		 * TODO
		 * 测试用
		 */
		this.setAuthenticated(true);
	}

	@Override
	protected PacketInputStream createPacketInputStream() {
		return new OraclePacketInputStream(true);
	}

	@Override
	protected PacketOutputStream createPakcetOutputStream() {
		return new OraclePacketOutputStream(true);
	}

	/**
	 * 为了提升性能，由于Oracle数据包写到目的地的时候已经包含了包头，则不需要经过PacketOutputStream处理
	 */
	public void postMessage(byte[] msg)
    {
        ByteBuffer out= ByteBuffer.allocate(msg.length);
        out.put(msg);
        out.flip();
        _outQueue.append(out);
        _cmgr.invokeConnectionWriteMessage(this);
    }

	public int getSdu() {
		return sdu;
	}

	public void setSdu(int sdu) {
		this.sdu = sdu;
	}

	public int getTdu() {
		return tdu;
	}

	public void setTdu(int tdu) {
		this.tdu = tdu;
	}
	
	public void postClose(Exception exception){
		super.postClose(exception);
		if(this.getMessageHandler() instanceof Sessionable){
			Sessionable session = (Sessionable)this.getMessageHandler();
			if(!session.isEnded()){
				session.endSession();
				this.setMessageHandler(null);
			}
		}
	}
}
